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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Troy
 */
@XmlRootElement
@XmlType(factoryMethod = "createInstance")
public class SystemConfigBean {

    private static SystemConfigBean configurationBean;
    private final StringProperty mainFrameTitle = new SimpleStringProperty("Mark Up App");
    private final StringProperty initialFileName = new SimpleStringProperty("untitled.xml");
    private final StringProperty initialDirectory = new SimpleStringProperty("c:\\");
    private final StringProperty imagePath = new SimpleStringProperty("C:\\test3.png");
    private ObservableList<String> specialCharList = FXCollections.observableArrayList(new ArrayList<String>());
    private ObservableList<String> fileExtensions = FXCollections.observableArrayList(new ArrayList<String>());
    private ObservableList<String> imageExtensions = FXCollections.observableArrayList(new ArrayList<String>());
    private ObservableList<String> recentFileList = FXCollections.observableArrayList(new ArrayList<String>());

    private SystemConfigBean() {
        setUpSpecialCharList();
        fileExtensions.add("*.xml");
        imageExtensions.addAll("*.jpg", "*.jpeg", "*.png", "*.tiff");
    }

    public static SystemConfigBean createInstance() {
        if (configurationBean == null) {
            configurationBean = new SystemConfigBean();
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
        specialCharList.add("\u27C2"); //Pependicular
        specialCharList.add("\u2225"); //Paralell to
        specialCharList.add("\u2104"); //Center line
        specialCharList.add("\u00B1"); //Tolerance symbol
    }

    public String getInitialFileName() {
        return initialFileName.get();
    }

    public void setInitialFileName(String fileLocation) {
        this.initialFileName.set(fileLocation);
    }

    public StringProperty fileLocationProperty() {
        return initialFileName;
    }

    public String getInitialDirectory() {
        return initialDirectory.get();
    }

    public void setInitialDirectory(String directoryLocation) {
        initialDirectory.set(directoryLocation);
    }

    public StringProperty initialDirectoryProperty() {
        return initialDirectory;
    }

    public List<String> getFileExtensions() {
        return fileExtensions;
    }

    public void setFileExtensions(List<String> fileExtensions) {
        this.fileExtensions = FXCollections.observableArrayList(fileExtensions);
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String path) {
        imagePath.set(path);
    }

    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public void setRecentFileList(ObservableList<String> list) {
        this.recentFileList = list;
    }

    public ObservableList<String> getRecentFileList() {
        return recentFileList;
    }

    public ObservableList<String> getImageExtensions() {
        return imageExtensions;
    }

    public void setImageExtensions(ObservableList<String> imageExtensions) {
        this.imageExtensions = imageExtensions;
    }
}
