/**
 * This is a class that use to control and set the scene of EndGame
 * @author XinChen Lee-modified
 */
package com.example.demo.postgame;

import com.example.demo.pregame.Account;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGame {
    private static EndGame singleInstance = null;
    private Stage pStage;
    private Color myColor;

    /**
     * This is a constructor of class EndGame
     */
    private EndGame(){

    }

    /**
     * This is a method to create instance of Singleton class
     * @return value of singleInstance
     */
    public static EndGame getInstance(){
        if(singleInstance == null)
            singleInstance= new EndGame();
        return singleInstance;
    }

    /**
     * This is a method use to show elements on Stage and handle the event do by user
     * @param endGameScene This is a parameter use to set the Scene of EndGame
     * @param root This is a parameter use to set the Group of EndGame to show on scene
     * @param primaryStage This is a parameter use to set the Stage
     * @param score This is a parameter use to store the score of user
     * @param fontColor This is a parameter use to store the font color
     */
    public void endGameShow(Scene endGameScene, Group root, Stage primaryStage,long score, Color fontColor){
        Account.saveRecord(score);

        VBox pane = new VBox();
        Button nextButton = new Button("NEXT");
        EndGame.this.text(pane, endGameScene, fontColor, score, nextButton);

        root.getChildren().add(pane);

        nextButton.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/RankScene.fxml"));
                    Parent root=loader.load();

                    RankSceneController rsc=loader.getController();
                    rsc.setColor(myColor, fontColor);

                    pStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, endGameScene.getWidth(), endGameScene.getHeight());
                    pStage.setScene(scene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        endGameScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.floatValue()!=oldValue.floatValue()){
                pane.setPrefSize(endGameScene.getWidth(), endGameScene.getHeight());
                pane.setAlignment(Pos.CENTER);
            }
        });

        endGameScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.floatValue()!=oldValue.floatValue()){
                pane.setPrefSize(endGameScene.getWidth(), endGameScene.getHeight());
                pane.setAlignment(Pos.CENTER);
            }
        });
    }

    /**
     * This is a setter method use to set the background color
     * @param myColor This is a parameter use to store the background color
     */
    public void setColor(Color myColor){
        this.myColor=myColor;
    }

    /**
     * This is a method use to set some elements to display on Stage
     * @param pane This is a parameter use to set the VBox
     * @param endGameScene This is a parameter use to set the Scene of EndGame
     * @param fontColor This is a parameter use to store the font color
     * @param score This is a parameter use to store the score of user
     * @param nextButton This is a parameter use to set a Button
     */
    private void text(VBox pane, Scene endGameScene, Color fontColor, long score, Button nextButton){
        pane.setAlignment(Pos.CENTER);
        pane.setPrefSize(endGameScene.getWidth(), endGameScene.getHeight());
        pane.setSpacing(50);

        Label text = new Label("GAME OVER");
        text.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Monospace",60));
        text.setTextFill(fontColor);
        text.setStyle("-fx-font-weight:bold");

        Label scoreText = new Label(score+"");
        scoreText.setAlignment(Pos.CENTER);
        scoreText.setFont(Font.font("Monospace",60));
        scoreText.setTextFill(fontColor);
        scoreText.setStyle("-fx-font-weight:bold");

        nextButton.setPrefSize(239,42);
        nextButton.setAlignment(Pos.CENTER);
        nextButton.setStyle("-fx-background-color: #f3f2f2; -fx-border-color: #c1bdbd; -fx-border-radius: 10; " +
                "-fx-background-radius:10; -fx-font-size:16; -fx-font-weight:bold; -fx-font-family:monospace;");

        pane.getChildren().addAll(text, scoreText, nextButton);
    }

}
