/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 *
 * @author Troy
 */
public class AnnotationTextView extends Text{

    
    public AnnotationTextView() {
    }

    public AnnotationTextView(String text) {
        super(text);
    }

    public AnnotationTextView(double x, double y, String text) {
        super(x, y, text);
    }
    
  @Override
    public boolean equals(Object o) {
        System.out.println("Equals invoked");
        boolean isEqual = false;
        if(o instanceof AnnotationTextView){
            AnnotationTextView atv = (AnnotationTextView)o;
            if(atv.getText().equalsIgnoreCase(this.getText())){
                isEqual = true;
            }
        }
        return isEqual;    
    }

    @Override
    public int hashCode() {
        return this.getText().hashCode();
    }
    
    
    
    
    
}
