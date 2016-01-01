/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Troy
 */
@XmlRootElement(name = "System")
@XmlType(factoryMethod = "createInstance")
public class SystemConfigWrapper {
    
    private ObservableList<String> recentFileList =
            FXCollections.observableArrayList(new ArrayList<String>());
    private static SystemConfigWrapper system;
    
    private SystemConfigWrapper(){
    
    }
    public static SystemConfigWrapper createInstance(){
        if(system == null){
            system = new SystemConfigWrapper();
        }
        return system;
    }

    public ObservableList<String> getRecentFileList() {
        return recentFileList;
    }

    @XmlElement(name = "recentFile")
    public void setRecentFileList(ObservableList<String> recentFileList) {
        this.recentFileList = recentFileList;
    }
    
    
}
