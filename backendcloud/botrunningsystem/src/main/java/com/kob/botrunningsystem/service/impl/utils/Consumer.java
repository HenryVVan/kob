package com.kob.botrunningsystem.service.impl.utils;

import com.kob.botrunningsystem.utils.BotInterface;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * @Author：Henry Wan
 * @Package：com.kob.botrunningsystem.service.impl.utils
 * @Project：backendcloud
 * @Date：2024/3/1 15:58
 * @Filename：Consumer
 */
@Component
public class Consumer extends Thread {
    private Bot bot;
    private static RestTemplate restTemplate;

    private static final String botReceiveUrl = "http://127.0.0.1:6221/pk/receive/bot/move/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        Consumer.restTemplate = restTemplate;
    }


    public void startTimeout(long timeout, Bot bot) {
        this.bot = bot;
        this.start();
        try {
            // 用join 不用sleep的原因，join更合理，如果线程执行完成，直接结束
            this.join(timeout); // 最多等待timeout秒，让当前线程执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt(); // 中断当前线程
        }
    }

    private String addUid(String code, String uid) {
        int k = code.indexOf(" implements com.kob.botrunningsystem.utils.BotInterface");
        System.out.println(k);
        return code.substring(0, k) + uid + code.substring(k);
    }

    @Override
    public void run() {
/*        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8);
        BotInterface botInterface = Reflect.compile(
                // 类重名的话，只会编译一次，需要加随机字符串
                // 每次用户输入 理应重新编译一次
                "com.kob.botrunningsystem.utils.BotImpl" + uid,
                addUid(bot.getBotCode(), uid)

        ).create().get();
        Integer direction = botInterface.nextMove(bot.getInput());
        System.out.println("move_direction " + bot.getUserId() + " " + direction);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("userId", String.valueOf(bot.getUserId()));
        data.add("direction", String.valueOf(direction));

        restTemplate.postForObject(botReceiveUrl, data, String.class);
    }
}
