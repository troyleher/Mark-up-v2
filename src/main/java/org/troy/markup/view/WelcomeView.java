/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.troy.markup.controller.Main;
import org.troy.markup.model.SystemConfigWrapper;

/**
 *
 * @author Troy
 */
public class WelcomeView extends BorderPane{
    private ListView<String> listView;
    private Button newButton;
    private Button openButton;
    private Stage stage;
    
    public WelcomeView(Stage stage) {
        this.stage = stage;
        initGUI();
    }
    
    private void initGUI() {
        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();
        
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        listView = new ListView<>();
        listView.setMaxHeight(150);
        gridPane.add(listView, 0, 0, 1, 1);
        
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(new Image("/images/test.png"));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        gridPane.add(imageView, 2, 0, 1, 1);
        
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(5);
        buttonHBox.setPadding(new Insets(30, 0, 0, 0));
        buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
        newButton = new Button("New...");
        openButton = new Button("Open");
        
        buttonHBox.getChildren().addAll(newButton, openButton);
        
        gridPane.add(buttonHBox, 0, 1, 3, 1);
        gridPane.setAlignment(Pos.CENTER);
        Rectangle border = new Rectangle(0, 0, 500, 400);
        border.setFill(Color.LIGHTBLUE);
        border.setStrokeWidth(5);
        border.setStroke(Color.GRAY);
        
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(border, gridPane);
        
        this.setCenter(stackPane);
    }

    public ListView<String> getListView() {
        return listView;
    }


    public Button getNewButton() {
        return newButton;
    }

    public Button getOpenButton() {
        return openButton;
    }

    public Stage getStage() {
        return stage;
    }
    
 
    
   
}
