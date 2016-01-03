/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.troy.markup.eventhandlers.OpenFileChooserHandler;
import org.troy.markup.model.SystemConfigBean;
import org.troy.markup.view.WelcomeView;

/**
 *
 * @author Troy
 */
public class WelcomeViewController {
    

    private WelcomeView welcomeView;

    public WelcomeViewController(WelcomeView wv) {
        welcomeView = wv;
        initEventHandlers();
        initGUI();
    }

    private void initEventHandlers() {
        Button openButton = welcomeView.getOpenButton();
        openButton.addEventHandler(ActionEvent.ACTION, new OpenFileChooserHandler(welcomeView.getStage()));
    }

    private void initGUI() {
        ListView<String> listView = welcomeView.getListView();
        listView.setItems(SystemConfigBean.createInstance().getRecentFileList());
    }
}
