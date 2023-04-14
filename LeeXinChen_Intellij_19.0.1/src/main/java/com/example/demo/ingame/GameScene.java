/**
 * This is a class that use to control and set the GameScene
 * @author XinChen Lee-modified
 */
package com.example.demo.ingame;

import com.example.demo.postgame.EndGame;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Random;

public class GameScene {
    private static final int HEIGHT = 500;
    private static int n=6;
    private final static int distanceBetweenCells = 10;
    private static double LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;
    private final TextMaker textMaker = TextMaker.getSingleInstance();
    private final Cell[][] cells = new Cell[n][n];
    private Group root;
    private long score = 0;
    private long high=0;
    private boolean win=false;

    /**
     * This is a method use to set the value of parameter n and calculate the value of parameter LENGTH
     * @param number This is a variable use to pass argument that assigned to variable n
     */
    public static void setN(int number) {
        n = number;
        LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;
    }

    /**
     * This is a getter use to get the value of LENGTH
     * @return value of LENGTH
     */
    static double getLENGTH() {
        return LENGTH;
    }

    /**
     * This ia a method use to randomly fill the number on cells with value of 2 or 4
     */
    private void randomFillNumber() {

        Cell[][] emptyCells = new Cell[n][n];
        int a = 0;
        int b = 0;
        int aForBound=0,bForBound=0;
        outer:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0) {
                    emptyCells[a][b] = cells[i][j];
                    if (b < n-1) {
                        bForBound=b;
                        b++;
                    } else {
                        aForBound=a;
                        a++;
                        b = 0;
                        if(a==n)
                            break outer;
                    }
                }
            }
        }

        Text text;
        Random random = new Random();
        boolean putTwo = random.nextInt() % 2 != 0;
        int xCell, yCell;
        xCell = random.nextInt(aForBound+1);
        yCell = random.nextInt(bForBound+1);
        if (putTwo) {
            text = textMaker.madeText("2", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
            emptyCells[xCell][yCell].setTextClass(text);
            root.getChildren().add(text);
            emptyCells[xCell][yCell].setColorByNumber(2);
        } else {
            text = textMaker.madeText("4", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY(), root);
            emptyCells[xCell][yCell].setTextClass(text);
            root.getChildren().add(text);
            emptyCells[xCell][yCell].setColorByNumber(4);
        }
    }

    /**
     * This is a method use to check if there are any empty cells
     * @return 1 if there are cells with value 0 on it and -1 otherwise
     */
    private int haveEmptyCell() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0){
                    return 1;
                }
            }
        }
        return -1;
    }

    /**
     * This is a method use to get the coordinate of cells by directions moved
     * @param i This parameter is use for counter
     * @param j This parameter is use for counter
     * @param direct This parameter is used to get the direction of user pressed
     * @return the value of coordinate of cells
     */
    private int passDestination(int i, int j, char direct) {
        int coordinate = j;
        if (direct == 'l') {
            for (int k = j - 1; k >= 0; k--) {
                if (cells[i][k].getNumber() != 0) {
                    coordinate = k + 1;
                    break;
                } else if (k == 0) {
                    coordinate = 0;
                }
            }
            return coordinate;
        }
        coordinate = j;
        if (direct == 'r') {
            for (int k = j + 1; k <= n - 1; k++) {
                if (cells[i][k].getNumber() != 0) {
                    coordinate = k - 1;
                    break;
                } else if (k == n - 1) {
                    coordinate = n - 1;
                }
            }
            return coordinate;
        }
        coordinate = i;
        if (direct == 'd') {
            for (int k = i + 1; k <= n - 1; k++) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k - 1;
                    break;
                } else if (k == n - 1) {
                    coordinate = n - 1;
                }
            }
            return coordinate;
        }
        coordinate = i;
        if (direct == 'u') {
            for (int k = i - 1; k >= 0; k--) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k + 1;
                    break;
                } else if (k == 0) {
                    coordinate = 0;
                }
            }
            return coordinate;
        }
        return -1;
    }

    /**
     * This is a method use to move all cells to the left
     */
    private void moveLeft() {
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                moveHorizontally(i, j, passDestination(i, j, 'l'), -1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    /**
     * This is a method use to move all cells to the right
     */
    private void moveRight() {
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= 0; j--) {
                moveHorizontally(i, j, passDestination(i, j, 'r'), 1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    /**
     * This is a method use to move all cells to up
     */
    private void moveUp() {
        for (int j = 0; j < n; j++) {
            for (int i = 1; i < n; i++) {
                moveVertically(i, j, passDestination(i, j, 'u'), -1);
            }
            for (int i = 0; i < n; i++) {
                cells[i][j].setModify(false);
            }
        }
    }

    /**
     * This is a method use to move all cells to down
     */
    private void moveDown() {
        for (int j = 0; j < n; j++) {
            for (int i = n - 1; i >= 0; i--) {
                moveVertically(i, j, passDestination(i, j, 'd'), 1);
            }
            for (int i = 0; i < n; i++) {
                cells[i][j].setModify(false);
            }
        }
    }

    /**
     * This method is used to check and the location is a valid position horizontally in the grid
     * @param i This is a parameter used for counter
     * @param j This is a parameter used for counter
     * @param des This is a parameter used for counter
     * @param sign This is a parameter used for counter
     * @return the location is a valid position horizontally in the cell
     */
    private boolean isValidDesH(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0) {
            return cells[i][des + sign].getNumber() == cells[i][j].getNumber() && cells[i][des + sign].getModify()
                    && cells[i][des + sign].getNumber() != 0;
        }
        return false;
    }

    /**
     * This is a method use to sum the number of cells if location is valid;
     * set number of previous location of cell to 0 if the cell has changed location when user move cells horizontally
     * @param i This is a parameter used for counter
     * @param j This is a parameter used for counter
     * @param des This is a parameter used for counter
     * @param sign This is a parameter used for counter
     */
    private void moveHorizontally(int i, int j, int des, int sign) {
        if (isValidDesH(i, j, des, sign)) {
            cells[i][j].adder(cells[i][des + sign]);
            cells[i][des].setModify(true);
            score+=cells[i][des+sign].getNumber();
        } else if (des != j) {
            cells[i][j].changeCell(cells[i][des]);
        }
    }

    /**
     * This method is used to check and the location is a valid position vertically in the grid
     * @param i This is a parameter used for counter
     * @param j This is a parameter used for counter
     * @param des This is a parameter used for counter
     * @param sign This is a parameter used for counter
     * @return the location is a valid position vertically in the cell
     */
    private boolean isValidDesV(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0)
            return cells[des + sign][j].getNumber() == cells[i][j].getNumber() && cells[des + sign][j].getModify()
                    && cells[des + sign][j].getNumber() != 0;
        return false;
    }

    /**
     * This is a method use to sum the number of cells if location is valid;
     * set number of previous location of cell to 0 if the cell has changed location when user move cells vertically
     * @param i This is a parameter used for counter
     * @param j This is a parameter used for counter
     * @param des This is a parameter used for counter
     * @param sign This is a parameter used for counter
     */
    private void moveVertically(int i, int j, int des, int sign) {
        if (isValidDesV(i, j, des, sign)) {
            cells[i][j].adder(cells[des + sign][j]);
            cells[des][j].setModify(true);
            score+=cells[des+sign][j].getNumber();
        } else if (des != i) {
            cells[i][j].changeCell(cells[des][j]);
        }
    }

    /**
     * This is a method use to check if there are two cells that have same number on them are besides each other
     * @param i This is a parameter used for counter
     * @param j This is a parameter used for counter
     * @return true if there are cells with same number besides and false otherwise
     */
    private boolean haveSameNumberNearly(int i, int j) {
        if (i < n - 1 && j < n - 1) {
            if (cells[i + 1][j].getNumber() == cells[i][j].getNumber())
                return true;
            if (cells[i][j + 1].getNumber() == cells[i][j].getNumber())
                return true;
        }
        return false;
    }

    /**
     * This is a method use to check if cells on screen still can move
     * @return true if cells still can move and false otherwise
     */
    private boolean canNotMove() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (haveSameNumberNearly(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This is a method use to set value of the highest tile
     */
    private void setHighestTile(){
        high = cells[0][0].getNumber();
        for ( int i = 0; i < n; i++ ) {
            for ( int j = 0; j < n; j++ ) {
                if ( cells[i][j].getNumber() > high ) {
                    high = cells[i][j].getNumber();
                }
            }
        }
    }

    /**
     * This is a method use to show the cells on Scene; handle event when key was pressed by moving up/left/down/right;
     * determine if user wins or lose the game; set the Scene of GameScene
     * @param gameScene This is a parameter use to set the Scene of GameScene
     * @param root This is a parameter use to set the Group of GameScene to show on scene
     * @param primaryStage This is a parameter use to set the Stage
     * @param endGameScene This is a parameter use to set the Scene of EndGame
     * @param endGameRoot This is a parameter use to set the Group of EndGame to show on scene
     * @param fontColor This is a parameter use to store the font color
     * @param highScore This is a parameter use to store the highest score of user
     */
    public void game(Scene gameScene, Group root, Stage primaryStage, Scene endGameScene, Group endGameRoot, Color fontColor, long highScore) {
        this.root = root;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cells[i][j] = new Cell((j) * LENGTH + (j + 1) * distanceBetweenCells,
                        (i) * LENGTH + (i + 1) * distanceBetweenCells, LENGTH, root);
            }
        }

        VBox pane2 = new VBox();
        Label scoreText = new Label();
        Label highTileText = new Label();
        Label highScoreText = new Label();
        text(pane2, fontColor, scoreText, highTileText, highScoreText);
        highScoreText.setText(highScore+"");
        pane2.relocate((gameScene.getWidth()-850)/2,500);

        root.getChildren().add(pane2);
        root.relocate((gameScene.getWidth()-550)/2, (gameScene.getHeight()-570)/2);

        gameScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.floatValue()!=oldValue.floatValue()){
                root.relocate((gameScene.getWidth()-550)/2, (gameScene.getHeight()-570)/2);
            }
        });

        gameScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.floatValue()!=oldValue.floatValue()){
                root.relocate((gameScene.getWidth()-500)/2, (gameScene.getHeight()-570)/2);
            }
        });

        randomFillNumber();
        randomFillNumber();

        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, key -> Platform.runLater(() -> {
            int haveEmptyCell;
            boolean rightKeyClicked=true;

            if (key.getCode() == KeyCode.DOWN || key.getCode()==KeyCode.S) {
                GameScene.this.moveDown();
            } else if (key.getCode() == KeyCode.UP || key.getCode()==KeyCode.W) {
                GameScene.this.moveUp();
            } else if (key.getCode() == KeyCode.LEFT || key.getCode()==KeyCode.A) {
                GameScene.this.moveLeft();
            } else if (key.getCode() == KeyCode.RIGHT || key.getCode()==KeyCode.D) {
                GameScene.this.moveRight();
            } else {
                rightKeyClicked=false;
            }

            scoreText.setText(score + "");
            GameScene.this.setHighestTile();
            highTileText.setText(high+"");
            if(score>highScore){
                highScoreText.setText(score+"");
            }

            haveEmptyCell = GameScene.this.haveEmptyCell();

            if (haveEmptyCell == -1) {
                if (GameScene.this.canNotMove()) {
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setTitle("Information Dialog");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Game Over");

                    alert3.showAndWait();

                    showEndGame(primaryStage, endGameScene, endGameRoot, score, fontColor, high, win);
                } else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Please move another direction.");

                    alert.showAndWait();
                }
            } else if(high==2048 && !win) {
                win=true;

                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Information Dialog");
                alert2.setHeaderText("CONGRATULATIONS, you have reached 2048!");
                alert2.setContentText("Do you want to continue?");

                ButtonType contButt = new ButtonType("Continue");
                ButtonType quitButt = new ButtonType("Quit");

                alert2.getButtonTypes().setAll(contButt, quitButt);

                Optional<ButtonType>result=alert2.showAndWait();
                if(result.get()==contButt){
                    alert2.close();
                    GameScene.this.randomFillNumber();
                } else if (result.get()==quitButt) {
                    showEndGame(primaryStage, endGameScene, endGameRoot, score, fontColor, high, win);
                }
            } else if(haveEmptyCell == 1 && rightKeyClicked || haveEmptyCell==0)
                GameScene.this.randomFillNumber();
        }));
    }

    /**
     * This is a method use to set some elements on Game Scene
     * @param pane This is a parameter use to set the VBox
     * @param fontColor This is a parameter use to store font color
     * @param scoreText This is a parameter use to set the Label
     * @param highTileText This is a parameter use to set the Label
     * @param highScoreText This is a parameter use to set the Label
     */
    private void text(VBox pane, Color fontColor, Label scoreText, Label highTileText, Label highScoreText){
        HBox pane2=new HBox();
        pane2.setAlignment(Pos.CENTER);
        pane2.setSpacing(15);
        Label text = new Label();
        text.setText("SCORE");
        text.setTextFill(fontColor);
        text.setFont(Font.font("Monospace", 20));
        scoreText.setFont(Font.font(20));
        scoreText.setText("0");
        scoreText.setTextFill(fontColor);
        scoreText.setStyle("-fx-font-weight: BOLD; -fx-font-family:Monospace;");
        pane2.getChildren().addAll(text, scoreText);

        HBox pane3=new HBox();
        pane3.setAlignment(Pos.CENTER);
        pane3.setSpacing(15);
        Label text2 = new Label();
        text2.setText("HIGHEST TILE");
        text2.setTextFill(fontColor);
        text2.setFont(Font.font("Monospace", 20));
        highTileText.setFont(Font.font(20));
        highTileText.setText("0");
        highTileText.setTextFill(fontColor);
        highTileText.setStyle("-fx-font-weight: BOLD; -fx-font-family:Monospace;");
        pane3.getChildren().addAll(text2, highTileText);

        HBox pane4=new HBox();
        pane4.setAlignment(Pos.CENTER);
        pane4.setSpacing(15);
        Label text3 = new Label();
        text3.setText("HIGHEST SCORE");
        text3.setTextFill(fontColor);
        text3.setFont(Font.font("Monospace", 20));
        highScoreText.setFont(Font.font(20));
        highScoreText.setText("0");
        highScoreText.setTextFill(fontColor);
        highScoreText.setStyle("-fx-font-weight: BOLD; -fx-font-family:Monospace;");
        pane4.getChildren().addAll(text3, highScoreText);

        HBox hb=new HBox();
        hb.setSpacing(30);
        hb.getChildren().addAll(pane2, pane3, pane4);

        Label info1=new Label("Press ↑←↓→ or WASD to play");
        info1.setTextFill(fontColor);
        info1.setFont(Font.font("Monospace", 13));
        Label info2=new Label("Score will not be saved if quit while playing.");
        info2.setTextFill(fontColor);
        info2.setFont(Font.font("Monospace", 13));

        pane.getChildren().addAll(hb, info1, info2);
        pane.setSpacing(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);
    }

    /**
     * This is a method use to show the scene of EndGame
     * @param primaryStage This is a parameter use to set the Stage
     * @param endGameScene This is a parameter use to set the Scene
     * @param endGameRoot This is a parameter use to set the Group to show on scene
     * @param score This is a parameter use to store score of user
     * @param fontColor This is a parameter use to store the font color
     * @param high This is a parameter use to store the highest tile score of user
     * @param win This is a parameter use to know whether user already win the game by reaching 2048
     */
    private void showEndGame(Stage primaryStage, Scene endGameScene, Group endGameRoot, long score, Color fontColor, long high, boolean win){
        primaryStage.setScene(endGameScene);

        EndGame.getInstance().endGameShow(endGameScene, endGameRoot, primaryStage, score, fontColor);
        score = 0;
        high=0;
        win=false;
    }
}
