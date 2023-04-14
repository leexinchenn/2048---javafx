/**
 * This is a class that use to control and set the scene of AccountScene
 * @author XinChen Lee
 */
package com.example.demo.pregame;

import com.example.demo.postgame.EndGame;
import com.example.demo.ingame.GameScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountSceneController {

    @FXML
    private AnchorPane aPane;
    @FXML
    private Button startBtn, backBtn;
    @FXML
    private Label unText;
    @FXML
    private TextField usernameTF;

    private Stage primaryStage;
    private Color mycolor, fontColor;
    private int level;

    /**
     * This is a setter method use to set the background color and font color of Account Scene
     * @param mycolor This is a parameter use to store the background color
     * @param fontColor This is a parameter use to store the font color
     */
    public void setColor(Color mycolor, Color fontColor) {
        this.mycolor=mycolor;
        this.fontColor=fontColor;
        aPane.setBackground(new Background(new BackgroundFill(mycolor, null, null)));
        unText.setTextFill(fontColor);
    }

    /**
     * This is a setter method use to set the level
     * @param level This is a parameter use to store the value of level
     */
    public void setLevel(int level) {
        this.level=level;
    }

    /**
     * This is a method use to check if username is empty; pass username to Account class;
     * show GameScene and EndGame Stage; while startBtn is clicked
     * @param event This is a parameter use to set ActionEvent
     */
    @FXML
    void startBtnOnClick(ActionEvent event){
        if (usernameTF.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your username");

            alert.showAndWait();
        } else {
            Account acc=new Account(usernameTF.getText());
            acc.getRecord();

            Group gameRoot = new Group();
            Scene gameScene = new Scene(gameRoot, aPane.getWidth(), aPane.getHeight(), mycolor);

            primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(gameScene);

            GameScene game = new GameScene();
            Group endgameRoot = new Group();
            Scene endGameScene = new Scene(endgameRoot, gameScene.getWidth(), gameScene.getHeight(), mycolor);
            GameScene.setN(level);
            game.game(gameScene, gameRoot, primaryStage, endGameScene, endgameRoot, fontColor, acc.getHighScore());
            EndGame.getInstance().setColor(mycolor);

        }
    }

    /**
     * This is a method use to show MenuScene Stage while backBtn is clicked
     * @param event This is a parameter use to set ActionEvent
     * @throws IOException an exception that is thrown when an I/O error occurs
     */
    @FXML
    void backBtnOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/MenuScene.fxml"));
        Parent root=loader.load();

        primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

}
