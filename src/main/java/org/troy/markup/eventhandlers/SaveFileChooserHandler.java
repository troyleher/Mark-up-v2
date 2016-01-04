/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.eventhandlers;

import java.io.File;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.troy.markup.dao.AnnotationDAO;
import org.troy.markup.dao.AnnotationDAOJAXB;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.Annotations;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.SystemConfigBean;
import org.troy.markup.utilities.Utilities;

/**
 *
 * @author Troy
 */
public class SaveFileChooserHandler implements EventHandler<ActionEvent> {

    private Stage stage;
    private SystemConfigBean scb = SystemConfigBean.createInstance();
    private BeanManager bm = BeanManager.createInstance();

    public SaveFileChooserHandler(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        SystemConfigBean configBean = SystemConfigBean.createInstance();
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(configBean.getInitialDirectory()));
        fc.setInitialFileName(configBean.getInitialFileName());
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mark up",
                configBean.getFileExtensions()));
        File fileToSave = fc.showSaveDialog(stage);
        if (fileToSave == null) {
            return;
        }
        scb.setInitialDirectory(fileToSave.getParent());
        scb.setInitialFileName(fileToSave.getName());
        AnnotationDAO dao = new AnnotationDAOJAXB();
        dao.save(bm.createProjectFile(), fileToSave);
        Utilities.updateRecentFileList(fileToSave);
        bm.setFileChanged(false);
    }



}
