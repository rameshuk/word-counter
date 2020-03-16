package com.test.wordcounter.util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
 External service
 */
@Service
public class Translator {

    private Map<String, String> dict;

    /* load initial data */
    private void bootstrapData() {
        dict = new HashMap<>();
        dict.put("flower", "flower");
        dict.put("flor", "flower");
        dict.put("blume", "flower");
    }

    public String translate(String word) {
        if (dict == null)
            bootstrapData();
        dict.putIfAbsent(word, word);
        return  dict.get(word);
    }
}
