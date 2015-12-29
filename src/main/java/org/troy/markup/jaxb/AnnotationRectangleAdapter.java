/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.jaxb;

import org.troy.markup.model.AnnotationRectangleBean;

/**
 *
 * @author Troy
 */
public class AnnotationRectangleAdapter {
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    
    public AnnotationRectangleAdapter(AnnotationRectangleBean arb){
        xPos = arb.getX();
        yPos = arb.getY();
        width = arb.getWidth();
        height = arb.getHeight();
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    
}
