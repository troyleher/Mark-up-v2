/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Troy
 */
@XmlRootElement(name = "Annotations")
public class Annotations {

    private List<Annotation> anotations = new ArrayList<>();
    private ConfigurationBean configBean = ConfigurationBean.createInstance();

    public List<Annotation> getAnotations() {
        return anotations;
    }

    @XmlElement(name = "annotation")
    public void setAnotations(List<Annotation> anotations) {
        this.anotations = anotations;
    }

    public ConfigurationBean getConfigBean() {
        return configBean;
    }

    public void setConfigBean(ConfigurationBean configBean) {
        this.configBean = configBean;
    }
    

}
