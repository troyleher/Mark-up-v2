/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Troy
 */
public class AnnotationEditingDialog {
    private Stage stage ;

    public AnnotationEditingDialog() {
        
        VBox vBox = new VBox();
        Text text = new Text("Text info");
        vBox.getChildren().add(text);
        stage = new Stage();
        stage.setScene(new Scene(vBox, 250, 100));
        stage.initModality(Modality.APPLICATION_MODAL);
        
    }
    
    public void show(){
        stage.showAndWait();
    }
    
    
}
