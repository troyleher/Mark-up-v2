package org.troy.markup.test.utilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.troy.markup.utilities.AnnotationLetterFactory;

/**
 *
 * @author Troy
 */
public class TestAnnotationLetterFactory {

    public TestAnnotationLetterFactory() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLetterCreation() {
        AnnotationLetterFactory.setCurrentLetter(AnnotationLetterFactory.RESET);
        assertTrue(AnnotationLetterFactory.createLetter().equalsIgnoreCase("a"));
        assertTrue(AnnotationLetterFactory.createLetter().equalsIgnoreCase("b"));
        
        for(int c = 0; c < 24; c++){
            //System.out.println(AnnotationLetterFactory.createLetter());
            AnnotationLetterFactory.createLetter();
        }
                assertTrue(AnnotationLetterFactory.createLetter().equalsIgnoreCase("z1"));
    }
    @Test
    public void testSetCurrentLetter(){
        AnnotationLetterFactory.setCurrentLetter("a".charAt(0));
        assertTrue(AnnotationLetterFactory.createLetter().equalsIgnoreCase("B"));
        
        AnnotationLetterFactory.setCurrentLetter("B".charAt(0));
        assertTrue(AnnotationLetterFactory.createLetter().equalsIgnoreCase("C"));
        
    }
     @Test
    public void testSetCurrentZPostfixNumber(){
        AnnotationLetterFactory.setCurrentZPostfixNumber(2);
        assertTrue(AnnotationLetterFactory.createLetter().equalsIgnoreCase("Z3"));
    }
    
}
