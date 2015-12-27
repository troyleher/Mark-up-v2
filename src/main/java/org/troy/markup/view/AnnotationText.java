/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.scene.text.Text;

/**
 *
 * @author Troy
 */
public class AnnotationText extends Text{

    public AnnotationText() {
    }

    public AnnotationText(double x, double y, String text) {
        super(x, y, text);
    }

    public AnnotationText(String text) {
        super(text);
    }

    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
       if(o instanceof AnnotationText){
           AnnotationText text = (AnnotationText)o;
           if(text.getText().equalsIgnoreCase(this.getText())){
               isEqual = true;
           }
       }
       return isEqual;
    }

    @Override
    public int hashCode() {
        return getText().hashCode();
    }
    
    
    
}
