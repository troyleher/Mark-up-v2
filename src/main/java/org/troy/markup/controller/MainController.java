/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
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
import org.troy.markup.model.Annotation;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.ConfigurationBean;
import org.troy.markup.view.ImageView;

/**
 *
 * @author Troy
 */
public class MainController extends Application implements Controller {

    private ImageView imageView;
    private Pane imagePane;
    private BeanManager bm;
    private ContextMenu tableViewContextMenu;

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigurationBean configBean = ConfigurationBean.createInstance();
        bm = BeanManager.createInstance();
        BorderPane borderPane = new BorderPane();

        imagePane = new AnchorPane();
        borderPane.setCenter(imagePane);

        //Load a sample image for development only
        Image image = new Image("/images/test.png");
        imageView = new ImageView(image, this);
        imagePane.getChildren().add(imageView);

        //Load table view
        TableView<Annotation> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Set up context menu for tableview
        tableViewContextMenu = new ContextMenu();
        MenuItem menu = new MenuItem("Test");
        tableViewContextMenu.getItems().add(menu);
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                if (tableViewContextMenu.isShowing()) {
                    tableViewContextMenu.hide();
                }
                if (tableView.getSelectionModel().getSelectedIndex() >= 0) {
                    tableViewContextMenu.show(tableView, e.getScreenX(), e.getScreenY());
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

    public void addAnnotationToView(Annotation a) {

    }

    @Override
    public void createAndDisplayAnnotation(Rectangle r) {
        Annotation a = new Annotation(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        bm.addAnnotationToList(a);
        imagePane.getChildren().add(a.getText());
        imagePane.getChildren().add(a.getCircle());
        imagePane.getChildren().add(a.getRectangle());

    }

    @Override
    public void displayDraggingRectangle(Rectangle r) {
        imagePane.getChildren().add(r);
    }

    @Override
    public void removeDraggingRectangle(Rectangle r) {
        imagePane.getChildren().remove(r);
    }

}
