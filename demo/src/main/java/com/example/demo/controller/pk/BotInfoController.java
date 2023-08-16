package com.example.demo.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getBotInfo/")
    public List<Map<Integer, String>> getBotInfo() {
        List<Map<Integer, String>> list = new LinkedList<>();
        Map<Integer, String > bot1 = new HashMap<>();
        bot1.put(1, "apple");
        bot1.put(2, "banana");
        Map<Integer, String > bot2 = new HashMap<>();
        bot2.put(3, "cat");
        bot2.put(4, "dog");
        list.add(bot1);
        list.add(bot2);
        return list;
    }
    @RequestMapping("getMap/")
    public Map<String , String> getMap() {
        Map<String, String > map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "apple");
        return map;
    }
}
