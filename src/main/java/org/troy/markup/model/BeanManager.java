/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.troy.markup.utilities.Utilities;

/**
 *
 * @author Troy
 */
public class BeanManager {

    private final ObservableList<Annotation> annotationList;
    private static BeanManager beanManager;

    private BeanManager() {
        annotationList = FXCollections.observableArrayList(new LinkedList<>());
    }

    public static BeanManager createInstance() {
        if (beanManager == null) {
            beanManager = new BeanManager();

        }
        return beanManager;
    }

    public ObservableList<Annotation> getAnnotationList() {
        return annotationList;
    }

    public void setAnnotationList(ObservableList<Annotation> aList) {
        if (aList != annotationList) {
            annotationList.clear();
            annotationList.addAll(aList);

        }
        synchronized (annotationList) {
            annotationList.notifyAll();
        }

    }

    public void addAnnotationToList(Annotation a) {
        annotationList.add(a);
    }

}
