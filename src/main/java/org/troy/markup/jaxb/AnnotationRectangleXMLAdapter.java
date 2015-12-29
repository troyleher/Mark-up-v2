/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.troy.markup.model.AnnotationRectangleBean;

/**
 *
 * @author Troy
 */
public class AnnotationRectangleXMLAdapter extends XmlAdapter<AnnotationRectangleAdapter, AnnotationRectangleBean>{

    @Override
    public AnnotationRectangleBean unmarshal(AnnotationRectangleAdapter v) throws Exception {
        return new AnnotationRectangleBean(v.getxPos(), v.getyPos(), v.getWidth(), v.getHeight());
    }

    @Override
    public AnnotationRectangleAdapter marshal(AnnotationRectangleBean v) throws Exception {
        return new AnnotationRectangleAdapter(v);
    }
    
}
