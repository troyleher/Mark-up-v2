/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.scene.shape.Rectangle;
import org.troy.markup.model.AnnotationRectangleBean;
import org.troy.markup.state.AnnotationMouseDefaultState;
import org.troy.markup.state.AnnotationMouseState;

/**
 *
 * @author Troy
 */
public class AnnotationRectangleView extends Rectangle{
    
    private AnnotationMouseState ams ;

    public AnnotationRectangleView() {
        this(0, 0, 0, 0);
    }
    public AnnotationRectangleView(double x, double y, double width, double height) {
        super(x, y, width, height);
        ams = new AnnotationMouseDefaultState();
        ams.changeRectangleEffects(this);
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
        if(o instanceof AnnotationRectangleView){
            AnnotationRectangleView arv = (AnnotationRectangleView)o;
            if(arv.getX() == this.getX()){
                if(arv.getY() == this.getY()){
                    if(arv.getWidth() == this.getWidth()){
                        if(arv.getHeight() == this.getHeight()){
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
