/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.troy.markup.memento.UndoRedoManagerImpl;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.ConfigurationBean;

/**
 *
 * @author Troy
 */
public class AnnotationEditingDialog {

    private Stage stage;
    private BeanManager bm = BeanManager.createInstance();
    private IndexRange selectedIndexRange;
    private int caretPos ;

    public AnnotationEditingDialog(Annotation a) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setAlignment(Pos.CENTER);

        //editing fields
        Label editLabel = new Label("Desc");
        gridPane.add(editLabel, 0, 1);
        TextField editField = new TextField(a.getDescription());
        editField.setFont(new Font("FreeSerif", 15));
        gridPane.add(editField, 1, 1, 2, 1);

        editField.addEventHandler(ActionEvent.ACTION, e -> {
            save(a, editField);
        });
        editField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                selectedIndexRange = editField.getSelection();
                caretPos = editField.getCaretPosition();
                //System.out.println(selectedIndexRange);
            }else{
                
            }
        });

        //Special characters
        HBox specialCharBox = new HBox();
        specialCharBox.setAlignment(Pos.BASELINE_LEFT);
        specialCharBox.setSpacing(10);
        List<String> specialCharList = ConfigurationBean.createInstance().getSpecialCharList();
        for (String character : specialCharList) {
            Button b = new Button(character);
            b.setFont(new Font("FreeSerif", 12));
            b.setMinWidth(30);
            b.addEventHandler(ActionEvent.ACTION, e -> {
                String specialChar = ((Button) e.getSource()).getText();
                editField.requestFocus();
                if((selectedIndexRange.getEnd()- selectedIndexRange.getStart()> 0)){
                    editField.positionCaret(caretPos);
                    editField.replaceText(selectedIndexRange, "");
                    
                }else{
                    editField.positionCaret(caretPos);
                
                }
                editField.insertText(editField.getCaretPosition(), specialChar);
             });
            specialCharBox.getChildren().add(b);

        }
        gridPane.add(specialCharBox, 0, 0, 2, 1);

        //Set up buttons
        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
        buttonHBox.setSpacing(10);
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        cancelButton.addEventHandler(ActionEvent.ACTION, e -> {
            stage.close();
        });

        buttonHBox.getChildren().addAll(saveButton, cancelButton);
        gridPane.add(buttonHBox, 2, 2);
        saveButton.addEventFilter(ActionEvent.ACTION, e -> {
            save(a, editField);

        });
        stage = new Stage();
        stage.setScene(new Scene(gridPane, 250, 100));
        stage.setTitle("Editing Dialog");
        stage.initModality(Modality.APPLICATION_MODAL);

    }

    private void save(Annotation a, TextField editField) {
        a.setDescription(editField.getText());
        UndoRedoManagerImpl.getInstance().save(BeanManager.createInstance().getAnnotationList());
        stage.close();
    }

    public void show() {
        stage.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }

}
