/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;


import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.troy.markup.controller.Controller;
import org.troy.markup.memento.UndoRedoManagerImpl;
import org.troy.markup.model.BeanManager;

/**
 *
 * @author Troy
 */
public class ImageView extends javafx.scene.image.ImageView {

    private Point2D pressedPoint;
    private Rectangle rect ;

    public ImageView(Image image, Controller c) {
        super(image);

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            rect = new Rectangle(e.getX(), e.getY(), 1, 1);
            rect.setStrokeWidth(1);
            rect.setStroke(Color.BLACK);
            rect.getStrokeDashArray().addAll(5d, 5d);
            rect.setFill(Color.TRANSPARENT);
            pressedPoint = new Point2D(e.getX(), e.getY());
            c.displayDraggingRectangle(rect);
        });
        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            c.removeDraggingRectangle(rect);
            c.createAndDisplayAnnotation(rect);
        });
       addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            rect.setWidth(e.getX() - pressedPoint.getX());
            rect.setHeight(e.getY() - pressedPoint.getY());
        });
      

    }

}
