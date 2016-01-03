/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.state;

import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.AnnotationCircleBean;
import org.troy.markup.model.AnnotationRectangleBean;

/**
 *
 * @author Troy
 */
public class AnnotationMouseDefaultState implements AnnotationMouseState{

    @Override
    public void changeRectangleEffects(Rectangle r) {
      
        r.setStroke(Color.GRAY);
        r.setStrokeWidth(1);
        r.setStyle("-fx-fill: null;");
    }

    @Override
    public void changeCircleEffects(Circle c) {
        c.setStroke(Color.GRAY);
        c.setStrokeWidth(1);
        c.setFill(Color.TRANSPARENT);
    }
    
}
