/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.AnnotationCircleBean;
import org.troy.markup.model.AnnotationRectangleBean;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.ConfigurationBean;
import org.troy.markup.view.ImageView;

/**
 *
 * @author Troy
 */
public class MainController extends Application implements Controller {

    private ImageView imageView;
    private Pane imagePane;
    private BeanManager bm;

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigurationBean configBean = ConfigurationBean.createInstance();
        bm = BeanManager.createInstance();
        BorderPane borderPane = new BorderPane();

        imagePane = new AnchorPane();
        borderPane.setCenter(imagePane);

        //Load a sample image for development only
        Image image = new Image("/images/test.png");
        imageView = new ImageView(image, this);
        imagePane.getChildren().add(imageView);

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle(configBean.getMainFrameTitle());
        primaryStage.show();

    }

    public void addAnnotationToView(Annotation a) {

    }

    @Override
    public void createAndDisplayAnnotation(Rectangle r) {
        Annotation a = new Annotation(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        bm.addAnnotationToList(a);
        AnnotationCircleBean c = a.getCircle();
        AnnotationRectangleBean rect = a.getRectangle();
        imagePane.getChildren().add(new Circle(c.getxPos(),
                c.getyPos(),
                c.getRadius()));
        imagePane.getChildren().add(parseToJavaFXRect(rect));
    }

    @Override
    public void displayDraggingRectangle(Rectangle r) {
        imagePane.getChildren().add(r);
    }

    @Override
    public void removeDraggingRectangle(Rectangle r) {
        imagePane.getChildren().remove(r);
    }
    
    private Rectangle parseToJavaFXRect(AnnotationRectangleBean arb){
        Rectangle r = new Rectangle(arb.getXPos(),
                arb.getYPos(), 
                arb.getWidth(),
                arb.getHieght());
        r.setStroke(arb.getStrokeColor());
        r.setFill(arb.getFillColor());
        r.setStrokeWidth(arb.getStrokeWidth().get());
        return r;
    }

}
