/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and getAnnotations the template in the editor.
 */
package org.troy.markup.dao;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.troy.markup.model.Annotations;
import org.troy.markup.model.BeanManager;

/**
 *
 * @author Troy
 */
public class AnnotationDAOJAXB implements AnnotationDAO{

    JAXBContext context;
    
    public AnnotationDAOJAXB() {
        try {
            context = JAXBContext.newInstance(Annotations.class);
        } catch (JAXBException ex) {
            Logger.getLogger(AnnotationDAOJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public boolean save() {
        boolean wasSaved = false;
        
        return wasSaved;
    }

    @Override
    public Annotations getAnnotations(File fileToOpen) {
        try {
            Unmarshaller marshaller = context.createUnmarshaller();
            return (Annotations)marshaller.unmarshal(fileToOpen);
        } catch (JAXBException ex) {
            Logger.getLogger(AnnotationDAOJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
