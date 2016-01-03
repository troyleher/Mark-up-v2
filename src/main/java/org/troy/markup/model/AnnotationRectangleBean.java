/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Troy
 */
public class AnnotationRectangleBean {

    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty width = new SimpleDoubleProperty();
    private DoubleProperty height = new SimpleDoubleProperty();

    private AnnotationRectangleBean() {
        super();
    }

    public AnnotationRectangleBean(double xPos, double yPos,
            double width, double height) {
        this.x.set(xPos);
        this.y.set(yPos);
        this.width.set(width);
        this.height.set(height);
    }

    public AnnotationRectangleBean(AnnotationRectangleBean a) {
        this(a.getX(), a.getY(), a.getWidth(), a.getHeight());
    }

    public double getX() {
        return x.get();
    }

    public void setX(double value) {
        x.set(value);
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return y.get();
    }

    public void setY(double value) {
        y.set(value);
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public double getWidth() {
        return width.get();
    }

    public void setWidth(double value) {
        width.set(value);
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public double getHeight() {
        return height.get();
    }

    public void setHeight(double value) {
        height.set(value);
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof AnnotationRectangleBean) {
            AnnotationRectangleBean rectBean = (AnnotationRectangleBean) obj;
            if (rectBean.getX() == this.getX()) {
                if (rectBean.getY() == this.getY()) {
                    if (rectBean.getWidth() == this.getWidth()) {
                        if (rectBean.getHeight() == this.getHeight()) {
                            isEqual = true;
                        }
                    }
                }
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return xProperty().hashCode() + yProperty().hashCode()
                + widthProperty().hashCode() + heightProperty().hashCode();
    }

}
