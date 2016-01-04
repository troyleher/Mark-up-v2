/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import java.io.File;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.troy.markup.dao.AnnotationDAO;
import org.troy.markup.dao.AnnotationDAOJAXB;
import org.troy.markup.eventhandlers.SaveFileChooserHandler;
import org.troy.markup.memento.*;
import org.troy.markup.model.*;
import org.troy.markup.utilities.Utilities;
import org.troy.markup.view.*;

/**
 * @author Troy
 */
public class Main extends Application {

    private BeanManager bm;
    private UndoRedoManager urm;
    private SystemConfigBean cb;
    private final static String SYSTEM_FILE_PATH = "/system/SystemConfig.xml";

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        cb = SystemConfigBean.createInstance();
        bm = BeanManager.createInstance();
        urm = UndoRedoManagerImpl.getInstance();
        initStageEvents(primaryStage);

        //Load system config information
        JAXBManager.loadStaticClasses(SystemConfigBean.class, SYSTEM_FILE_PATH);
        try {
            WelcomeView wv = new WelcomeView(primaryStage);
            WelcomeViewController wvc = new WelcomeViewController(wv);
            primaryStage.setScene(new Scene(wv));
            primaryStage.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setContentText(e.getCause().getMessage());
            alertError.showAndWait();
            alertError.close();
        }
    }

    private void initStageEvents(Stage primaryStage) {
        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            if (bm.isFileChanged()) {
                //Ask if user would like to save the file
                Alert shouldSave = new Alert(Alert.AlertType.WARNING);
                shouldSave.setContentText("Save changes?");
                shouldSave.getButtonTypes().clear();
                shouldSave.getButtonTypes().add(ButtonType.YES);
                shouldSave.getButtonTypes().add(ButtonType.NO);
                Optional<ButtonType> result = shouldSave.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    //If file exists save without prompting
                    String fileAbsolutePath = cb.getInitialDirectory() + "\\" + cb.getInitialFileName();
                    File fileToSave = new File(fileAbsolutePath);
                    if (fileToSave.exists() && fileToSave.canWrite()) {
                        AnnotationDAO dao = new AnnotationDAOJAXB();
                        dao.save(bm.createProjectFile(), fileToSave);
                        Utilities.updateRecentFileList(fileToSave);
                    }
                    //if file does not exist display file chooser
                    if (!fileToSave.exists()) {
                        SaveFileChooserHandler fileChooser = new SaveFileChooserHandler(primaryStage);
                        fileChooser.handle(null);
                    }
                } else {

                }
                shouldSave.close();
            }
            JAXBManager.saveStaticClasses(SystemConfigBean.class, SYSTEM_FILE_PATH, SystemConfigBean.createInstance());
        });
    }

}
