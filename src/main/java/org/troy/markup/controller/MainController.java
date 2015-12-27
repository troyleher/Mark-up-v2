/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import java.util.List;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.troy.markup.memento.UndoRedoManager;
import org.troy.markup.memento.UndoRedoManagerImpl;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.ConfigurationBean;
import org.troy.markup.utilities.Utilities;
import org.troy.markup.view.ImageView;

/**
 *
 * @author Troy
 */
public class MainController extends Application implements Controller {

    private ImageView imageView;
    private Pane imagePane;
    private BeanManager bm;
    private UndoRedoManager urm;
    private ContextMenu tableViewContextMenu;
    private MenuItem deleteMenuItem;

    private final static String SELECTED_INDEX = "SELECTED_INDEX";

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigurationBean configBean = ConfigurationBean.createInstance();
        bm = BeanManager.createInstance();
        urm = UndoRedoManagerImpl.getInstance();
        BorderPane borderPane = new BorderPane();

        imagePane = new AnchorPane();
        borderPane.setCenter(imagePane);

        setUpMenuBar(borderPane);

        //Load a sample image for development only
        Image image = new Image("/images/test.png");
        imageView = new ImageView(image, this);
        imagePane.getChildren().add(imageView);

        setUpChangeListenerForChangingList();

        //Load table view
        TableView<Annotation> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Set up context menu for tableview
        tableViewContextMenu = new ContextMenu();
        deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.addEventHandler(ActionEvent.ACTION, this::deleteButtonAction);

        tableViewContextMenu.getItems().add(deleteMenuItem);
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                if (tableViewContextMenu.isShowing()) {
                    tableViewContextMenu.hide();
                }
                if (tableView.getSelectionModel().getSelectedIndex() >= 0) {
                    tableViewContextMenu.show(tableView, e.getScreenX(), e.getScreenY());
                    deleteMenuItem.getProperties().put(SELECTED_INDEX, tableView.getSelectionModel().getSelectedIndex());
                }
            }
        });

        TableColumn<Annotation, String> symbolColumn = new TableColumn<>("Annotation");
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));

        TableColumn<Annotation, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(BeanManager.createInstance().getAnnotationList());

        tableView.getColumns().addAll(symbolColumn, descriptionColumn);
        borderPane.setRight(tableView);

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle(configBean.getMainFrameTitle());
        primaryStage.show();

    }

    private void setUpMenuBar(BorderPane borderPane) {
        //Set up and menu bar
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);

        Menu editMenu = new Menu("Edit");
        MenuItem redoMenuItem = new MenuItem("redo");
        redoMenuItem.setDisable(true);
        ObservableList<ObservableList<Annotation>> redoList = UndoRedoManagerImpl.getInstance().getRedoList();
        redoList.addListener((ListChangeListener.Change<? extends ObservableList<Annotation>> c) -> {
            if (c.getList().isEmpty()) {
                redoMenuItem.setDisable(true);
            } else {
                redoMenuItem.setDisable(false);
            }
        });
        redoMenuItem.addEventHandler(ActionEvent.ACTION, e -> {
            BeanManager.createInstance().setAnnotationList(urm.redo());
        });

        MenuItem undoMenuItem = new MenuItem("undo");
        undoMenuItem.setDisable(true);
        ObservableList<ObservableList<Annotation>> saveList = UndoRedoManagerImpl.getInstance().getSaveList();
        saveList.addListener((ListChangeListener.Change<? extends ObservableList<Annotation>> c) -> {
            if (c.getList().isEmpty()) {
                undoMenuItem.setDisable(true);
            } else {
                undoMenuItem.setDisable(false);
            }
        });
        undoMenuItem.addEventHandler(ActionEvent.ACTION, e -> {
            bm.setAnnotationList(urm.undo());
        });
        editMenu.getItems().addAll(redoMenuItem, undoMenuItem);
        menuBar.getMenus().add(editMenu);
        borderPane.setTop(menuBar);
    }

    private void setUpChangeListenerForChangingList() {
        //Set up listener for annotation list
        //TODO 
        ObservableList<Annotation> aList = bm.getAnnotationList();
        aList.addListener((ListChangeListener.Change<? extends Annotation> c) -> {
            if (c.next()) {
                if (c.wasAdded()) {
                    List<? extends Annotation> addedList = c.getAddedSubList();
                    addedList.stream().forEach((a) -> {
                        imagePane.getChildren().add(a.getDisplableNode());
                    });
                }
                if (c.wasRemoved()) {
                    List<? extends Annotation> removedList = c.getRemoved();
                    removedList.stream().forEach((a) -> {
                        imagePane.getChildren().remove(a.getDisplableNode());
                    });
                }
            }
        });
    }

    /**
     * Creates and displays an annotation on top of the image, it is limited to
     * a rectangle that is greater than 10 x 10 square
     *
     * @param r
     */
    @Override
    public void createAndDisplayAnnotation(Rectangle r) {
        if (r.getWidth() > 10 && r.getHeight() > 10) {
            bm.setAnnotationList(Utilities.reLetter(bm.getAnnotationList()));
            Annotation a = new Annotation(r.getX(), r.getY(), r.getWidth(), r.getHeight());
            bm.addAnnotationToList(a);
            //System.out.printf("Number of elements in list = %s", bm.getAnnotationList().size());
            urm.save(bm.getAnnotationList());
        }
    }

    @Override
    public void displayDraggingRectangle(Rectangle r) {
        imagePane.getChildren().add(r);
    }

    @Override
    public void removeDraggingRectangle(Rectangle r) {
        imagePane.getChildren().remove(r);
    }

    private void deleteButtonAction(ActionEvent e) {
        int indexToDelete = (Integer) deleteMenuItem.getProperties().get(SELECTED_INDEX);
        //System.out.printf("Index to delete %s \n" , index);
        ObservableList<Annotation> aList = bm.getAnnotationList();
        aList.remove(indexToDelete);
        aList = bm.getAnnotationList();
        bm.setAnnotationList(Utilities.reLetter(aList));
        urm.save(aList);
    }

}
