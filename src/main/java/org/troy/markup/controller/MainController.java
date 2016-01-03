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
public class MainController extends Application implements Controller {

    private ImageView imageView;
    private Pane imagePane;
    private BeanManager bm;
    private UndoRedoManager urm;
    private ContextMenu tableViewContextMenu;
    private MenuItem deleteMenuItem;
    private TableView<Annotation> tableView;
    private SystemConfigBean configBean;
    private FileChooserController fcc;
    private boolean fileHasChanged = false;

    private final static String SELECTED_INDEX = "SELECTED_INDEX";
    private final static String SYSTEM_FILE_PATH = "/system/SystemConfig.xml";

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        configBean = SystemConfigBean.createInstance();
        bm = BeanManager.createInstance();
        urm = UndoRedoManagerImpl.getInstance();
        fcc = new FileChooserController(this);
        initStageEvents(primaryStage);
        
        //Load system config information
        JAXBManager.loadStaticClasses(SystemConfigBean.class, SYSTEM_FILE_PATH);
        
        WelcomeView wv = new WelcomeView();
        WelcomeViewController wvc = new WelcomeViewController(wv, this);
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
                    displaySaveFileChooser(primaryStage);
                } else {

                }
                shouldSave.close();

            }
            JAXBManager.saveStaticClasses(SystemConfigBean.class, SYSTEM_FILE_PATH, SystemConfigBean.createInstance());
        });
    }

    private void displayMainWindow(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        imagePane = new AnchorPane();
        borderPane.setCenter(imagePane);
        setUpMenuBar(borderPane, primaryStage);
        //Load a sample image for development only
        Image image = new Image("/images/test.png");
        File imageFile = new File(configBean.getImagePath());
        //Image image = new Image(imageFile.toURI().toString());
        imageView = new ImageView(image, this);
        imagePane.getChildren().add(imageView);
        initAnnotations(bm.getAnnotationList());
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
                    //new AnnotationMouseDefaultState().changeRectangleEffects(a);
                }
                //Higlight selected annotation
                int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                ObservableList<Annotation> aList = bm.getAnnotationList();
                Annotation a = aList.get(selectedIndex);
                //new AnnotationTableRowSelectedState().changeRectangleEffects(a);
                //System.out.println("State changed");
                //bm.setAnnotationList(bm.getAnnotationList());
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
                fcc.displayOpenFileChooser(stage);
            } catch (JAXBException ex) {
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setContentText("An error occured trying to open the file");
                errorDialog.showAndWait();
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,
                        null, ex);
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
            BeanManager.createInstance().setAnnotationList(redoList2);
            initAnnotations(redoList2);

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
            initAnnotations(bm.getAnnotationList());
        });
        editMenu.getItems().addAll(redoMenuItem, undoMenuItem);
        menuBar.getMenus().add(editMenu);
        borderPane.setTop(menuBar);
    }

    private void displaySaveFileChooser(Stage stage) {
        SystemConfigBean configBean = SystemConfigBean.createInstance();
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
                if (recentFileList.contains(fileName)) {
                    recentFileList.remove(fileName);
                }
                if (recentFileList.size() == 10) {
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
            //bm.setAnnotationList(Utilities.reLetter(aList));
            Annotation a2 = new Annotation(r.getX(), r.getY(), r.getWidth(), r.getHeight());
            bm.addAnnotationToList(a2);
            urm.save(bm.getAnnotationList());
            initAnnotations(aList);
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
        // bm.setAnnotationList(Utilities.reLetter(aList));
        /**
         * for(Annotation a : bm.getAnnotationList()){
         * setUpAnnotationCircleMouseEvents(a); }
         *
         */
        urm.save(aList);
    }

    private void initAnnotationCircleEvents(Annotation a, Circle c) {

        c.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            new AnnotationMouseEnteredState().changeCircleEffects(c);
            tableView.getSelectionModel().clearAndSelect(bm.getAnnotationList().indexOf(a));
        });
        c.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            new AnnotationMouseDefaultState().changeCircleEffects(c);
        });
        c.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                a.getCircle().setIsCirclePressed(true);
            }

        });
        c.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                a.getCircle().setCircleMoved(true);
                a.getCircle().setIsCirclePressed(false);
                urm.save(bm.getAnnotationList());
                a.getCircle().setCircleMoved(false);
            }
        });
        c.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                c.setCenterX(e.getX());
                c.setCenterY(e.getY());
            }
        });
        c.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getClickCount() == 2) {
                AnnotationEditingDialog aed = new AnnotationEditingDialog(a);
                aed.getStage().setX(e.getScreenX() + a.getRectangle().getWidth() + 20);
                aed.getStage().setY(e.getScreenY() + a.getRectangle().getHeight() + 20);
                aed.show();
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                ContextMenu popupMenu = new ContextMenu();
                MenuItem deleteMenu = new MenuItem("Delete");
                deleteMenu.addEventHandler(ActionEvent.ACTION, me -> {

                    bm.getAnnotationList().remove(a);
                    urm.save(bm.getAnnotationList());
                });
                popupMenu.getItems().add(deleteMenu);
                popupMenu.show(imagePane, e.getScreenX(), e.getScreenY());
            }
        });
    }

    private void initTextNode(Annotation a, Circle c) {
        Text text = new Text();
        text.textProperty().bindBidirectional(a.symbolProperty());
        text.xProperty().bind(c.centerXProperty().subtract(+5));
        text.yProperty().bind(c.centerYProperty().add(5));
        c.getProperties().put("symbol", text);
        imagePane.getChildren().add(text);
    }

    @Override
    public void initAnnotations(ObservableList<Annotation> aList) {
        initListeners(aList);
        initViewNodes(aList);
    }

    private void initListeners(ObservableList<Annotation> aList) {

        aList.addListener((ListChangeListener.Change<? extends Annotation> c) -> {
            while (c.next()) {
                if (c.wasRemoved()) {
                    List<? extends Annotation> removedList = c.getRemoved();
                    for (Annotation a : removedList) {
                        AnnotationRectangleBean arb = a.getRectangle();
                        AnnotationRectangleView arv = new AnnotationRectangleView(arb.getX(), arb.getY(), arb.getWidth(), arb.getHeight());
                        AnnotationCircleBean acb = a.getCircle();
                        AnnotationCircleView acv = new AnnotationCircleView(acb.getX(), acb.getY(), acb.getRadius());

                        //Hack since remove text node is bugged
                        Node text = null;
                        for (Node n : imagePane.getChildren()) {
                            if (n instanceof AnnotationCircleView) {
                                AnnotationCircleView acv1 = (AnnotationCircleView) n;
                                if (acv1.equals(acv)) {
                                    text = (Node) n.getProperties().get("symbol");
                                }
                            }
                        }
                        imagePane.getChildren().remove(text);
                        imagePane.getChildren().removeAll(arv, acv);
                    }
                    Utilities.reLetter(aList);
                }
                if (c.wasAdded()) {
                    Utilities.reLetter((ObservableList<Annotation>) c.getList());
                }

                fileHasChanged = true;

            }
        });
    }

    private void initViewNodes(ObservableList<Annotation> aList) {
        imagePane.getChildren().retainAll(imageView);
        for (Annotation a : aList) {
            initRectangleViewNode(a);
            initCircleViewNode(a);
        }
    }

    private void initCircleViewNode(Annotation a) {
        AnnotationCircleBean acb = a.getCircle();
        AnnotationCircleView c = new AnnotationCircleView();
        c.centerXProperty().bindBidirectional(acb.xProperty());
        c.centerYProperty().bindBidirectional(acb.yProperty());
        c.radiusProperty().bindBidirectional(acb.radiusProperty());
        initAnnotationCircleEvents(a, c);
        initTextNode(a, c);
        imagePane.getChildren().add(c);
    }

    private void initRectangleViewNode(Annotation a) {
        AnnotationRectangleBean arb = a.getRectangle();
        AnnotationRectangleView r = new AnnotationRectangleView();
        r.xProperty().bindBidirectional(arb.xProperty());
        r.yProperty().bindBidirectional(arb.yProperty());
        r.widthProperty().bindBidirectional(arb.widthProperty());
        r.heightProperty().bindBidirectional(arb.heightProperty());
        initAnnotationRectangleEvents(r);
        imagePane.getChildren().add(r);
    }

    private void initAnnotationRectangleEvents(Rectangle rect) {
        rect.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            new AnnotationMouseEnteredState().changeRectangleEffects(rect);
        });
        rect.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            new AnnotationMouseDefaultState().changeRectangleEffects(rect);
        });
    }

}
