/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.troy.markup.eventhandlers.OpenFileChooserHandler;
import org.troy.markup.view.WelcomeView;

/**
 *
 * @author Troy
 */
public class WelcomeViewController {
    

    private WelcomeView welcomeView;
    private Main mainController;

    public WelcomeViewController(WelcomeView wv, Main mainController) {
        welcomeView = wv;
        this.mainController = mainController;
        initEventHandlers();
    }

    private void initEventHandlers() {
        Button openButton = welcomeView.getOpenButton();
        openButton.addEventHandler(ActionEvent.ACTION, new OpenFileChooserHandler(welcomeView.getStage()));
    }
}
