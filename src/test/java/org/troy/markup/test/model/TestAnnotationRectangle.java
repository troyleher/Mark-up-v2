/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.test.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.troy.markup.model.AnnotationRectangleBean;

/**
 *
 * @author Troy
 */
public class TestAnnotationRectangle {
    
    public TestAnnotationRectangle() {
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
    public void testEquals() {
        AnnotationRectangleBean rectange1 = new AnnotationRectangleBean(10, 10, 50, 50);
        AnnotationRectangleBean rectange2 = new AnnotationRectangleBean(10, 10, 50, 50);
        AnnotationRectangleBean rectange3 = new AnnotationRectangleBean(10, 10, 50, 540);
        
        assertTrue(rectange1.equals(rectange2));
        assertFalse(rectange1.equals(rectange3));
    }
}
