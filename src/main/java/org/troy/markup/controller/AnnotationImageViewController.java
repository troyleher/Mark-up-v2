/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.troy.markup.view.AnnotationImageView;

/**
 *
 * @author Troy
 */
public class AnnotationImageViewController {

    private final AnnotationImageView view;
    private MainController mc;
    private Point2D pressedPoint;
    private Rectangle rect;

    public AnnotationImageViewController(AnnotationImageView view, MainController mc) {
        this.view = view;
        this.mc = mc;
        initEventHandlers();
    }
    private void initEventHandlers() {
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            rect = new Rectangle(e.getX(), e.getY(), 1, 1);
            rect.setStrokeWidth(1);
            rect.setStroke(Color.BLACK);
            rect.getStrokeDashArray().addAll(5d, 5d);
            rect.setFill(Color.TRANSPARENT);
            pressedPoint = new Point2D(e.getX(), e.getY());
            mc.displayDraggingRectangle(rect);
        });
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            mc.removeDraggingRectangle(rect);
            mc.createAndDisplayAnnotation(rect);
        });
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            rect.setWidth(e.getX() - pressedPoint.getX());
            rect.setHeight(e.getY() - pressedPoint.getY());
        });
    }

}
