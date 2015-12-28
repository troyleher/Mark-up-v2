/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.BeanManager;

/**
 *
 * @author Troy
 */
public class AnnotationEditingDialog {
    private Stage stage ;
    private BeanManager bm = BeanManager.createInstance();
    public AnnotationEditingDialog(Annotation a) {
        
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        vBox.getChildren().add(hBox);
        TextField editField = new TextField(a.getDescription());
        hBox.getChildren().add(editField);
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        vBox.getChildren().add(buttonBox);
        Button saveButton = new Button("Save");
        saveButton.addEventFilter(ActionEvent.ACTION, e -> {
            a.setDescription(editField.getText());
            
        });
        buttonBox.getChildren().add(saveButton);
        stage = new Stage();
        stage.setScene(new Scene(vBox, 250, 100));
        stage.setTitle("Editing Dialog");
        stage.initModality(Modality.APPLICATION_MODAL);
        
    }
    
    public void show(){
        stage.showAndWait();
    }
    
    
}
