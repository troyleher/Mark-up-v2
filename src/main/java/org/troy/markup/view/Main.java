/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.view;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.troy.markup.model.ConfigurationBean;

/**
 *
 * @author Troy
 */
public class Main extends Application{
    
    public static void main(String... args){
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigurationBean configBean = ConfigurationBean.createInstance();
        BorderPane borderPane = new BorderPane();
        
        Pane anchorPane = new AnchorPane();
        borderPane.setCenter(anchorPane);
        
        //Load a sample image for development only
        Image image = new Image("/images/test.png");
        ImageView imageView = new ImageView(image, anchorPane);
        anchorPane.getChildren().add(imageView);
       
        
        
         
        
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle(configBean.getMainFrameTitle());
        primaryStage.show();
                
    }
    
}
