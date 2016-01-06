/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Troy
 */
public class NewProjectView extends BorderPane{

    private Button selectButton;
    private Button createButton;
    private Button cancelButton;
    private TextField imagePathField;
    public NewProjectView() {
        initGUI();
    }
    private void initGUI(){
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setPadding(new Insets(40, 20 , 20, 20));
        this.setCenter(gridPane);
        
        HBox hBox1 = new HBox();
        hBox1.setSpacing(5);
        imagePathField = new TextField("Image path...");
        imagePathField.setDisable(true);
        imagePathField.setMinWidth(250);
        selectButton = new Button("Select...");
        hBox1.getChildren().addAll(imagePathField, selectButton);
        gridPane.add(hBox1, 0, 0, 2, 1);
        
        //Setup buttons
        HBox buttonHBox = new HBox(5);
        buttonHBox.setPadding(new Insets(20, 0, 0, 0));
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);
        cancelButton = new Button("Cancel");
        createButton = new Button("Create");
        createButton.setDisable(true);
        buttonHBox.getChildren().addAll(createButton, cancelButton);
        gridPane.add(buttonHBox, 0, 1, 2, 1);
        this.setMinHeight(200);
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public TextField getImagePathField() {
        return imagePathField;
    }

    public Button getCreateButton() {
        return createButton;
    }
    
    
    
    
    
}
