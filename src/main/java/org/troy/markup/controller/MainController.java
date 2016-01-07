/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import org.troy.markup.eventhandlers.OpenFileChooserHandler;
import org.troy.markup.eventhandlers.SaveFileChooserHandler;
import org.troy.markup.memento.UndoRedoManager;
import org.troy.markup.memento.UndoRedoManagerImpl;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.AnnotationCircleBean;
import org.troy.markup.model.AnnotationRectangleBean;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.SystemConfigBean;
import org.troy.markup.state.AnnotationMouseDefaultState;
import org.troy.markup.state.AnnotationMouseEnteredState;
import org.troy.markup.utilities.Utilities;
import org.troy.markup.view.AnnotationCircleView;
import org.troy.markup.view.AnnotationEditingDialog;
import org.troy.markup.view.AnnotationRectangleView;
import org.troy.markup.view.MainView;

/**
 *
 * @author Troy
 */
public class MainController implements Controller {

    private MainView mv;
    private PrinterJob printerJob;
    private BeanManager bm = BeanManager.createInstance();
    private SystemConfigBean scb = SystemConfigBean.createInstance();
    private UndoRedoManager urm = UndoRedoManagerImpl.getInstance();
    private final static String SELECTED_INDEX = "SELECTED_INDEX";
    private Scale scale;

    public MainController(MainView mv) {
        this.mv = mv;
        initEventHandlers();
        AnnotationImageViewController aivc = new AnnotationImageViewController(mv.getImageView(), this);
    }

    private void initEventHandlers() {
        initMenuItemEventHandlers();
        initTableEventHandlers();

    }

    private void initTableEventHandlers() {
        TableView<Annotation> tableView = mv.getTableView();
        ContextMenu tableViewContextMenu = mv.getTableViewContextMenu();
        MenuItem deleteMenuItem = mv.getDeleteMenuItem();

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
    }

