/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.eventhandlers;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.troy.markup.controller.MainController;
import org.troy.markup.dao.AnnotationDAO;
import org.troy.markup.dao.AnnotationDAOJAXB;
import org.troy.markup.model.Annotations;
import org.troy.markup.model.BeanManager;
import org.troy.markup.model.SystemConfigBean;

/**
 *
 * @author Troy
 */
public class OpenFileChooserHandler implements EventHandler<ActionEvent>{
    
    private MainController mc;
    private BeanManager bm;

    public OpenFileChooserHandler(MainController mc) {
        this.mc = mc;
        bm = BeanManager.createInstance();
    }
    @Override
    public void handle(ActionEvent event) {
        SystemConfigBean scb = SystemConfigBean.createInstance();
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(scb.getInitialDirectory()));
        fc.setInitialFileName(null);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mark up", scb.getFileExtensions()));
        fc.setTitle("Open");
        File file = fc.showOpenDialog(null);
        if(file != null){
            AnnotationDAO dao = new AnnotationDAOJAXB();
            Annotations a = dao.getAnnotations(file);
            bm.setAnnotationList(FXCollections.observableArrayList(a.getAnotations()));
            mc.initAnnotations(bm.getAnnotationList());
        }
        
        
    }
    
}
