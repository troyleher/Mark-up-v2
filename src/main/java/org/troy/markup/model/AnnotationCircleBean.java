/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.scene.shape.Circle;

/**
 *
 * @author Troy
 */
public class AnnotationCircleBean extends Circle{
    
    
    private AnnotationCircleBean(){}
    public AnnotationCircleBean(double xPos, double yPos, double radius){
       super(xPos, yPos, radius);
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
