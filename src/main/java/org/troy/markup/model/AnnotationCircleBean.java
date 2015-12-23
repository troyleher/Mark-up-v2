/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Troy
 */
public class AnnotationCircleBean {
    private DoubleProperty xPos = new SimpleDoubleProperty(this, "xPos", 0);
    private DoubleProperty yPos = new SimpleDoubleProperty(this, "yPos", 0);
    private DoubleProperty radius = new SimpleDoubleProperty(this, "radius", 0);
    
    private AnnotationCircleBean(){}
    public AnnotationCircleBean(double xPos, double yPos, double radius){
        this.xPos.set(xPos);
        this.yPos.set(xPos);
        this.radius.set(radius);
    }

    public double getxPos() {
        return xPos.doubleValue();
    }

    public void setxPos(double xPos) {
        this.xPos.set(xPos);
    }
    public DoubleProperty xPosDoubleProperty(){
        return xPos;
    }

    public double getyPos() {
        return yPos.doubleValue();
    }

    public void setyPos(double yPos) {
        this.yPos.set(yPos);
    }
    
    public DoubleProperty yPosDoubleProperty(){
        return yPos;
    }

    public double getRadius() {
        return radius.get();
    }

    public void setRadius(double radius) {
        this.radius.set(radius);
    }
    public DoubleProperty radiusDoubleProperty(){return radius;}

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof AnnotationCircleBean){
            AnnotationCircleBean aCircle = (AnnotationCircleBean)obj;
            if(aCircle.xPos.get() == this.xPos.get())
                if(aCircle.yPos.get() == this.yPos.get())
                    if(aCircle.radius.get() == this.getRadius()){
                        isEqual = true;
                    }
        }
        return isEqual;
    }
    
    
    
    
}
