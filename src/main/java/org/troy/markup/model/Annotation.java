/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.input.MouseEvent;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.troy.markup.jaxb.AnnotationCircleXMLAdapter;
import org.troy.markup.jaxb.AnnotationRectangleXMLAdapter;
import org.troy.markup.memento.UndoRedoManager;
import org.troy.markup.memento.UndoRedoManagerImpl;
import org.troy.markup.state.AnnotationMouseDefaultState;
import org.troy.markup.state.AnnotationMouseEnteredState;
import org.troy.markup.state.AnnotationMouseState;
import org.troy.markup.utilities.AnnotationLetterFactory;

/**
 *
 * @author Troy
 */
public final class Annotation {

    private AnnotationCircleBean circle;
    private AnnotationRectangleBean rectangle;
    private StringProperty symbol = new SimpleStringProperty(this, "symbol", null);
    private StringProperty description = new SimpleStringProperty(this, "description", "To be done.");

    private BeanManager bm = BeanManager.createInstance();
    UndoRedoManager urm = UndoRedoManagerImpl.getInstance();

    public Annotation() {
        this(0, 0, 0, 0);
    }

    public Annotation(double xPos, double yPos, double width, double height) {
        setUpAnnotationRectangle(xPos, yPos, width, height);
        setUpAnnotationCircle(xPos, yPos);
        symbol.set(AnnotationLetterFactory.createLetter());
    }

    public Annotation(Annotation a) {
        AnnotationRectangleBean rect = a.getRectangle();
        AnnotationCircleBean c = a.getCircle();
        setUpAnnotationRectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        setUpAnnotationCircle(c.getX(), c.getY(), c.getRadius());
        this.setDescription(a.getDescription());
        this.setSymbol(a.getSymbol());

    }

    private void setUpAnnotationCircle(double xPos, double yPos) {
        circle = new AnnotationCircleBean(xPos - 10, yPos - 10, 10);
    }

    private void setUpAnnotationCircle(double xPos, double yPos, double radius) {
        circle = new AnnotationCircleBean(xPos, yPos, radius);
    }

    private void setUpAnnotationRectangle(double xPos, double yPos, double width, double height) {
        rectangle = new AnnotationRectangleBean(xPos, yPos, width, height);
        //rectangle.setPickOnBounds(false);

    }

    public AnnotationCircleBean getCircle() {
        return circle;
    }
    @XmlJavaTypeAdapter(AnnotationCircleXMLAdapter.class)
    public void setCircle(AnnotationCircleBean acb){
        circle = acb;
    }

    public AnnotationRectangleBean getRectangle() {
        return rectangle;
    }
    @XmlJavaTypeAdapter(AnnotationRectangleXMLAdapter.class)
    public void setRectangle(AnnotationRectangleBean arb){
        rectangle = arb;
    }

    public String getSymbol() {
        return symbol.getValue();
    }

    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }
    public StringProperty symbolProperty(){
        return symbol;
    }

       public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

}
