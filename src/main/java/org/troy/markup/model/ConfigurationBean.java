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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Troy
 */
public class ConfigurationBean {

    private static ConfigurationBean configurationBean;
    private StringProperty mainFrameTitle = new SimpleStringProperty("Mark Up App");
    private StringProperty fileLocation = new SimpleStringProperty("");
    private StringProperty initialDirectory = new SimpleStringProperty("C:\\");
    private ObservableList<String> specialCharList = FXCollections.observableArrayList(new ArrayList<String>());
    private ObservableList<String> fileExtensions = FXCollections.observableArrayList(new ArrayList<String>());

    private ConfigurationBean() {
        setUpSpecialCharList();
        fileExtensions.add("*.xml");
    }

    public static ConfigurationBean createInstance() {
        if (configurationBean == null) {
            configurationBean = new ConfigurationBean();
        }
        return configurationBean;

    }

    /**
     * Return the title for the main stage of the Java FX application
     *
     * @return
     */
    public String getMainFrameTitle() {
        return mainFrameTitle.get();
    }

    public void setMainFrameTitle(String mainFrameTitle) {
        this.mainFrameTitle.set(mainFrameTitle);
    }

    public StringProperty mainFrameTitle() {
        return mainFrameTitle;
    }

    public ObservableList<String> getSpecialCharList() {
        return specialCharList;
    }

    public void setSpecialCharList(ObservableList<String> specialCharList) {
        this.specialCharList = specialCharList;
    }

    private void setUpSpecialCharList() {
        specialCharList.add("\u2300"); //Diameter
    }
    
    public String getFileLocation(){
        return fileLocation.get();
    }
    public void setFileLocation(String fileLocation){
        this.fileLocation.set(fileLocation);
    }
    public StringProperty fileLocationProperty(){
        return fileLocation;
    }
    public String getInitialDirectory(){
        return initialDirectory.get();
    }
    public void setInitialDirectory(String directoryLocation){
        initialDirectory.set(directoryLocation);
    }
    public StringProperty initialDirectoryProperty(){
        return initialDirectory;
    }
    public List<String> getFileExtensions(){
        return fileExtensions;
    }
    public void setFileExtensions(List<String> fileExtensions){
        this.fileExtensions = FXCollections.observableArrayList(fileExtensions);
    }

}
