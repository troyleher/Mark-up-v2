package org.troy.markup.test.memento;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.troy.markup.memento.UndoRedoManagerImpl;
import org.troy.markup.model.Annotation;

/**
 *
 * @author Troy
 */
public class TestAnnotationMemento {

    public TestAnnotationMemento() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSave() {
        ObservableList<Annotation> listToSave = FXCollections.observableArrayList(new ArrayList<>());
        Annotation a1 = new Annotation(20, 20, 100, 100);
        Annotation a2 = new Annotation(20, 20, 80, 80);
        listToSave.add(a1);
        listToSave.add(a2);

        UndoRedoManagerImpl amV2 = (UndoRedoManagerImpl) UndoRedoManagerImpl.getInstance();
        amV2.save(listToSave);

        List<Annotation> saveList = amV2.getSaveListByIndex(0);
        assertTrue(saveList.size() == 2);

    }

    @Test
    public void testUndo1() {

        UndoRedoManagerImpl amV2 = (UndoRedoManagerImpl) UndoRedoManagerImpl.getInstance();
        amV2.resetMemento();

        ObservableList<Annotation> listToSave1 = FXCollections.observableArrayList(new ArrayList<>());
        Annotation a1 = new Annotation(20, 20, 100, 100);
        Annotation a2 = new Annotation(20, 20, 80, 80);
        listToSave1.add(a1);
        listToSave1.add(a2);

        amV2.save(listToSave1);

        ObservableList<Annotation> listToSave2 = FXCollections.observableArrayList(new ArrayList<>());
        Annotation a3 = new Annotation(21, 20, 100, 100);
        Annotation a4 = new Annotation(21, 20, 80, 80);
        Annotation a5 = new Annotation(21, 20, 80, 80);
        listToSave2.add(a3);
        listToSave2.add(a4);
        listToSave2.add(a5);

        amV2.save(listToSave2);
        
        ObservableList<Annotation> listToSave3 = FXCollections.observableArrayList(new ArrayList<>());
        Annotation a6 = new Annotation(20, 20, 100, 100);
        Annotation a7 = new Annotation(20, 20, 80, 80);
        listToSave3.add(a6);
        listToSave3.add(a7);

        amV2.save(listToSave3);

        List<Annotation> undoList = amV2.undo();

        assertTrue(undoList.size() == 3);

    }

    @Test
    public void testUndo2(){
        //Test for 1 element left in save list
        UndoRedoManagerImpl amV2 = (UndoRedoManagerImpl) UndoRedoManagerImpl.getInstance();
        amV2.resetMemento();

        ObservableList<Annotation> listToSave1 = FXCollections.observableArrayList(new ArrayList<>());
        Annotation a1 = new Annotation(20, 20, 100, 100);
        Annotation a2 = new Annotation(20, 20, 80, 80);
        listToSave1.add(a1);
        listToSave1.add(a2);

        amV2.save(listToSave1);
        List<Annotation> undoResultList = amV2.undo();
        assertTrue(undoResultList.isEmpty());

    }
    @Test
    public void testRedo() {
        UndoRedoManagerImpl amV2 = (UndoRedoManagerImpl) UndoRedoManagerImpl.getInstance();
        amV2.resetMemento();

        ObservableList<Annotation> listToSave1 = FXCollections.observableArrayList(new ArrayList<>());
        Annotation a1 = new Annotation(20, 20, 100, 100);
        Annotation a2 = new Annotation(20, 20, 80, 80);
        listToSave1.add(a1);
        listToSave1.add(a2);

        amV2.save(listToSave1);

        ObservableList<Annotation> listToSave2 = FXCollections.observableArrayList(new ArrayList<>());
        Annotation a3 = new Annotation(21, 20, 100, 100);
        Annotation a4 = new Annotation(21, 20, 80, 80);
        Annotation a5 = new Annotation(21, 20, 80, 80);
        listToSave2.add(a3);
        listToSave2.add(a4);
        listToSave2.add(a5);

        amV2.save(listToSave2);
        
        amV2.undo();
        List<Annotation> redoList = amV2.redo();
        assertTrue(redoList.size() == 3);
    }
}
