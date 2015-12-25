/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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
    private Text text;
    private StringProperty description = new SimpleStringProperty(this, "description", "To be done.");
    private AnnotationMouseState annotationMouseState;
    private boolean isCirclePressed = false;

    public Annotation(double xPos, double yPos, double width, double height) {
        setUpAnnotationRectangle(xPos, yPos, width, height);
        setUpAnnotationCircle(xPos, yPos);
        text = new Text(AnnotationLetterFactory.createLetter());
        text.xProperty().bind(circle.centerXProperty().subtract(+5));
        text.yProperty().bind(circle.centerYProperty().add(5));
        annotationMouseState = new AnnotationMouseDefaultState();
        annotationMouseState.changeEffects(this);
    }

    private void setUpAnnotationCircle(double xPos, double yPos) {
        circle = new AnnotationCircleBean(xPos - 10, yPos - 10, 10);
        circle.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            new AnnotationMouseEnteredState().changeEffects(this);
        });
        circle.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            new AnnotationMouseDefaultState().changeEffects(this);
        });
        circle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            isCirclePressed = true;
        });
        circle.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            isCirclePressed = false;
        });
        circle.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            circle.setXPos(e.getX());
            circle.setYPos(e.getY());
        });
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
        return text.textProperty().get();
    }
    public void setSymbol(String symbol){
        text.setText(symbol);
    }

    public AnnotationMouseState getAnnotationMouseState() {
        return annotationMouseState;
    }

    public void setAnnotationMouseState(AnnotationMouseState annotationMouseState) {
        this.annotationMouseState = annotationMouseState;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
    

}
