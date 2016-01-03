/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.state;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Troy
 */
public interface AnnotationMouseState {
    
    public void changeRectangleEffects(Rectangle r);
    public void changeCircleEffects(Circle c);
    //public void changeRectangleCircleEffects(Rectangle r, Circle c);
    
}
