/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Troy
 */
@XmlRootElement
public class SystemConfigWrapper {
    
    private ObservableList<String> recentFileList =
            FXCollections.observableArrayList(new ArrayList<String>());

    public ObservableList<String> getRecentFileList() {
        return recentFileList;
    }

    public void setRecentFileList(ObservableList<String> recentFileList) {
        this.recentFileList = recentFileList;
    }
    
    
}
