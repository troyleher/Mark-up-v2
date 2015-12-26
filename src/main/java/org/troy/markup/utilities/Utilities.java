/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.utilities;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.troy.markup.model.Annotation;

/**
 *
 * @author Troy
 */
public class Utilities {

    public static ObservableList<Annotation> copy(ObservableList<Annotation> listToCopy) {
        ObservableList<Annotation> newList = FXCollections.observableArrayList(new ArrayList<>(listToCopy.size()));
        listToCopy.stream().forEach((a) -> {
            newList.add(new Annotation(a));
        });
        return newList;
    }

    public static ObservableList<Annotation> reLetter(ObservableList<Annotation> annotList) {
        ObservableList<Annotation> modifiedList = FXCollections.observableArrayList(new ArrayList<>(annotList.size()));
        AnnotationLetterFactory.setCurrentLetter(AnnotationLetterFactory.RESET);
        for (Annotation a : annotList) {
            Annotation newA = new Annotation(a);
            newA.setSymbol(AnnotationLetterFactory.createLetter());
            modifiedList.add(newA);
        }
        return modifiedList;
    }
}
