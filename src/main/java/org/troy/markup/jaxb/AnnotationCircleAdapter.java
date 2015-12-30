/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.jaxb;

import org.troy.markup.model.AnnotationCircleBean;

/**
 *
 * @author Troy
 */
public class AnnotationCircleAdapter {
    private double xPos;
    private double yPos;
    private double radius;
    public  AnnotationCircleAdapter(){}
    public AnnotationCircleAdapter(AnnotationCircleBean acb){
        xPos = acb.getCenterX();
        yPos = acb.getCenterY();
        radius = acb.getCircleRadius();
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    
}
