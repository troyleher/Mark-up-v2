/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and getAnnotations the template in the editor.
 */
package org.troy.markup.dao;

import java.io.File;
import javafx.collections.ObservableList;
import org.troy.markup.model.Annotations;

/**
 *
 * @author Troy
 */
public interface AnnotationDAO {

    public boolean save();
    public Annotations getAnnotations(File fileToOpen);

}
