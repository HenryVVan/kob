package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getBotInfo/")
    public List<Map<Integer, String>> getBotInfo() {
        List<Map<Integer, String>> list = new LinkedList<>();
        Map<Integer, String> bot1 = new HashMap<>();
        bot1.put(1, "apple");
        bot1.put(2, "banana");
        Map<Integer, String> bot2 = new HashMap<>();
        bot2.put(3, "cat");
        bot2.put(4, "dog");
        list.add(bot1);
        list.add(bot2);
        return list;
    }

    @RequestMapping("getInfo/")
    public Map<String, String> getInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "wh");
        map.put("rating", "1500");
        return map;
    }

    @RequestMapping("getName/")
    public TreeSet<String> getName() {
        TreeSet<String> ts = new TreeSet<>();
        ts.add("wh");
        ts.add("wh");
        ts.add("wxy");
        return ts;
    }

}
