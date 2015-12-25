/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author Troy
 */
public interface Controller {
    
    public void createAndDisplayAnnotation(Rectangle r);
    
    public void displayDraggingRectangle(Rectangle r);
    
    public void removeDraggingRectangle(Rectangle r);
    
   
    
}
