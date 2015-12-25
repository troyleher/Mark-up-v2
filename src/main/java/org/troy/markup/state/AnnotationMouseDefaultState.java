/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.state;

import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.paint.Color;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.AnnotationCircleBean;
import org.troy.markup.model.AnnotationRectangleBean;

/**
 *
 * @author Troy
 */
public class AnnotationMouseDefaultState implements AnnotationMouseState{

    @Override
    public void changeEffects(Annotation a) {
        AnnotationRectangleBean rect = a.getRectangle();
        AnnotationCircleBean circle = a.getCircle();
        
        
        rect.setStroke(Color.GRAY);
        rect.setStrokeWidth(1);
        rect.setStyle("-fx-fill: null;");
        
        circle.setStroke(Color.GRAY);
        circle.setStrokeWidth(1);
        circle.setFill(Color.TRANSPARENT);
   
    }
    
}
