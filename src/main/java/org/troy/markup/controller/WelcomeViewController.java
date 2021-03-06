/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;
import org.troy.markup.dao.AnnotationDAO;
import org.troy.markup.dao.AnnotationDAOJAXB;
import org.troy.markup.eventhandlers.OpenFileChooserHandler;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.Annotations;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.SystemConfigBean;
import org.troy.markup.view.MainView;
import org.troy.markup.view.NewProjectView;
import org.troy.markup.view.WelcomeView;

/**
 *
 * @author Troy
 */
public class WelcomeViewController {

    private WelcomeView welcomeView;
    private ListView<String> listView;
    private String selectedFileName;
    private SystemConfigBean scb = SystemConfigBean.createInstance();
    private BeanManager bm = BeanManager.createInstance();

    public WelcomeViewController(WelcomeView wv) {
        welcomeView = wv;
        listView = welcomeView.getListView();
        setUpDefaultState();
        initEventHandlers();
        initGUI();
    }

    private void initEventHandlers() {

        //Open button
        Button selectButton = welcomeView.getSelectBtn();
        OpenFileChooserHandler ofch = new OpenFileChooserHandler(welcomeView.getStage());
        ofch.setFileExt(FXCollections.observableArrayList(scb.getFileExtensions()));
        ofch.setFileExtDescription("Mark Up");
        selectButton.addEventHandler(ActionEvent.ACTION, ofch);

        //ListView recent file list
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedFileName = newValue;
                if (!newValue.isEmpty()) {
                    welcomeView.getOpenRecentFileButton().setDisable(false);
                }
            }
        });
        listView.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getClickCount() == 2) {
                if (selectedFileName != null && !selectedFileName.isEmpty()) {
                    try {
                        openRecentFile();
                    } catch (Exception exception) {
                        Logger.getLogger(WelcomeViewController.class.getName()).log(Level.SEVERE, null, exception);
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("An error occured!");
                        alert.setContentText(exception.getCause().getMessage());
                        alert.showAndWait();
                        alert.close();
                    }
                }
            }
        });
        
        welcomeView.getOpenRecentFileButton().addEventHandler(ActionEvent.ACTION, e -> {
            try {
                openRecentFile();
            } catch (JAXBException ex) {
                Logger.getLogger(WelcomeViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //List view listener to swap thumbnail image
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //read in xml file
                //lookup image that is assocaited with this file
                //if image exist show image in thumbnail
                //else show default image

                AnnotationDAO dao = new AnnotationDAOJAXB();
                File fileToOpen = new File(listView.getSelectionModel().getSelectedItem());
                if (fileToOpen.exists() && fileToOpen.canRead()) {
                    try {
                        Annotations annotations = dao.getAnnotations(fileToOpen);
                        File imagePath = new File(annotations.getImagePath());
                        ImageView imageView = welcomeView.getImageView();
                        if(imagePath.exists()){
                            Image thumbnail = new Image(imagePath.toURI().toString(), imageView.getFitWidth(), imageView.getFitHeight(), false, true);
                            System.out.println(thumbnail.getHeight());
                            imageView.setImage(thumbnail);
                        }else{
                            imageView.setImage(new Image("/images/noimage_thumbnail.jpg"));
                        }
                    } catch (JAXBException ex) {
                        Logger.getLogger(WelcomeViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });

        //List view listener to swap thumbnail image
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //read in xml file
                //lookup image that is assocaited with this file
                //if image exist show image in thumbnail
                //else show default image

                AnnotationDAO dao = new AnnotationDAOJAXB();
                File fileToOpen = new File(listView.getSelectionModel().getSelectedItem());
                if (fileToOpen.exists() && fileToOpen.canRead()) {
                    try {
                        Annotations annotations = dao.getAnnotations(fileToOpen);
                        File imagePath = new File(annotations.getImagePath());
                        ImageView imageView = welcomeView.getImageView();
                        if(imagePath.exists()){
                            Image thumbnail = new Image(imagePath.toURI().toString(), imageView.getFitWidth(), imageView.getFitHeight(), false, true);
                            System.out.println(thumbnail.getHeight());
                            imageView.setImage(thumbnail);
                        }else{
                            imageView.setImage(new Image("/images/noimage_thumbnail.jpg"));
                        }
                    } catch (JAXBException ex) {
                        Logger.getLogger(WelcomeViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });

        //New button
        Button newButton = welcomeView.getNewButton();
        newButton.addEventHandler(ActionEvent.ANY, e -> {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            NewProjectView newView = new NewProjectView();
            NewProjectController npc = new NewProjectController(newView);
            dialogStage.setScene(new Scene(newView));
            newView.getCancelButton().addEventHandler(ActionEvent.ANY, event -> {
                dialogStage.close();
            });
            newView.getCreateButton().addEventFilter(ActionEvent.ANY, event -> {
                dialogStage.close();
                Stage stage = (Stage) welcomeView.getScene().getWindow();
                MainView mainView = new MainView(stage);
                MainController mainController = new MainController(mainView);
            });
            dialogStage.showAndWait();
        }
        );
    }

    private void openRecentFile() throws JAXBException {
        AnnotationDAO dao = new AnnotationDAOJAXB();
        File file = new File(selectedFileName);
        scb.setInitialDirectory(file.getParent());
        scb.setInitialFileName(file.getName());
        Annotations a = dao.getAnnotations(file);
        ObservableList<Annotation> aList = FXCollections.observableArrayList(a.getAnotations());
        bm.setAnnotationList(aList);
        bm.setImagePath(a.getImagePath());
        welcomeView.getStage().setScene(null);
        MainView mv = new MainView(welcomeView.getStage());
        MainController mc = new MainController(mv);
        mc.initAnnotations(bm.getAnnotationList());
        bm.setFileChanged(false);
    }

    private void initGUI() {
        listView.setItems(SystemConfigBean.createInstance().getRecentFileList());
    }

    private void setUpDefaultState() {
        welcomeView.getOpenRecentFileButton().setDisable(true);
    }
}
