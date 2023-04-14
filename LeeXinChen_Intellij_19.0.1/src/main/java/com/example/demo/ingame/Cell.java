/**
 * This is a class that use to control and set the cells on GameScene
 * @author XinChen Lee-modified
 */

package com.example.demo.ingame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Cell {
    private final Rectangle rectangle;
    private final Group root;
    private Text textClass;
    private boolean modify = false;

    /**
     * This is a setter for parameter boolean modify use to determine which cells should be modified
     * @param modify This parameter is used to determine which cells should be modified
     */
    void setModify(boolean modify) {
        this.modify = modify;
    }

    /**
     * This is a getter for parameter boolean modify use to determine which cells should be modified
     * @return true if cells needs to modify
     */
    boolean getModify() {
        return !modify;
    }

    /**
     * This is a method to set empty space to 0
     * @param x This is a parameter to set horizontal location of cell
     * @param y This is a parameter to set vertical location of cell
     * @param scale This is a parameter to set height and width of cell
     * @param root This is a parameter of Group use to show elements on scene
     */
    Cell(double x, double y, double scale, Group root) {
        rectangle = new Rectangle();
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setHeight(scale);
        rectangle.setWidth(scale);
        this.root = root;
        rectangle.setFill(Color.rgb(184,173,159));
        this.textClass = TextMaker.getSingleInstance().madeText("0", x, y, root);
        root.getChildren().add(rectangle);
    }

    /**
     * This is a setter for parameter Text textClass
     * @param textClass This parameter is used to set the text on cells
     */
    void setTextClass(Text textClass) {
        this.textClass = textClass;
    }

    /**
     * This ia a method to use to assign empty cells that are not assign to value 0
     * @param cell This parameter is used to call functions in class Cell
     */
    void changeCell(Cell cell) {
        TextMaker.changeTwoText(textClass, cell.getTextClass());
        root.getChildren().remove(cell.getTextClass());
        root.getChildren().remove(textClass);

        if (!cell.getTextClass().getText().equals("0")) {
            root.getChildren().add(cell.getTextClass());
        }
        if (!textClass.getText().equals("0")) {
            root.getChildren().add(textClass);
        }
        setColorByNumber(getNumber());
        cell.setColorByNumber(cell.getNumber());
    }

    /**
     * This is a method that use to add value of two cells together
     * @param cell This parameter is used to call functions in class Cell
     */
    void adder(Cell cell) {
        cell.getTextClass().setText((cell.getNumber() + this.getNumber()) + "");
        textClass.setText("0");
        root.getChildren().remove(textClass);
        cell.setColorByNumber(cell.getNumber());
        setColorByNumber(getNumber());
    }

    /**
     * This is a method used to set the color of cells by their value
     * @param number This parameter is used to store the number on cell
     */
    void setColorByNumber(int number) {
        switch (number) {
            case 0:
                rectangle.setFill(Color.rgb(184,173,159));
                break;
            case 2:
                rectangle.setFill(Color.rgb(250,238,220));
                break;
            case 4:
                rectangle.setFill(Color.rgb(234,220,200));
                break;
            case 8:
                rectangle.setFill(Color.rgb(239,178,123));
                break;
            case 16:
                rectangle.setFill(Color.rgb(238,154,101));
                break;
            case 32:
                rectangle.setFill(Color.rgb(245,124,97));
                break;
            case 64:
                rectangle.setFill(Color.rgb(244,97,67));
                break;
            case 128:
                rectangle.setFill(Color.rgb(240,207,118));
                break;
            case 256:
                rectangle.setFill(Color.rgb(234,200,100));
                break;
            case 512:
                rectangle.setFill(Color.rgb(230,185,89));
                break;
            case 1024:
                rectangle.setFill(Color.rgb(227,170,83));
                break;
            case 2048:
                rectangle.setFill(Color.rgb(223,155,70));
            default:
                rectangle.setFill(Color.rgb(223,155,70));
        }
    }

    /**
     * This is a getter of horizontal location of rectangle
     * @return the horizontal location of rectangle
     */
    double getX() {
        return rectangle.getX();
    }

    /**
     * This is a getter of vertical location of rectangle
     * @return the vertical location of rectangle
     */
    double getY() {
        return rectangle.getY();
    }

    /**
     * This is a getter to get the text number on cell
     * @return the number on cell
     */
    int getNumber() {
        return Integer.parseInt(textClass.getText());
    }

    /**
     * This is a getter to get the text of parameter textClass
     * @return text of parameter textClass
     */
    private Text getTextClass() {
        return textClass;
    }

}