    private void initMenuItemEventHandlers() {
        MenuItem deleteMenuItem = mv.getDeleteMenuItem();
        deleteMenuItem.addEventHandler(ActionEvent.ACTION, e -> {
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
        });

        //Open menu item
        MenuItem openMenuItem = mv.getOpenMenuItem();
        OpenFileChooserHandler ofch = new OpenFileChooserHandler(mv.getPrimaryStage());
        ofch.setFileExt(FXCollections.observableArrayList(scb.getFileExtensions()));
        ofch.setFileExtDescription("Mark Up");
        openMenuItem.addEventHandler(ActionEvent.ACTION, ofch);

        //Save menu item
        MenuItem saveMenuItem = mv.getSaveMenuItem();
        saveMenuItem.addEventHandler(ActionEvent.ACTION, new SaveFileChooserHandler(mv.getPrimaryStage()));

        //Redo menu item
        MenuItem redoMenuItem = mv.getRedoMenuItem();
        //redoMenuItem.addEventHandler(ActionEvent.ACTION, null);
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

        //Undo menu item
        MenuItem undoMenuItem = mv.getUndoMenuItem();
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

        initPrintMenuItems();
    }

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
        mv.getImagePane().getChildren().add(r);
    }

    @Override
    public void removeDraggingRectangle(Rectangle r) {
        mv.getImagePane().getChildren().remove(r);
    }

    private void initAnnotationCircleEvents(Annotation a, Circle c) {

        c.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            new AnnotationMouseEnteredState().changeCircleEffects(c);
            mv.getTableView().getSelectionModel().clearAndSelect(bm.getAnnotationList().indexOf(a));
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
                bm.setFileChanged(true);
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
                    Utilities.reLetter(bm.getAnnotationList());
                    urm.save(bm.getAnnotationList());
                    bm.setFileChanged(true);
                });
                popupMenu.getItems().add(deleteMenu);
                popupMenu.show(mv.getImagePane(), e.getScreenX(), e.getScreenY());
            }
        });
    }

    private void initTextNode(Annotation a, Circle c) {
        Text text = new Text();
        text.setFont(Font.font("verdana ", FontWeight.BOLD, 14));
        text.textProperty().bindBidirectional(a.symbolProperty());
        text.xProperty().bind(c.centerXProperty().subtract(+7));
        text.yProperty().bind(c.centerYProperty().add(5));
        c.getProperties().put("symbol", text);
        mv.getImagePane().getChildren().add(text);
    }

    @Override
    public void initAnnotations(ObservableList<Annotation> aList) {
        bm.setFileChanged(true);
        initListeners(aList);
        initViewNodes(aList);
    }

    private void initListeners(ObservableList<Annotation> aList) {

        aList.addListener((ListChangeListener.Change<? extends Annotation> c) -> {
            Pane imagePane = mv.getImagePane();
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

                //fileHasChanged = true;
            }
        });
    }

    private void initViewNodes(ObservableList<Annotation> aList) {
        mv.getImagePane().getChildren().retainAll(mv.getImageView());
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
        mv.getImagePane().getChildren().add(c);
    }

    private void initRectangleViewNode(Annotation a) {
        AnnotationRectangleBean arb = a.getRectangle();
        AnnotationRectangleView r = new AnnotationRectangleView();
        r.xProperty().bindBidirectional(arb.xProperty());
        r.yProperty().bindBidirectional(arb.yProperty());
        r.widthProperty().bindBidirectional(arb.widthProperty());
        r.heightProperty().bindBidirectional(arb.heightProperty());
        initAnnotationRectangleEvents(r);
        mv.getImagePane().getChildren().add(r);
    }

    private void initAnnotationRectangleEvents(Rectangle rect) {
        rect.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            new AnnotationMouseEnteredState().changeRectangleEffects(rect);
        });
        rect.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            new AnnotationMouseDefaultState().changeRectangleEffects(rect);
        });
    }

    private void initPrintMenuItems() {
        MenuItem pageSetupMenuItem = mv.getPageSetupMenuItem();
        MenuItem printMenuItem = mv.getPrintMenuItem();

        pageSetupMenuItem.addEventHandler(ActionEvent.ACTION, e -> {
            Printer printer = Printer.getDefaultPrinter();
            printerJob = PrinterJob.createPrinterJob(printer);
            if (printerJob != null) {
                printerJob.showPageSetupDialog(null);
            } else {
                Alert popup = new Alert(Alert.AlertType.ERROR,
                        "No printers registed on this computer", ButtonType.OK);
                Optional<ButtonType> answer = popup.showAndWait();
                popup.close();
            }
        });
        printMenuItem.addEventHandler(ActionEvent.ACTION, e -> {
            if (printerJob == null) {
                printerJob = PrinterJob.createPrinterJob();
            }

            boolean shouldPrint = printerJob.showPrintDialog(null);
            if (shouldPrint) {
                System.out.println("Printing..");
                //Scale pane to fit paper size
                //hide rectangle nodes for printing
                //print 
                //unhide rectangle nodes
                //scale pane back to original size

                scalePaneToFit(printerJob, mv.getImagePane());
                Pane printedPane = mv.getImagePane();
                for (Node n : printedPane.getChildren()) {
                    if (n instanceof AnnotationRectangleView) {
                        n.setVisible(false);
                    }
                }
                boolean printed = printerJob.printPage(mv.getImagePane());
                if (printed) {
                    printerJob.endJob();
                    //redisplay rectangle nodes
                    for (Node n : printedPane.getChildren()) {
                        if (n instanceof AnnotationRectangleView) {
                            n.setVisible(true);
                        }
                    }
                } else {

                    Alert popup = new Alert(Alert.AlertType.ERROR, "Could not print", ButtonType.OK);
                    Optional<ButtonType> result = popup.showAndWait();
                    popup.close();
                }
                if (scale != null) {
                    mv.getImagePane().getTransforms().remove(scale);
                }
            }
        });

    }

    private void scalePaneToFit(PrinterJob printerJob, Pane imagePane) {
        PageLayout pl = printerJob.getJobSettings().getPageLayout();
        double paperWidth = (pl.getPaper().getWidth()) - (pl.getLeftMargin() + pl.getRightMargin());
        double paperHieght = (pl.getPaper().getHeight()) - (pl.getTopMargin() + pl.getBottomMargin());
        double imageWidth = imagePane.getWidth();
        double imageHeight = imagePane.getHeight();

        scale = null;
        if (pl.getPageOrientation() == PageOrientation.PORTRAIT) {
            double scaleInX = paperWidth / imageWidth;
            double scaleInY = paperHieght / imageHeight;
            double scaleValue = Math.min(scaleInX, scaleInY);
            scale = new Scale(scaleValue, scaleValue);
        }
        if (pl.getPageOrientation() == PageOrientation.LANDSCAPE) {
            double scaleInX = paperWidth / imageHeight;
            double scaleInY = paperHieght / imageWidth;
            double scaleValue = Math.min(scaleInX, scaleInY);
            scale = new Scale(scaleValue, scaleValue);
        }
        if (scale != null) {
            imagePane.getTransforms().add(scale);
        }

    }

}
