/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.Circle;

/**
 *
 * @author Troy
 */
public class AnnotationCircleBean extends Circle{
    
    private boolean isCirclePressed = false;
    private BooleanProperty circleMoved = new SimpleBooleanProperty(false);
    
    private AnnotationCircleBean(){}
    public AnnotationCircleBean(double xPos, double yPos, double radius){
       super(xPos, yPos, radius);
    }
    public AnnotationCircleBean(AnnotationCircleBean circle){
        this(circle.getXPos(), circle.getYPos(), circle.getRadius());
    }

    public double getXPos() {
        return getCenterX();
    }

    public void setXPos(double xPos) {
        setCenterX(xPos);
    }


    public double getYPos() {
        return getCenterY();
    }

    public void setYPos(double yPos) {
        setCenterY(yPos);
    }
    
    public double getCircleRadius() {
        return getRadius();
    }

    public void setCircleRadius(double radius) {
        setRadius(radius);
    }

    public boolean isIsCirclePressed() {
        return isCirclePressed;
    }

    public void setIsCirclePressed(boolean isCirclePressed) {
        this.isCirclePressed = isCirclePressed;
    }
    public void setCircleMoved(boolean moved){
        circleMoved.set(moved);
    }
    public boolean getCircleMoved(){
        return circleMoved.get();
    }
    public BooleanProperty circleMovedProperty(){
        return  circleMoved;
    }
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof AnnotationCircleBean){
            AnnotationCircleBean aCircle = (AnnotationCircleBean)obj;
          
            if(aCircle.getCenterX() == this.getCenterX())
                if(aCircle.getCenterY() == this.getCenterY())
                    if(aCircle.getRadius() == this.getRadius()){
                        isEqual = true;
                    }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return centerXProperty().hashCode() + centerYProperty().hashCode() + radiusProperty().hashCode();
    }
    
    
    
    
    
}
