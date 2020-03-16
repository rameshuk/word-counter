package com.test.wordcounter.service;


import com.test.wordcounter.util.Translator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WordCounterServiceTest {

    @Mock
    Translator translator;

    @InjectMocks
    WordCounterService wordCounterService = new WordCounterServiceImpl();

    @Test
    public void whenNoWordFound() {
        assertEquals(0, wordCounterService.counter("NotFoundWord"));
        verify(translator, times(1)).translate("NotFoundWord");
    }

    @Test
    public void whenWordAppearsOnce() {
        final String word = "OneTime";
        Mockito.when(translator.translate(word)).thenReturn(word);
        wordCounterService.add(word);
        assertEquals(1, wordCounterService.counter(word));
        verify(translator, times(2)).translate(word);
    }

    @Test
    public void whenWordAppearsTwice() {
        final String word = "TwoTimes";
        Mockito.when(translator.translate(word)).thenReturn(word);
        wordCounterService.add(word);
        wordCounterService.add(word);
        assertEquals(2, wordCounterService.counter(word));
        verify(translator, times(3)).translate(word);
    }

    @Test
    public void whenWordAppearsManyTimes() {
        final String word = "ManyTimes";
        Mockito.when(translator.translate(word)).thenReturn(word);
        int times = 10;
        for (int i = 0; i < times; i++) {
            wordCounterService.add(word);
        }
        assertEquals(times, wordCounterService.counter(word));
        verify(translator, times(times+1)).translate(word);
    }

    @Test
    public void whenWordContainsNonAlphabetic() {
        final String flower = "flower@123";
        wordCounterService.add(flower);
        assertEquals(0, wordCounterService.counter(flower));

        final String anotherFlower = "flower";
        Mockito.when(translator.translate(anotherFlower)).thenReturn(anotherFlower);
        wordCounterService.add(anotherFlower);
        assertEquals(1, wordCounterService.counter(anotherFlower));

        verify(translator, never()).translate(flower);
        verify(translator, times(2)).translate(anotherFlower);

    }

    @Test
    public void whenWordAddedInDifferentLanguages() {
        final String flower = "flower";
        Mockito.when(translator.translate(flower)).thenReturn(flower);
        wordCounterService.add(flower);
        final String flowerInSpanish = "flor";
        Mockito.when(translator.translate(flowerInSpanish)).thenReturn(flower);
        wordCounterService.add(flowerInSpanish);
        final String flowerInGermany = "blume";
        Mockito.when(translator.translate(flowerInGermany)).thenReturn(flower);
        wordCounterService.add(flowerInGermany);
        assertEquals(3, wordCounterService.counter(flower));
        assertEquals(3, wordCounterService.counter(flowerInSpanish));
        assertEquals(3, wordCounterService.counter(flowerInGermany));

        verify(translator, times(2)).translate(flower);
        verify(translator, times(2)).translate(flowerInSpanish);
        verify(translator, times(2)).translate(flowerInGermany);
    }

    @Test
    public void whenTwoDifferentWordsAdded() {
        String str = "StringOne";
        Mockito.when(translator.translate(str)).thenReturn(str);
        wordCounterService.add(str);
        wordCounterService.add(str);
        assertEquals(2, wordCounterService.counter(str));

        String anotherStr = "StringTwo";
        Mockito.when(translator.translate(anotherStr)).thenReturn(anotherStr);
        wordCounterService.add(anotherStr);
        assertEquals(1, wordCounterService.counter(anotherStr));

        verify(translator, times(3)).translate(str);
        verify(translator, times(2)).translate(anotherStr);
    }
}
