/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.SystemConfigBean;
import org.troy.markup.model.SystemConfigWrapper;
import org.troy.markup.view.MainView;
import org.troy.markup.view.NewProjectView;

/**
 *
 * @author Troy
 */
public class NewProjectController {

    private NewProjectView newProjectView;
    private SystemConfigBean scb = SystemConfigBean.createInstance();
    private BeanManager bm = BeanManager.createInstance();

    public NewProjectController(NewProjectView npv) {
        newProjectView = npv;
        initEvents();
    }

    private void initEvents() {
        Button selectButton = newProjectView.getSelectButton();
        Button createButton = newProjectView.getCreateButton();
        selectButton.addEventHandler(ActionEvent.ANY, e -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(scb.getInitialDirectory()));
            fc.setTitle("Select image");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", scb.getImageExtensions()));
            File imageFile = fc.showOpenDialog(newProjectView.getScene().getWindow());
            if (imageFile != null) {
                if (imageFile.exists() && imageFile.canWrite()) {
                    //System.out.println(imageFile.getAbsolutePath());
                    bm.setImagePath(imageFile.getAbsolutePath());
                    scb.setInitialDirectory(imageFile.getParent());
                    scb.setInitialFileName(imageFile.getName().replaceAll("\\.\\w*", "") + ".xml");
                    TextField imagePathField = newProjectView.getImagePathField();
                    imagePathField.clear();
                    imagePathField.textProperty().bind(bm.imagePathProperty());
                    createButton.setDisable(false);
                } else {
                    createButton.setDisable(true);
                }
            } else {
                createButton.setDisable(true);
            }
        });


    }

}
