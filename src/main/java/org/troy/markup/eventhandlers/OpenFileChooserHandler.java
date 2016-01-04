/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.eventhandlers;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.troy.markup.controller.MainController;
import org.troy.markup.controller.WelcomeViewController;
import org.troy.markup.dao.AnnotationDAO;
import org.troy.markup.dao.AnnotationDAOJAXB;
import org.troy.markup.model.Annotations;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.SystemConfigBean;
import org.troy.markup.view.MainView;

/**
 *
 * @author Troy
 */
public class OpenFileChooserHandler implements EventHandler<ActionEvent> {

    private BeanManager bm;
    private Stage stage;

    public OpenFileChooserHandler(Stage stage) {
        bm = BeanManager.createInstance();
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        SystemConfigBean scb = SystemConfigBean.createInstance();
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(scb.getInitialDirectory()));
        fc.setInitialFileName(null);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mark up", scb.getFileExtensions()));
        fc.setTitle("Open");
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            try {
                //Update Config
                scb.setInitialDirectory(file.getParent());
                scb.setInitialFileName(file.getName());

                AnnotationDAO dao = new AnnotationDAOJAXB();

                Annotations a = dao.getAnnotations(file);
                MainView mainView = new MainView(stage);
                MainController mc = new MainController(mainView);
                bm.setAnnotationList(FXCollections.observableArrayList(a.getAnotations()));
                mc.initAnnotations(bm.getAnnotationList());
            } catch (Exception ex) {
                Logger.getLogger(OpenFileChooserHandler.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(ex.getCause().getMessage());
                alert.showAndWait();
                alert.close();
            }
        }
    }

}
