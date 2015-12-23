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
import org.troy.markup.model.AnnotationCircleBean;

/**
 *
 * @author Troy
 */
public class TestAnnotationCircle {

    public TestAnnotationCircle() {
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
        AnnotationCircleBean circle1 = new AnnotationCircleBean(10, 10, 50);
        AnnotationCircleBean circle2 = new AnnotationCircleBean(10, 10, 40);
        AnnotationCircleBean circle3 = new AnnotationCircleBean(10, 10, 40);
        
        assertFalse(circle1.equals(circle2));
        assertTrue(circle2.equals(circle3));
    }
}
