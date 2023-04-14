/**
 * This is a class that use to set the text of cells
 * XinChen Lee-modified
 */
package com.example.demo.ingame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class TextMaker {
    private static TextMaker singleInstance = null;

    /**
     * This is a constructor of class TextMaker
     */
    private TextMaker() {

    }

    /**
     * This is a method to create instance of Singleton class
     * @return value of singleInstance
     */
    static TextMaker getSingleInstance() {
        if (singleInstance == null)
            singleInstance = new TextMaker();
        return singleInstance;
    }

    /**
     * This is a method use to set the font size, location, color and style of text on cell
     * @param input This is a parameter use to save argument of text
     * @param xCell This is a parameter use to get horizontal location of cell
     * @param yCell This is a parameter use to get vertical location of cell
     * @param root This is a parameter use to set Group to show on Scene
     * @return the value of text
     */
    Text madeText(String input, double xCell, double yCell, Group root) {
        double length = GameScene.getLENGTH();
        Text text = new Text(input);
        text.setFont(Font.font(23));
        text.relocate((xCell + (1.2)* length / 7.0), (yCell + 2 * length / 7.0));
        text.setFill(Color.BLACK);
        text.setStyle("-fx-font-weight:bold");

        return text;
    }

    /**
     * This is a method use to change the text on two cells
     * @param first This is a temporary parameter used to store the text on cell
     * @param second This is a temporary parameter used to store the text on cell
     */
    static void changeTwoText(Text first, Text second) {
        String temp;
        temp = first.getText();
        first.setText(second.getText());
        second.setText(temp);

        double tempNumber;
        tempNumber = first.getX();
        first.setX(second.getX());
        second.setX(tempNumber);

        tempNumber = first.getY();
        first.setY(second.getY());
        second.setY(tempNumber);
    }
}
