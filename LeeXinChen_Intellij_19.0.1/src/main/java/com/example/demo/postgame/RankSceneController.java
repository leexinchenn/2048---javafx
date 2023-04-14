/**
 * This is a class that use to control and set scene of RankScene
 * @author XinChen Lee
 */
package com.example.demo.postgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class RankSceneController implements Initializable {

    @FXML
    private Label winnersNameTF, winnersScoreTF, titleLB;
    private Stage primaryStage;
    private Color myColor, fontColor;
    @FXML
    private AnchorPane aPane;

    private static final Path path = Paths.get("UserInfo.txt");
    private static final String filename;

    static {
        try {
            filename = String.valueOf(path.toRealPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This is a setter method use to set the background color and font color;
     * @param myColor This is a parameter use to store the background color
     * @param fontColor This is a parameter use to store the font color
     */
    public void setColor(Color myColor, Color fontColor) {
        this.myColor=myColor;
        this.fontColor=fontColor;
        aPane.setBackground(new Background(new BackgroundFill(myColor, null, null)));
        winnersScoreTF.setTextFill(fontColor);
        winnersNameTF.setTextFill(fontColor);
        titleLB.setTextFill(fontColor);
    }

    /**
     * This is a method use to let user restart the game without closing and opening the application again
     * @param event This is a parameter use to set ActionEvent
     */
    @FXML
    void backToMenuBtnOnClick(ActionEvent event) {
        primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.close();

        Parent menuRoot = null;
        try {
            menuRoot = FXMLLoader.load(getClass().getResource("/scene/MenuScene.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene menuScene=new Scene(menuRoot);
        primaryStage.setScene(menuScene);

        primaryStage.show();
    }

    /**
     * This is a method use to close the Stage while quitBtn is clicked
     * @param event This is a parameter use to set ActionEvent
     */
    @FXML
    void quitBtnOnClick(ActionEvent event) {
        primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    /**
     * This is a method use to run codes in it while the Stage shown
     * @param location This is an absolute URL giving the base location
     * @param resources This is a parameter use to get locale-specific resource
     */
    public void initialize(URL location, ResourceBundle resources) {
        getLines();
        getRecord();
    }

    /**
     * This is a getter method use to get the value of line in file
     * @return value of lines in file
     */
    private long getLines(){
        Path path = Paths.get(filename);

        try {
            return Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * This is a method use to get the records in file and show it on Stage
     */
    void getRecord(){
        String currentLine;
        StringBuilder un = new StringBuilder();
        StringBuilder sc = new StringBuilder();

        String [] unameFromFile = new String[(int) getLines()];
        long[] scoreFromFile = new long[(int) getLines()];

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            currentLine=br.readLine();
            int k=0;
            while (currentLine!=null){
                String[] data=currentLine.split(",");
                unameFromFile[k]=data[0];
                scoreFromFile[k]= Long.parseLong(data[1]);

                currentLine= br.readLine();
                k++;
            }

            sortData(unameFromFile, scoreFromFile);

            int p=0;
            while(p<getLines() && p<10){
                un.append(p + 1).append(") ").append(unameFromFile[p]).append("\n");
                sc.append(scoreFromFile[p]).append("\n");
                p++;
            }

            winnersNameTF.setText(un.toString());
            winnersScoreTF.setText(sc.toString());

            br.close();
            fr.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This is a method use to sort the data in descending order
     * @param un This is a parameter use to store the username of user
     * @param sc This is a parameter use to store the score of user
     */
    void sortData(String[]un, long[]sc){
        for(int i=0; i<getLines(); i++) {
            for (int j = i + 1; j < getLines(); j++) {
                long tmp;
                String temp;
                if (sc[i] < sc[j]) {
                    tmp = sc[i];
                    sc[i] = sc[j];
                    sc[j] = tmp;

                    temp=un[i];
                    un[i]=un[j];
                    un[j]=temp;
                }
            }
        }
    }

}
