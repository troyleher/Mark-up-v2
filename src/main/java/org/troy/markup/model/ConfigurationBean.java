/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Troy
 */
public class ConfigurationBean {

    private static ConfigurationBean configurationBean;
    
    
    private StringProperty mainFrameTitle = new SimpleStringProperty("Mark Up App");

    private ConfigurationBean() {
    }

    public static ConfigurationBean createInstance() {
        if (configurationBean == null) {
            configurationBean = new ConfigurationBean();
        }
        return configurationBean;
    }

    /**
     * Return the title for the main stage of the Java FX application
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

}
