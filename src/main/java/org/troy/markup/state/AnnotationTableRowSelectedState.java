/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.state;

import javafx.scene.paint.Color;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.AnnotationCircleBean;
import org.troy.markup.model.AnnotationRectangleBean;
import org.troy.markup.model.BeanManager;

/**
 *
 * @author Troy
 */
public class AnnotationTableRowSelectedState implements AnnotationMouseState {

    @Override
    public void changeEffects(Annotation a) {
        AnnotationRectangleBean rect = a.getRectangle();
        AnnotationCircleBean circle = a.getCircle();

        rect.setStroke(Color.ROYALBLUE);
        rect.setStrokeWidth(2);
        //rect.setEffect(new Glow(1.0));
        rect.setStyle("-fx-fill: null;");

        circle.setStroke(Color.ROYALBLUE);
        circle.setStrokeWidth(2);
        circle.setFill(Color.TRANSPARENT);

    }

}
