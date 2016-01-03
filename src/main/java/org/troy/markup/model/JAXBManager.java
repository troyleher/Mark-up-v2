/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Troy
 */
public class JAXBManager {

    public static void loadStaticClasses(Class c, String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller marshaller = context.createUnmarshaller();
            marshaller.unmarshal(JAXBManager.class.getResourceAsStream(path));
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveStaticClasses(Class c, String path, Object o) {
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(o, new File(JAXBManager.class.getResource(path).toURI()));
        } catch (JAXBException | URISyntaxException ex) {
            Logger.getLogger(JAXBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
