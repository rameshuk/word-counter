package com.test.wordcounter.service.util;

import com.test.wordcounter.util.Translator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TranslatorTest {

    private Translator translator;

    @Before
    public void setUp() {
        translator = new Translator();
    }

    @Test
    public void testEnglishWord(){
        assertEquals("flower", translator.translate("flower"));
    }
    @Test
    public void testSpanishWord(){
        assertEquals("flower", translator.translate("flor"));
    }
    @Test
    public void testGermanhWord(){
        assertEquals("flower", translator.translate("blume"));
    }
}
