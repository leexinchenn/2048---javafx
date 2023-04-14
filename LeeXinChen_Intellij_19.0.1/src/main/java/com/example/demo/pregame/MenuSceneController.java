/**
 * This is a class that use to control and set the scene of MenuScene
 * @author XinChen Lee
 */
package com.example.demo.pregame;

import com.example.demo.postgame.RankSceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuSceneController implements Initializable {

    @FXML
    private ComboBox levelCB, themeCB;
    private final String[] levelItems={"Simple", "Normal", "Hard"};
    private final ObservableList<String> levelList= FXCollections.observableArrayList(levelItems);
    private final String[] themeItems={"Classic", "Dark", "Pink"};
    private final ObservableList<String> themeList = FXCollections.observableArrayList(themeItems);

    @FXML
    private Button playBtn;
    @FXML
    private Label themeText, levelText, titleText;
    @FXML
    private AnchorPane aPane;

    private String selTheme, selLevel;
    private Stage primaryStage;
    private Color mycolor=Color.rgb(252, 252, 225), fontColor=Color.BLACK;
    private int level;
    private boolean clickedT=false, clickedL=false;

    /**
     * This is a method use to show AccountScene Stage if theme and level ComboBox were chosen;
     * show AlertBox if they were not chosen; while playBtn is clicked
     * @param event This is a parameter use to set ActionEvent
     * @throws IOException an exception that is thrown when an I/O error occurs
     */
    @FXML
    void playBtnClicked(ActionEvent event) throws IOException {

        if(clickedT && clickedL){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/AccountScene.fxml"));
            Parent root=loader.load();

            AccountSceneController asc = loader.getController();
            asc.setColor(mycolor, fontColor);
            asc.setLevel(level);

            primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, aPane.getWidth(),aPane.getHeight());
            primaryStage.setScene(scene);
        }else {
            showAlert(clickedT, clickedL);
        }
    }

    /**
     * This is a method use to run codes in it while the Stage shown (something like constructor in javafx)
     * @param url This is an absolute URL giving the base location
     * @param resourceBundle This is a parameter use to get locale-specific resource
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        levelCB.setItems(levelList);
        levelCB.setStyle("-fx-font: 14px \"monospaced\";");
        themeCB.setItems(themeList);
        themeCB.setStyle("-fx-font: 14px \"monospaced\";");
    }

    /**
     * This is a method use to get the theme chosen by user while theme ComboBox is clicked
     */
    @FXML
    void themeClicked() {
        clickedT=true;
        selTheme=themeCB.getSelectionModel().getSelectedItem().toString();

        switch (selTheme) {
            case "Classic" -> {
                mycolor = Color.rgb(252, 252, 225);
                fontColor = Color.BLACK;
            }
            case "Dark" -> {
                mycolor = Color.rgb(41, 39, 33);
                fontColor = Color.WHITE;
            }
            case "Pink" -> {
                mycolor = Color.rgb(245, 195, 199, 0.2);
                fontColor = Color.BLACK;
            }
        }

        aPane.setBackground(new Background(new BackgroundFill(mycolor, null, null)));
        themeText.setTextFill(fontColor);
        levelText.setTextFill(fontColor);
        titleText.setTextFill(fontColor);
    }

    /**
     * This is a method use to get the level chosen by user
     */
    @FXML
    void levelClicked() {
        clickedL=true;
        selLevel=levelCB.getSelectionModel().getSelectedItem().toString();

        switch (selLevel) {
            case "Simple" -> level = 6;
            case "Normal" -> level = 5;
            case "Hard" -> level = 4;
        }
    }

    /**
     * This is a method use to show the AlertBox while ComboBox was not chosen
     * @param cT This is a parameter use to check if theme ComboBox was chosen
     * @param cL This is a parameter use to check if level ComboBox was chosen
     */
    void showAlert(boolean cT, boolean cL){
        String info="";
        if (!cT && !cL){
            info="Please choose a Theme and Level to continue.";
        } else if (!cT) {
            info="Please choose a Theme to continue.";
        } else if (!cL) {
            info="Please choose a Level to continue.";
        }

        Alert both = new Alert(Alert.AlertType.INFORMATION);
        both.setTitle("Information Dialog");
        both.setHeaderText(null);
        both.setContentText(info);

        both.showAndWait();
    }

    /**
     * This is a method use to display the instruction when user click instruction button
     * @param event This is a parameter use to set ActionEvent
     */
    @FXML
    void insBtnClicked(ActionEvent event) {
        Alert instruc = new Alert(Alert.AlertType.INFORMATION);
        instruc.setTitle("Instruction");
        instruc.setHeaderText(null);
        instruc.setContentText("1. Player could choose preferred Theme.\n" +
                "2. Player could choose preferred Level: \nSimple [6x6] Normal [5x5] Hard [4x4]\n" +
                "3. Player must combine tiles containing the same numbers until they reach the number 2048.\n" +
                "4. ONLY the highest score of each player will be saved.\n" +
                "5. Data will NOT be saved if quit during game.\n" +
                "6. ONLY TOP 10 players will be shown on leaderboard.\n"+
                "7. Leaderboard is shown regardless level chosen.\n\n"+
                "GOOD LUCK PLAYING and try to reach 2048!");
        instruc.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        instruc.showAndWait();
    }

    /**
     * This is a method use to show RankScene while button is clicked
     * @param event This is a parameter use to set ActionEvent
     * @throws IOException an exception that is thrown when an I/O error occurs
     */
    @FXML
    void leaderBtnClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/RankScene.fxml"));
        Parent root=loader.load();

        RankSceneController rsc=loader.getController();
        rsc.setColor(mycolor, fontColor);

        primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, aPane.getWidth(), aPane.getHeight());
        primaryStage.setScene(scene);
    }
}
