/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.troy.markup.model.AnnotationCircleBean;

/**
 *
 * @author Troy
 */
public class AnnotationCircleXMLAdapter extends XmlAdapter<AnnotationCircleAdapter, AnnotationCircleBean> {

    @Override
    public AnnotationCircleBean unmarshal(AnnotationCircleAdapter v) throws Exception {
        return new AnnotationCircleBean(v.getxPos(), v.getyPos(), v.getRadius());
    }

    @Override
    public AnnotationCircleAdapter marshal(AnnotationCircleBean v) throws Exception {
        return new AnnotationCircleAdapter(v);
    }
    
}
