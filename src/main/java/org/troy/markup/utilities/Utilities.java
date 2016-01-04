/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.SystemConfigBean;

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
        AnnotationLetterFactory.setCurrentLetter(AnnotationLetterFactory.RESET);
        for (Annotation a : annotList) {
            a.setSymbol(AnnotationLetterFactory.createLetter());
        }
        return annotList;
    }

    public static void updateRecentFileList(File file) {
        ObservableList<String> aList = SystemConfigBean.createInstance().getRecentFileList();

        //If entry exist remove it
        aList.remove(file.getAbsolutePath());
        aList.add(0, file.getAbsolutePath());
        //Trim list
        if (aList.size() == 11) {
            aList.remove(10);
        }
    }
}
