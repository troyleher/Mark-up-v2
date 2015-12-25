/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author Troy
 */
public class AnnotationRectangleBean extends Rectangle{

   
    private AnnotationRectangleBean() {
        super();
    }

    public AnnotationRectangleBean(double xPos, double yPos,
            double width, double height) {
        super(xPos, yPos, width, height);
    }

    
    public final void setXPos(Double value) {
        setX(value);
    }

    public final Double getXPos() {
        return getX();
    }

  

    public final void setYPos(Double value) {
        setY(value);
    }

    public final Double getYPos() {
        return getY();
    }

    

    public final void setRectangleWidth(Double value) {
        setWidth(value);
    }

    public final Double getRectangleWidth() {
        return getWidth();
    }

   

    public final void setRectangleHeight(Double value) {
        setHeight(value);
    }

    public final Double getRectangleHeight() {
        return getHeight();
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
        return xProperty().hashCode() + yProperty().hashCode() + 
                widthProperty().hashCode() + heightProperty().hashCode();
    }

    

}
