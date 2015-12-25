/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Troy
 */
public class BeanManager {

    private ObservableList<Annotation> annotationList;
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

    public void addAnnotationToList(Annotation a) {
        annotationList.add(a);
    }

}
