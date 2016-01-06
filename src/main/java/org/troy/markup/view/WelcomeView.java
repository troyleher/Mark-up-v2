/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Troy
 */
public class WelcomeView extends BorderPane{
    private ListView<String> listView;
    private Button newButton;
    private Button openButton;
    private Stage stage;
    private ImageView imageView;
    
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
        
        Label recentLabel = new Label("Recent file List");
        recentLabel.setFont(Font.font("Veranda", FontWeight.BOLD, 15));
        gridPane.add(recentLabel, 0 , 0, 1, 1);
        
        Label imagePreveiwLabel = new Label("Image Preview");
        imagePreveiwLabel.setFont(Font.font("Veranda", FontWeight.BOLD, 15));
        gridPane.add(imagePreveiwLabel, 2, 0, 1, 1);
        
        listView = new ListView<>();
        listView.setMaxHeight(150);
        gridPane.add(listView, 0, 1, 1, 1);
        
        imageView = new javafx.scene.image.ImageView(new Image("/images/noimage_thumbnail.jpg"));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        gridPane.add(imageView, 2, 1, 1, 1);
        
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(5);
        buttonHBox.setPadding(new Insets(30, 0, 0, 0));
        buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
        newButton = new Button("New...");
        openButton = new Button("Open");
        
        buttonHBox.getChildren().addAll(newButton, openButton);
        
        gridPane.add(buttonHBox, 0, 2, 3, 1);
        gridPane.setAlignment(Pos.CENTER);
        Rectangle border = new Rectangle(0, 0, 500, 400);
        border.setFill(Color.LIGHTBLUE);
        border.setStrokeWidth(5);
        border.setStroke(Color.GRAY);
        
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(border, gridPane);
        
        this.setCenter(stackPane);
        stage.setTitle("Welcome");
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

    public ImageView getImageView() {
        return imageView;
    }
    
 
    
   
}
