/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.troy.markup.utilities.AnnotationLetterFactory;

/**
 *
 * @author Troy
 */
public final class Annotation {
    private AnnotationCircleBean circle ;
    private AnnotationRectangleBean rectangle;
    private StringProperty symbol = new SimpleStringProperty(this, "symbol", "");
    
    public Annotation(double xPos, double yPos, double width, double height){
       rectangle = new AnnotationRectangleBean(xPos, yPos, width, height);
       circle = new AnnotationCircleBean(xPos - 10, yPos - 10, 10);
       symbol.set(AnnotationLetterFactory.createLetter());
    }

    public AnnotationCircleBean getCircle() {
        return circle;
    }

    public AnnotationRectangleBean getRectangle() {
        return rectangle;
    }

    public StringProperty getSymbol() {
        return symbol;
    }
    
}
