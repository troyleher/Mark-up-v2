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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
import javax.xml.bind.Unmarshaller;
import org.troy.markup.memento.*;
import org.troy.markup.model.*;
import org.troy.markup.state.*;
import org.troy.markup.utilities.Utilities;
import org.troy.markup.view.*;

/**
 * @author Troy
 */
public class MainController extends Application implements Controller {

    private ImageView imageView;
    private Pane imagePane;
    private BeanManager bm;
    private UndoRedoManager urm;
    private ContextMenu tableViewContextMenu;
    private MenuItem deleteMenuItem;
    private TableView<Annotation> tableView;
    private ConfigurationBean configBean;
    private boolean fileHasChanged = false;

    private final static String SELECTED_INDEX = "SELECTED_INDEX";

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        configBean = ConfigurationBean.createInstance();
        bm = BeanManager.createInstance();
        urm = UndoRedoManagerImpl.getInstance();
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
                    displaySaveFileChooser(primaryStage);
                } else {

                }
                shouldSave.close();

            }
        });
        displayWelcomeWindow(primaryStage);
    }

    private void displayWelcomeWindow(Stage primaryStage) {
        SystemConfigWrapper scw = null;
        try {
            JAXBContext context = JAXBContext.newInstance(SystemConfigWrapper.class);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            scw = (SystemConfigWrapper) unMarshaller.unmarshal(MainController.class.getResource("/system/recentFiles.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Display inner dialog with new button  or list of previous files
        BorderPane borderPane1 = new BorderPane();
        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();

        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        ObservableList<String> list = null;
        if (scw != null) {
            list = scw.getRecentFileList();
        }

        ListView<String> listView = new ListView<>(list);
        listView.setMaxHeight(150);
        gridPane.add(listView, 0, 0, 1, 1);

        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(new Image("/images/test.png"));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        gridPane.add(imageView, 2, 0, 1, 1);

        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(5);
        buttonHBox.setPadding(new Insets(30, 0, 0, 0));
        buttonHBox.setAlignment(Pos.BASELINE_RIGHT);
        Button newButton = new Button("New...");
        Button openButton = new Button("Open");
        openButton.addEventHandler(ActionEvent.ACTION, e -> {
            try {
                displayOpenFileChooser(primaryStage);
                displayMainWindow(primaryStage);
            } catch (JAXBException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        buttonHBox.getChildren().addAll(newButton, openButton);

        gridPane.add(buttonHBox, 0, 1, 3, 1);
        gridPane.setAlignment(Pos.CENTER);
        Rectangle border = new Rectangle(0, 0, 500, 400);
        border.setFill(Color.LIGHTBLUE);
        border.setStrokeWidth(5);
        border.setStroke(Color.GRAY);

        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(border, gridPane);

        borderPane1.setCenter(stackPane);
        primaryStage.setScene(new Scene(borderPane1));

        primaryStage.setTitle(configBean.getMainFrameTitle());
        primaryStage.show();
    }

    private void displayMainWindow(Stage primaryStage) {
        javafx.scene.image.ImageView imageView;
        BorderPane borderPane = new BorderPane();
        imagePane = new AnchorPane();
        borderPane.setCenter(imagePane);
        setUpMenuBar(borderPane, primaryStage);
        //Load a sample image for development only
        //Image image = new Image("/images/test.png");
        File imageFile = new File(configBean.getImagePath());
        Image image = new Image(imageFile.toURI().toString());
        imageView = new ImageView(image, this);
        imagePane.getChildren().add(imageView);
        setUpChangeListenerForChangingList();
        setUpAnnotationViewNodes(bm.getAnnotationList());
        setUpTableView(borderPane);
        primaryStage.setScene(new Scene(borderPane));
        Font.loadFont(MainController.class.getResource("/fonts/FreeSerif.ttf").toExternalForm(), 10);
        primaryStage.setTitle(configBean.getMainFrameTitle());
        primaryStage.titleProperty().bind(configBean.mainFrameTitle().
                concat(" ").concat(configBean.fileLocationProperty()));
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.show();

    }

    private void setUpTableView(BorderPane borderPane) {
        //Load table view
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Set up context menu for table view
        tableViewContextMenu = new ContextMenu();
        deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.addEventHandler(ActionEvent.ACTION, this::deleteButtonAction);
        tableViewContextMenu.getItems().add(deleteMenuItem);
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                if (tableViewContextMenu.isShowing()) {
                    tableViewContextMenu.hide();
                }
                int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    tableViewContextMenu.show(tableView, e.getScreenX(), e.getScreenY());
                    deleteMenuItem.getProperties().put(SELECTED_INDEX, selectedIndex);
                }
            }
            if (e.getButton() == MouseButton.PRIMARY || e.getButton() == MouseButton.SECONDARY) {
                //Reset states of all other annotations
                for (Annotation a : bm.getAnnotationList()) {
                    new AnnotationMouseDefaultState().changeEffects(a);
                }
                //Higlight selected annotation
                int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                ObservableList<Annotation> aList = bm.getAnnotationList();
                Annotation a = aList.get(selectedIndex);
                new AnnotationTableRowSelectedState().changeEffects(a);
                //System.out.println("State changed");
                bm.setAnnotationList(bm.getAnnotationList());
            }
        });
        TableColumn<Annotation, String> symbolColumn = new TableColumn<>("Annotation");
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));

        TableColumn<Annotation, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(BeanManager.createInstance().getAnnotationList());

        tableView.getColumns().addAll(symbolColumn, descriptionColumn);
        borderPane.setRight(tableView);
    }

    private void setUpMenuBar(BorderPane borderPane, Stage stage) {
        //Set up and menu bar
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);

        //create menu
        //Open Menu
        MenuItem openMenuItem = new MenuItem("Open...");
        fileMenu.getItems().add(openMenuItem);
        openMenuItem.addEventHandler(ActionEvent.ACTION, e -> {
            try {
                displayOpenFileChooser(stage);

            } catch (JAXBException ex) {
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setContentText("An error occured trying to open the file");
                errorDialog.showAndWait();
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        //Save Menu
        MenuItem saveMenuItem = new MenuItem("Save");
        fileMenu.getItems().add(saveMenuItem);
        saveMenuItem.addEventHandler(ActionEvent.ACTION, e -> {
            displaySaveFileChooser(stage);
        });

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
            ObservableList<Annotation> redoList2 = urm.redo();
            for (Annotation a : redoList2) {
                setUpAnnotationCircleMouseEvents(a);
                //setUpEditingPropertyListeners(a);
            }

            BeanManager.createInstance().setAnnotationList(redoList2);

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
            for (Annotation a : bm.getAnnotationList()) {
                setUpAnnotationCircleMouseEvents(a);
            }
        });
        editMenu.getItems().addAll(redoMenuItem, undoMenuItem);
        menuBar.getMenus().add(editMenu);
        borderPane.setTop(menuBar);
    }

    private void displaySaveFileChooser(Stage stage) {
        ConfigurationBean configBean = ConfigurationBean.createInstance();
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(configBean.getInitialDirectory()));
        //System.out.println(config.getInitialDirectory()+config.getInitialFileName());
        fc.setInitialFileName(configBean.getInitialFileName());
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mark up files",
                configBean.getFileExtensions()));
        File fileToSave = fc.showSaveDialog(stage);
        try {
            JAXBContext annotContext = JAXBContext.newInstance(Annotations.class);
            JAXBContext systemContext = JAXBContext.newInstance(SystemConfigWrapper.class);
          
            if (fileToSave == null) {
                return;
            }
            if (!fileToSave.exists()) {
                fileToSave.createNewFile();
            }
            if (fileToSave.canWrite()) {
                configBean.setInitialDirectory(fileToSave.getParent());
                Marshaller marshaller = annotContext.createMarshaller();
                // output pretty printed
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                //Create jaxb compatabile list
                Annotations annotations = new Annotations();
                annotations.setAnotations(bm.getAnnotationList());
                marshaller.marshal(annotations, fileToSave);
                fileHasChanged = false;
                
                
                //Add saved file name to list of saved files
                SystemConfigWrapper scw = SystemConfigWrapper.createInstance();
                ObservableList<String> recentFileList = scw.getRecentFileList();
                String fileName = configBean.getInitialDirectory() + configBean.getInitialFileName();
                //Remove matching fileName
                if(recentFileList.contains(fileName)){
                    recentFileList.remove(fileName);
                }
                if(recentFileList.size() == 10){
                    recentFileList.remove(0);
                }
                
                recentFileList.add(fileName);
                Marshaller system = systemContext.createMarshaller();
                File systemFile = new File(MainController.class.getResource("/system/recentFiles.xml").toURI());
                system.marshal(scw, systemFile);

            }

        } catch (Exception ex) {
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setContentText("An error occured trying to save file");
            errorDialog.showAndWait();
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayOpenFileChooser(Stage stage) throws JAXBException {
        ConfigurationBean config = ConfigurationBean.createInstance();
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(config.getInitialDirectory()));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mark up files",
                config.getFileExtensions()));
        File file = fc.showOpenDialog(stage);
        if (file == null) {
            return;
        }
        JAXBContext context = JAXBContext.newInstance(Annotations.class);
        Unmarshaller m = context.createUnmarshaller();
        Annotations annotations = (Annotations) m.unmarshal(file);
        ObservableList<Annotation> aList = FXCollections.observableArrayList(annotations.getAnotations());
        for (Annotation a : aList) {
            setUpAnnotationCircleMouseEvents(a);
            new AnnotationMouseDefaultState().changeEffects(a);

        }
        bm.setAnnotationList(aList);
        //System.out.println(file.getParent());
        config.setInitialDirectory(file.getParent());
        config.setInitialFileName(file.getName());
    }

    private void setUpChangeListenerForChangingList() {
        //Set up listener for annotation list

        ObservableList<Annotation> aList = bm.getAnnotationList();
        aList.addListener((ListChangeListener.Change<? extends Annotation> c) -> {
            if (c.next()) {
                if (c.wasAdded()) {
                    ObservableList<Annotation> addedList = FXCollections.observableArrayList(c.getAddedSubList());
                    setUpAnnotationViewNodes(addedList);
                }
                if (c.wasRemoved() || c.wasUpdated()) {
                    List<? extends Annotation> removedList = c.getRemoved();
                    removedList.stream().forEach((a) -> {
                        imagePane.getChildren().remove((Node) a.getProperties().get(Annotation.GROUP_NODE));
                    });

                    //Remove all nodes
                    for (Annotation a : c.getList()) {
                        imagePane.getChildren().remove((Node) a.getProperties().get(Annotation.GROUP_NODE));
                    }

                    //Reletter list and display new modified list on screen
                    Utilities.reLetter(bm.getAnnotationList());
                    for (Annotation a : c.getList()) {
                        a.getProperties().put(Annotation.GROUP_NODE, createViewGroupNode(a));
                        imagePane.getChildren().add((Node) a.getProperties().get(Annotation.GROUP_NODE));
                    }
                }
                fileHasChanged = true;
            }
        });
    }

    private void setUpAnnotationViewNodes(ObservableList<Annotation> aList) {
        for (Annotation a : aList) {
            a.getProperties().put(Annotation.GROUP_NODE, createViewGroupNode(a));
            imagePane.getChildren().add((Node) a.getProperties().get(Annotation.GROUP_NODE));
        }

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
            ObservableList<Annotation> aList = bm.getAnnotationList();
            bm.setAnnotationList(Utilities.reLetter(aList));
            Annotation a2 = new Annotation(r.getX(), r.getY(), r.getWidth(), r.getHeight());
            setUpAnnotationCircleMouseEvents(a2);
            //setUpEditingPropertyListeners(a2);
            bm.addAnnotationToList(a2);
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
        /**
         * for(Annotation a : bm.getAnnotationList()){
         * setUpAnnotationCircleMouseEvents(a); }
         *
         */
        urm.save(aList);
    }

    private void setUpAnnotationCircleMouseEvents(Annotation a) {
        AnnotationCircleBean circle = a.getCircle();
        circle.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            new AnnotationMouseEnteredState().changeEffects(a);
            tableView.getSelectionModel().clearAndSelect(bm.getAnnotationList().indexOf(a));

        });
        circle.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            new AnnotationMouseDefaultState().changeEffects(a);
        });
        circle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            circle.setIsCirclePressed(true);

        });
        circle.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            circle.setCircleMoved(true);
            circle.setIsCirclePressed(false);
            urm.save(bm.getAnnotationList());
            circle.setCircleMoved(false);
        });
        circle.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            circle.setXPos(e.getX());
            circle.setYPos(e.getY());
        });
        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getClickCount() == 2) {
                AnnotationEditingDialog aed = new AnnotationEditingDialog(a);
                // System.out.println(e.getX());
                //System.out.println(a.getRectangle().getX() + e.getX());
                aed.getStage().setX(e.getScreenX() + a.getRectangle().getWidth() + 20);
                aed.getStage().setY(e.getScreenY() + a.getRectangle().getHeight() + 20);
                aed.show();
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                ContextMenu popupMenu = new ContextMenu();
                MenuItem deleteMenu = new MenuItem("Delete");
                deleteMenu.addEventHandler(ActionEvent.ACTION, me -> {
                    bm.getAnnotationList().remove(a);
                });
                popupMenu.getItems().add(deleteMenu);
                popupMenu.show(imagePane, e.getScreenX(), e.getScreenY());

            }
        });

    }

    /**
     * Creates a single consolidated node that represents the view of the
     * annotation
     *
     * @param a
     * @return The node to display an annotation
     */
    private Node createViewGroupNode(Annotation a) {
        Group group = new Group();
        ObservableList<Node> children = group.getChildren();
        children.add(a.getRectangle());
        children.add(createTextNode(a));
        children.add(a.getCircle());
        return group;
    }

    private Text createTextNode(Annotation a) {
        Text text = new Text(a.getSymbol());
        text.xProperty().bind(a.getCircle().centerXProperty().subtract(+5));
        text.yProperty().bind(a.getCircle().centerYProperty().add(5));
        return text;
    }

}
