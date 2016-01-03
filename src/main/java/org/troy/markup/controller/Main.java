/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.troy.markup.memento.*;
import org.troy.markup.model.*;
import org.troy.markup.state.*;
import org.troy.markup.utilities.Utilities;
import org.troy.markup.view.*;

/**
 * @author Troy
 */
public class Main extends Application  {

    private AnnotationImageView imageView;
    private Pane imagePane;
    private BeanManager bm;
    private UndoRedoManager urm;
    private ContextMenu tableViewContextMenu;
    private MenuItem deleteMenuItem;
    private TableView<Annotation> tableView;
    private SystemConfigBean configBean;
    private FileChooserController fcc;
    private boolean fileHasChanged = false;

    private final static String SYSTEM_FILE_PATH = "/system/SystemConfig.xml";

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        configBean = SystemConfigBean.createInstance();
        bm = BeanManager.createInstance();
        urm = UndoRedoManagerImpl.getInstance();
        initStageEvents(primaryStage);

        //Load system config information
        JAXBManager.loadStaticClasses(SystemConfigBean.class, SYSTEM_FILE_PATH);
        WelcomeView wv = new WelcomeView(primaryStage);
        WelcomeViewController wvc = new WelcomeViewController(wv);
        primaryStage.setScene(new Scene(wv));
        primaryStage.show();
    }

    private void initStageEvents(Stage primaryStage) {
        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            if (fileHasChanged) {
                //Ask if user would like to save the file
                Alert shouldSave = new Alert(Alert.AlertType.WARNING);
                shouldSave.setContentText("Save changes?");
                shouldSave.getButtonTypes().clear();
                shouldSave.getButtonTypes().add(ButtonType.YES);
                shouldSave.getButtonTypes().add(ButtonType.NO);
                Optional<ButtonType> result = shouldSave.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    //displaySaveFileChooser(primaryStage);
                } else {

                }
                shouldSave.close();
            }
            JAXBManager.saveStaticClasses(SystemConfigBean.class, SYSTEM_FILE_PATH, SystemConfigBean.createInstance());
        });
    }

}
