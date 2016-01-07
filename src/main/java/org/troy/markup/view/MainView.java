/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import java.io.File;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.troy.markup.controller.FileChooserController;
import org.troy.markup.controller.Main;
import org.troy.markup.memento.UndoRedoManager;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.SystemConfigBean;

/**
 *
 * @author Troy
 */
public class MainView extends BorderPane {

    private AnnotationImageView imageView;
    private Pane imagePane;
    private BeanManager bm = BeanManager.createInstance();
    private UndoRedoManager urm;
    private ContextMenu tableViewContextMenu;
    private MenuItem deleteMenuItem;
    private MenuItem openMenuItem;
    private MenuItem saveMenuItem;
    private MenuItem pageSetupMenuItem;
    private MenuItem printMenuItem;
    private Menu editMenu;
    private MenuItem redoMenuItem;
    private MenuItem undoMenuItem;
    private TableView<Annotation> tableView;
    private SystemConfigBean configBean = SystemConfigBean.createInstance();
    private FileChooserController fcc;
    private Stage primaryStage;

    public MainView(Stage primaryStage) {
        //this.mc = mc;
        this.primaryStage = primaryStage;
        initGUI();
    }

    private void initGUI() {
        imagePane = new AnchorPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(imagePane);
        this.setCenter(scrollPane);
        setUpMenuBar();
        //Load a sample image for development only
        //Image image = new Image("/images/test.png");
        File imageFile = new File(bm.getImagePath());
        Image image = new Image(imageFile.toURI().toString());
        imageView = new AnnotationImageView(image);
        
        imagePane.getChildren().add(imageView);
        //mc.initAnnotations(bm.getAnnotationList());
        setUpTableView(this);
        primaryStage.setScene(new Scene(this));
        Font.loadFont(Main.class.getResource("/fonts/FreeSerif.ttf").toExternalForm(), 10);
        //primaryStage.setTitle(configBean.getMainFrameTitle());
        primaryStage.titleProperty().bind(configBean.mainFrameTitle().
                concat(" ").concat(configBean.fileLocationProperty()));
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
//        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    private void setUpTableView(BorderPane borderPane) {
        //Load table view
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Set up context menu for table view
        tableViewContextMenu = new ContextMenu();
        deleteMenuItem = new MenuItem("Delete");
        tableViewContextMenu.getItems().add(deleteMenuItem);
        
        TableColumn<Annotation, String> symbolColumn = new TableColumn<>("Annotation");
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));

        TableColumn<Annotation, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(BeanManager.createInstance().getAnnotationList());

        tableView.getColumns().addAll(symbolColumn, descriptionColumn);
        borderPane.setRight(tableView);
    }
    
    private void setUpMenuBar() {
        //Set up and menu bar
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);

        //Open Menu
        openMenuItem = new MenuItem("Open...");
        fileMenu.getItems().add(openMenuItem);

        //Save Menu
        saveMenuItem = new MenuItem("Save");
        fileMenu.getItems().add(saveMenuItem);
        
        SeparatorMenuItem seperator = new SeparatorMenuItem();
        fileMenu.getItems().add(seperator);
        
        pageSetupMenuItem = new MenuItem("Page Setup...");
        fileMenu.getItems().add(pageSetupMenuItem);
        
        printMenuItem = new MenuItem("Print...");
        fileMenu.getItems().add(printMenuItem);
       
        //Edit menu item
        editMenu = new Menu("Edit");
        redoMenuItem = new MenuItem("redo");
        redoMenuItem.setDisable(true);
        
        //Undo menu item
        undoMenuItem = new MenuItem("undo");
        undoMenuItem.setDisable(true);
        
        editMenu.getItems().addAll(redoMenuItem, undoMenuItem);
        menuBar.getMenus().add(editMenu);
        this.setTop(menuBar);
    }

    public TableView<Annotation> getTableView() {
        return tableView;
    }

    public ContextMenu getTableViewContextMenu() {
        return tableViewContextMenu;
    }

    public MenuItem getDeleteMenuItem() {
        return deleteMenuItem;
    }

    public MenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public MenuItem getSaveMenuItem() {
        return saveMenuItem;
    }

    public MenuItem getRedoMenuItem() {
        return redoMenuItem;
    }

    public MenuItem getUndoMenuItem() {
        return undoMenuItem;
    }

    public Pane getImagePane() {
        return imagePane;
    }

    public AnnotationImageView getImageView() {
        return imageView;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public MenuItem getPageSetupMenuItem() {
        return pageSetupMenuItem;
    }

    public MenuItem getPrintMenuItem() {
        return printMenuItem;
    }
    
    
    
    

 
    
    
    
    

}
