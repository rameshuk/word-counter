package com.test.wordcounter.service;

import com.test.wordcounter.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WordCounterServiceImpl implements WordCounterService {

    @Autowired
    private Translator translate;

    //to make program simple, i used map in the same class,
    // other option is to create Repository to maintain this map/database
    private Map<String, Integer> counter = new HashMap<>();

    /*
     checks if given word contains only alphabets
     */
    private boolean isAlphabets(final String word) {
        return word.chars().allMatch(Character::isLetter);
    }

    public int counter(String word) {
        int defValue = 0;
        if (isAlphabets(word))
            defValue = counter.getOrDefault(translate.translate(word), defValue);
        return defValue;
    }

    /*
     adds word to repository if is valid input
     */
    public void add(String word) {
        if (isAlphabets(word))
            counter.merge(translate.translate(word), 1, Integer::sum);
        //else we can log error or throw exception depends on requirement.
    }


}
