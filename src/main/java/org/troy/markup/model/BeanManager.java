/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

/**
 *
 * @author Troy
 */
public class BeanManager {

    private final ObservableList<Annotation> annotationList;
    private final StringProperty imagePath = new SimpleStringProperty();
    private boolean fileHasChanged = false;
    private static BeanManager beanManager;

    private BeanManager() {
        annotationList = FXCollections.observableArrayList(new Callback<Annotation, Observable[]>() {

            @Override
            public Observable[] call(Annotation a) {
                return new Observable[]{a.descriptionProperty(), a.symbolProperty(),
                    a.getCircle().circleMovedProperty()};
            }

        });
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

    public boolean isFileChanged() {
        return fileHasChanged;
    }

    public void setFileChanged(boolean fileHasChanged) {
        this.fileHasChanged = fileHasChanged;
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public Annotations createProjectFile() {
        Annotations annotations = new Annotations();
        annotations.setAnotations(annotationList);
        annotations.setImagePath(imagePath.get());
        return annotations;
    }

}
