/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.troy.markup.model.Annotation;
import org.troy.markup.model.Annotations;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.SystemConfigBean;
import org.troy.markup.state.AnnotationMouseDefaultState;

/**
 *
 * @author Troy
 */
public class FileChooserController {

    private Controller c;
    private BeanManager bm;
    private SystemConfigBean cb;

    public FileChooserController(Controller c) {
        this.c = c;
        bm = BeanManager.createInstance();
        cb = SystemConfigBean.createInstance();
    }

    public void displayOpenFileChooser(Stage stage) throws JAXBException {
       FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(cb.getInitialDirectory()));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mark up files",
                cb.getFileExtensions()));
        File file = fc.showOpenDialog(stage);
        if (file == null) {
            return;
        }
        JAXBContext context = JAXBContext.newInstance(Annotations.class);
        Unmarshaller m = context.createUnmarshaller();
        Annotations annotations = (Annotations) m.unmarshal(file);
        ObservableList<Annotation> aList = FXCollections.observableArrayList(annotations.getAnotations());
        for (Annotation a : aList) {
            c.initAnnotations(aList);
            //new AnnotationMouseDefaultState().changeRectangleEffects(a);

        }
        bm.setAnnotationList(aList);
        //System.out.println(file.getParent());
        cb.setInitialDirectory(file.getParent());
        cb.setInitialFileName(file.getName());
    }
}
