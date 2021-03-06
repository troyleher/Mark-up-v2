/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.state;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Troy
 */
public class AnnotationTableRowSelectedState implements AnnotationMouseState {

    @Override
    public void changeRectangleEffects(Rectangle r) {
        r.setStroke(Color.ROYALBLUE);
        r.setStrokeWidth(2);
        r.setStyle("-fx-fill: null;");
    }

    @Override
    public void changeCircleEffects(Circle c) {
        c.setStroke(Color.ROYALBLUE);
        c.setStrokeWidth(2);
        c.setFill(Color.TRANSPARENT);
    }

}
