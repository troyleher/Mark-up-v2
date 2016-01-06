/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

/**
 *
 * @author Troy
 */
public class WelcomeView extends BorderPane{
    private ListView<String> listView;
    private Button newButton;
    private Button selectButton;
    private Button openRecentFileButton;
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
        
        HBox buttonHBox1 = new HBox();
        buttonHBox1.setSpacing(5);
        buttonHBox1.setPadding(new Insets(30, 0, 0, 0));
        buttonHBox1.setAlignment(Pos.BASELINE_LEFT);
        openRecentFileButton = new Button("Open recent file");
        buttonHBox1.getChildren().add(openRecentFileButton);
        gridPane.add(buttonHBox1, 0, 1, 1, 1);
        
        
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(5);
        buttonHBox.setPadding(new Insets(30, 0, 0, 0));
        buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
        newButton = new Button("New...");
        selectButton = new Button("Select..");
        
        buttonHBox.getChildren().addAll(newButton, selectButton);
        
        gridPane.add(buttonHBox, 1, 1, 2, 1);
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

    public Button getSelect() {
        return selectButton;
    }

    public Stage getStage() {
        return stage;
    }

    public Button getOpenRecentFileButton() {
        return openRecentFileButton;
    }
    
 
    
   
}
