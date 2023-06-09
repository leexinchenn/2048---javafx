# Lee Xin Chen 20380997

#### All .java files are stored at LeeXinChen_Intellij_19.0.1→src→main→java→com.example.demo.
#### All .fxml files are stored at LeeXinChen_Intellij_19.0.1→src→main→resources→scene.
#### The only .txt file and README.md are stored at LeeXinChen_Intellij_19.0.1.

### 1. How to compile the code<br>
You could open Intellij, select File→New→Project from Version Control, select GitHub, select **COMP2024_CW_hcyxl4** 
and clone.<br>After cloning, you could go to LeeXinChen_Intellij_19.0.1→src→main→java→com.example.demo, select **Main.java** 
and run, the application should have shown.

### 2. Where does Javadoc documentation stored<br>
LeeXinChen_Intellij_19→src→javadoc→com.example.leexinchen_intellij_19.0.1, select module-summary.html

### 3. Features that are implemented and are working properly
   - MenuSceneController.java<br>
     - Theme and Level features were implemented and the background color will change while Theme was chosen.
     - Play button was implemented and user could proceed to next scene while this button was clicked.
     - Instruction button was implemented and instruction box will be shown when user clicked this button.
     - Leaderboard button was implemented and system will display RankScene.
   - AccountSceneController.java<br>
     - A text field was provided for user to enter their username.<br>
     - Back button was implemented so that user could go back to previous scene if they wanted to change Theme and 
     or Level chosen.
     - Start button was implemented, user could proceed to next Scene and their username will be passed to Account.java 
     while clicked this button.
   - Account.java
     - In this class will check if the username was existed in text file. If it is existed, the highest score of user
     will be pass and show in GameScene, and system will not create a new account for existed user. If it is not exist,
     the highest score will be set to 0, system will create a new account for the user.
     - There's also a method to save the records of user into text file after the game was ended. For existing user, 
     system will compare if their current score is higher than their highest score, and if it is higher system will store
     current score into file and current score would not be stored otherwise. For new user, system will store their current
     score into text file. Long story short, only highest score of the user will be stored into text file.
   - GameScene.java
     - There will be 3 scores shown in this scene which are score, highest tile, and highest score. Score is the 
     score that user gain in this game, score will be only added while there's cells merged together; highest tile is the
     highest number of a single cell in this game, for example there's cells with number of 2, 4, 8, and 32 in this game,
     highest tile would be 32; highest score is the highest score of user gained from a single game in history.
     - While user reached 2048, system will prompt user to choose if they wanted to continue or quit the game.
     - While the cells are full on screen but there's cells that still can merge, system will ask user to move another
     direction.
     - User will proceed to next Scene while user lost(no moves) or won(reached 2048 and decided to quit) the game.
   - EndGame.java
     - Score of user will be shown on this scene.
     - Current score and history highest score of user will be compared and store into text file for existed user.
     - Next button was implemented and user will proceed to next scene while this button on click.
   - RankSceneController.java
     - Top 10 leaderboard will be shown here. User may not be able to see their names on screen if they did not reach 
     top 10.
     - Quit button was implemented and this application will be stopped while this button is clicked.
     - Restart button was implemented and this application will restart while this button is clicked.

### 4. Features that are implemented and are not working properly
   - GameScene.java
     - While cells with number 2 2 4 8 in a row and user decided to move left, all cells will be merged together and 
     becomes a cell with number of 16, but it should be 4 4 8 if no problem. Situation 8 4 2 2 move right, 8 4 4 move right, 
     etc., will have the same problem.
     - Sometimes system could not detect there are cells with same number nearly and will conclude user has lost the game.
   - EndGame.java
     - Scene size should be same with the previous scene but failed.

### 5. Features that are not implemented and why<br>
I think I have implemented all features that stated.

### 6. List of new Java classes that introduced for assignment
   - RankSceneController class
   - AccountSceneController class
   - MenuSceneController class

### 7. List of Java classes that modified from the given code base
   - Cell class
   - GameScene class
   - TextMaker class
   - EndGame class
   - Account class
   - Main class

### 8. Refactoring activities
- Reorganized java classes to different package with pre-game, in-game, and post-game.
- Singleton design pattern was implemented in class Cell and TextMaker.
- Generate the dependencies in pom.xml file.
- Classes that receive and set data from other classes have utilized get and set methods.