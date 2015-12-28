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
import javafx.scene.text.Text;
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
    private AnnotationMouseState annotationMouseState;
    private Text symbolText;
    private ObservableMap<String, Object> properties = FXCollections.observableHashMap();
    
    public static final String GROUP_NODE = "GROUP_NODE";

    private BeanManager bm = BeanManager.createInstance();
    UndoRedoManager urm = UndoRedoManagerImpl.getInstance();

    public Annotation() {
        this(0, 0, 0, 0);
    }

    public Annotation(double xPos, double yPos, double width, double height) {
        setUpAnnotationRectangle(xPos, yPos, width, height);
        setUpAnnotationCircle(xPos, yPos);
        symbol.set(AnnotationLetterFactory.createLetter());
        annotationMouseState = new AnnotationMouseDefaultState();
        annotationMouseState.changeEffects(this);
    }

    public Annotation(Annotation a) {
        AnnotationRectangleBean rect = a.getRectangle();
        AnnotationCircleBean c = a.getCircle();
        setUpAnnotationRectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        setUpAnnotationCircle(c.getXPos(), c.getYPos(), c.getRadius());
        this.setDescription(a.getDescription());
        annotationMouseState = a.getAnnotationMouseState();
        annotationMouseState.changeEffects(this);
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
        rectangle.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            new AnnotationMouseEnteredState().changeEffects(this);
        });
        rectangle.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            new AnnotationMouseDefaultState().changeEffects(this);
        });
    }

    public AnnotationCircleBean getCircle() {
        return circle;
    }

    public AnnotationRectangleBean getRectangle() {
        return rectangle;
    }

    public String getSymbol() {
        return symbol.getValue();
    }

    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }

    public AnnotationMouseState getAnnotationMouseState() {
        return annotationMouseState;
    }

    public void setAnnotationMouseState(AnnotationMouseState annotationMouseState) {
        this.annotationMouseState = annotationMouseState;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty(){
        return description;
    }
    public Text getSymbolText() {
        return symbolText;
    }

    public void setSymbolText(Text symbolText) {
        this.symbolText = symbolText;
    }

    public ObservableMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(ObservableMap<String, Object> properties) {
        this.properties = properties;
    }
    
    

    

}
