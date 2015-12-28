/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.troy.markup.memento.UndoRedoManagerImpl;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.BeanManager;

/**
 *
 * @author Troy
 */
public class AnnotationEditingDialog {

    private Stage stage;
    private BeanManager bm = BeanManager.createInstance();

    public AnnotationEditingDialog(Annotation a) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setAlignment(Pos.CENTER);

        Label editLabel = new Label("Desc");
        gridPane.add(editLabel, 0, 1);
        TextField editField = new TextField(a.getDescription());
        gridPane.add(editField, 1, 1, 2, 1);

        //Set up buttons
        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
        buttonHBox.setSpacing(10);
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        buttonHBox.getChildren().addAll(saveButton, cancelButton);
        gridPane.add(buttonHBox, 2, 2);
        saveButton.addEventFilter(ActionEvent.ACTION, e -> {
            a.setDescription(editField.getText());
            UndoRedoManagerImpl.getInstance().save(BeanManager.createInstance().getAnnotationList());
            stage.close();

        });
        stage = new Stage();
        stage.setScene(new Scene(gridPane, 250, 100));
        stage.setTitle("Editing Dialog");
        stage.initModality(Modality.APPLICATION_MODAL);

    }

    public void show() {
        stage.showAndWait();
    }

}
