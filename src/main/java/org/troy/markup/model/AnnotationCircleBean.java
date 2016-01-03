/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Troy
 */
public class AnnotationCircleBean {

    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty radius = new SimpleDoubleProperty();
    private boolean isCirclePressed = false;
    private BooleanProperty circleMoved = new SimpleBooleanProperty(false);

    public AnnotationCircleBean() {
        this(0, 0, 0);
    }

    public AnnotationCircleBean(double xPos, double yPos, double radius) {
        this.x.set(xPos);
        this.y.set(yPos);
        this.radius.set(radius);
    }

    public AnnotationCircleBean(AnnotationCircleBean circle) {
        this(circle.getX(), circle.getY(), circle.getRadius());
    }

    public double getX() {
        return x.get();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return y.get();
    }

    public void setY(double yPos) {
        y.set(yPos);
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public double getRadius() {
        return radius.get();
    }

    public void setRadius(double radius) {
        this.radius.set(radius);
    }

    public DoubleProperty radiusProperty() {
        return radius;
    }

    public boolean isIsCirclePressed() {
        return isCirclePressed;
    }

    public void setIsCirclePressed(boolean isCirclePressed) {
        this.isCirclePressed = isCirclePressed;
    }

    public void setCircleMoved(boolean moved) {
        circleMoved.set(moved);
    }

    public boolean getCircleMoved() {
        return circleMoved.get();
    }

    public BooleanProperty circleMovedProperty() {
        return circleMoved;
    }

    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if (o instanceof AnnotationCircleBean) {
            AnnotationCircleBean c = (AnnotationCircleBean) o;
            if (c.getX() == this.getX()) {
                if (c.getY() == this.getY()) {
                    if (c.getRadius() == this.getRadius()) {
                        isEqual = true;
                    }
                }
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode() + radius.hashCode();
    }

}
