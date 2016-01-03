/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.scene.shape.Circle;
import org.troy.markup.state.AnnotationMouseDefaultState;
import org.troy.markup.state.AnnotationMouseState;

/**
 *
 * @author Troy
 */
public class AnnotationCircleView extends Circle{
    
    private AnnotationMouseState ams;

    public AnnotationCircleView() {
        this(0,0,0);
    }

    public AnnotationCircleView(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        ams = new AnnotationMouseDefaultState();
        ams.changeCircleEffects(this);
    }

    public AnnotationMouseState getAms() {
        return ams;
    }

    public void setAms(AnnotationMouseState ams) {
        this.ams = ams;
    }

    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if(o instanceof AnnotationCircleView){
            AnnotationCircleView acv = (AnnotationCircleView)o;
            if(acv.getCenterX() == this.getCenterX()){
                if(acv.getCenterY() == this.getCenterY()){
                    if(acv.getRadius() == this.getRadius()){
                        isEqual = true;
                    }
                }
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return centerXProperty().hashCode() + 
                centerYProperty().hashCode() + radiusProperty().hashCode();
    }
    
    
    
    
    
    
}
