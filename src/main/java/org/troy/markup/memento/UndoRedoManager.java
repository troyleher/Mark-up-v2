/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.memento;

import java.beans.PropertyChangeListener;
import java.util.List;
import javafx.collections.ObservableList;
import org.troy.markup.model.Annotation;

/**
 *
 * @author Troy
 */
public interface UndoRedoManager {

    public void save(ObservableList<Annotation> list);

    public ObservableList<Annotation> undo();

    public ObservableList<Annotation> redo();

    public ObservableList<ObservableList<Annotation>> getSaveList();

    public ObservableList<ObservableList<Annotation>> getRedoList();

}
