/**
 * This is a class that use to run the first scene-MenuScene
 * @author XinChen Lee-modified
 */
package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * This is a method use to start the first Stage
     * @param primaryStage This is a parameter use to set the Stage
     * @throws Exception exceptions that can occur during the execution of a program
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent menuRoot = FXMLLoader.load(getClass().getResource("/scene/MenuScene.fxml"));
        Scene menuScene=new Scene(menuRoot);
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("2048 by hcyxl4");
        primaryStage.show();
    }

    /**
     * This is the main method that launch the first scene/stage
     * @param args This is a parameter that use to store the value of args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
