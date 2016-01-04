/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Troy
 */
@XmlRootElement(name = "Annotations")
public class Annotations {

    private List<Annotation> anotations = new ArrayList<>();
    private StringProperty imagePath = new SimpleStringProperty();

    public List<Annotation> getAnotations() {
        return anotations;
    }

    @XmlElement(name = "annotation")
    public void setAnotations(List<Annotation> anotations) {
        this.anotations = anotations;
    }
    public String getImagePath(){
        return imagePath.get();
    }
    public void setImagePath(String imagePath){
        this.imagePath.set(imagePath);
    }
    public StringProperty imagePathProperty(){
        return imagePath;
    }


    

}
