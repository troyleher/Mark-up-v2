/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;


import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Troy
 */
public class ImageView extends javafx.scene.image.ImageView {

    private Point2D pressedPoint;
    private Pane pane;
    private Rectangle rect ;

    public ImageView(Image image, Pane pane) {
        super(image);
        this.pane = pane;

        addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            rect = new Rectangle(e.getX(), e.getY(), 1, 1);
            rect.setStrokeWidth(1);
            rect.setStroke(Color.BLACK);
            rect.getStrokeDashArray().addAll(5d, 5d);
            rect.setFill(Color.TRANSPARENT);
            pressedPoint = new Point2D(e.getX(), e.getY());
            pane.getChildren().add(rect);
            System.out.println("Mouse pressed");
        });
        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            pane.getChildren().remove(rect);

        });
       addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            rect.setWidth(e.getX() - pressedPoint.getX());
            rect.setHeight(e.getY() - pressedPoint.getY());
        });

    }

}
