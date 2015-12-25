/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.memento;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.troy.markup.model.Annotation;
import org.troy.markup.utilities.Utilities;

/**
 *
 * @author Troy
 */
public class UndoRedoManagerImpl implements UndoRedoManager {

    private static UndoRedoManager annotationMemento;
    private ObservableList<ObservableList<Annotation>> saveList = FXCollections.observableArrayList(new LinkedList<>());
    private ObservableList<ObservableList<Annotation>> redoList = FXCollections.observableArrayList(new LinkedList<>());

    private UndoRedoManagerImpl() {
    }

    public static UndoRedoManager getInstance() {
        if (annotationMemento == null) {
            annotationMemento = new UndoRedoManagerImpl();
        }
        return annotationMemento;
    }

    @Override
    public void save(ObservableList<Annotation> list) {
        if (list != null && !list.isEmpty()) {
            saveList.add(Utilities.copy(list));
            redoList.clear();
            //System.out.printf("Save list size = %s", saveList.size());
        }
    }

    @Override
    public ObservableList<Annotation> undo() {
        ObservableList<Annotation> currentList = FXCollections.observableArrayList(new LinkedList<>());

        if (!saveList.isEmpty()) {
            redoList.add(saveList.remove(saveList.size() - 1));
            
            if (saveList.size() > 0) {
                currentList = Utilities.copy(saveList.get(saveList.size() - 1));
            }
        }
        return currentList;
    }

    @Override
    public ObservableList<Annotation> redo() {
        ObservableList<Annotation> l = null;
        if (!redoList.isEmpty()) {
            l = redoList.remove(redoList.size() - 1);
            saveList.add(l);
       }
        return l;
    }

    public List<Annotation> getSaveListByIndex(int index) {
        return saveList.get(index);
    }

    public void resetMemento() {
        redoList = FXCollections.observableArrayList(new LinkedList<>());
        saveList =  FXCollections.observableArrayList(new LinkedList<>());
    }

    
    private void printList(List<Annotation> aList) {
        System.out.println("***************************");
        for (Annotation a : aList) {
            System.out.print(a.getSymbol());
            System.out.print(" " + a.getDescription() + "\n");
        }
        System.out.println("***************************");
    }

    @Override
    public ObservableList<ObservableList<Annotation>> getSaveList() {
        return saveList;
    }

    @Override
    public ObservableList<ObservableList<Annotation>> getRedoList() {
        return redoList;
    }
    
    

}
