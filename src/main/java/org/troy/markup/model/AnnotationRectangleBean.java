/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import com.sun.javafx.font.FontConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author Troy
 */
public class AnnotationRectangleBean {

    private DoubleProperty xPos = new SimpleDoubleProperty(this, "xPos", 0);
    private DoubleProperty yPos = new SimpleDoubleProperty(this, "yPos", 0);
    private DoubleProperty width = new SimpleDoubleProperty(this, "width", 0);
    private DoubleProperty hieght = new SimpleDoubleProperty(this, "height", 0);
    private Color strokeColor = Color.BLACK;
    private Color fillColor = Color.TRANSPARENT;
    private IntegerProperty strokeWidth = new SimpleIntegerProperty(this, "strokeWidth", 1);
    private Collection<Double> strokeArrayList ;

    private AnnotationRectangleBean() {
        strokeArrayList = new ArrayList<>(5);
        strokeArrayList.add(0d);
    }

    public AnnotationRectangleBean(double xPos, double yPos,
            double width, double height) {
        this();
        this.xPos.set(xPos);

        this.yPos.set(yPos);

        this.width.set(width);

        this.hieght.set(height);
    }

    public final void setXPos(Double value) {
        xPos.set(value);
    }

    public final Double getXPos() {
        return xPos.get();
    }

    public final DoubleProperty xPosProperty() {
        return xPos;
    }

    public final void setYPos(Double value) {
        yPos.set(value);
    }

    public final Double getYPos() {
        return yPos.get();
    }

    public final DoubleProperty yPosProperty() {
        return yPos;
    }

    public final void setWidth(Double value) {
        width.set(value);
    }

    public final Double getWidth() {
        return width.get();
    }

    public final DoubleProperty widthProperty() {
        return width;
    }

    public final void setHieght(Double value) {
        hieght.set(value);
    }

    public final Double getHieght() {
        return hieght.get();
    }

    public final DoubleProperty hieghtProperty() {
        return hieght;
    }
    

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof AnnotationRectangleBean) {
            AnnotationRectangleBean rectBean = (AnnotationRectangleBean) obj;
            if (rectBean.xPos.get() == this.xPos.get()) {
                if (rectBean.yPos.get() == this.yPos.get()) {
                    if (rectBean.width.get() == this.width.get()) {
                        if (rectBean.hieght.get() == this.hieght.get()) {
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
        return xPos.hashCode() + yPos.hashCode() + width.hashCode() + hieght.hashCode();
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public IntegerProperty getStrokeWidth() {
        return strokeWidth;
    }

    public Collection<Double> getStrokeArrayList() {
        return strokeArrayList;
    }

}
