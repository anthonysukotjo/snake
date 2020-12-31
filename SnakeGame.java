/*******************************************************************

 Program name:         Snake
 Program description:  Implement the classic arcade game Snake

 Class: CS101 sec. 005
 Name:  Anthony Ardi Sukotjo
 Date:  Nov 25 2020

 Note: This is the SnakeGame Class
 *******************************************************************/

import processing.core.PApplet;
import processing.core.PFont;

public class SnakeGame extends PApplet {

    // grid object
    private Grid snakeGameGrid;

    // font object
    private PFont font;

    // players current HighScore
    private int highScore;

    //to if the player has already pressed a key
    private boolean onePress;

    // SnakeGame constructor
    public SnakeGame() {
        snakeGameGrid = new Grid();
        highScore = 0;
        onePress = true;

    }

    // color in the screen based on the values in the game's gameGrid
    public void colorGrid(int[][] grid) {
        for (int x = 0; x < 25 ; x++) {
            for (int y = 0; y < 25 ; y++) {

                // checks each x,y cell in the grid
                switch (grid[x][y]) {

                    //empty cell: black
                    case 0:
                        fill(0);
                        rect( x * 30, y * 30, 30, 30);
                        break;

                    // snake: red
                    case 1:
                        fill(255, 0,0);
                        rect(x * 30, y * 30, 30, 30);
                        break;

                    // food: green
                    case 2:
                        fill(0,255, 0);
                        rect(x * 30, y * 30, 30, 30);
                        break;

                    // snake head: blue
                    case 3:
                        fill(0,0, 255);
                        rect(x * 30, y * 30, 30, 30);
                        break;

                }

            }


        }

    }


    public static void main(String[] args) {

        PApplet.main("SnakeGame");

    }


    // set up size of window
    public void settings() {

        size(750, 750);

    }

    public void setup() {

        frameRate(10);

        font = createFont("Futura", 100, true);

    }


    public void keyPressed() {

        boolean validKeyPresses = keyCode == LEFT || keyCode == RIGHT || keyCode == UP || keyCode == DOWN;



        // check if the snake is alive
        if (snakeGameGrid.getAlive()) {


            // if statement checks if a key has already been pressed
            // and that the player pressed a valid key
            // this ensures that the player is only able to hit a key once
            // everytime the draw method is called
            if(validKeyPresses && onePress){

                // call the keyboardActions method
                // on the snakeGameGrid object to convert the key press
                // to an action in the game
                snakeGameGrid.keyboardActions(keyCode);

                // once a valid key has been pressed set onePressed to false
                // this prevents the game from registering more than one key press from the player
                // for every time the draw method is called
                onePress = false;
            }

        } else {

            // snake is dead
            // pressing SpaceBar resets board, updates high score
            // and starts a new game
            if (keyCode == 32) {

                // update high score
                if (snakeGameGrid.getSnakeSize() - 3 > highScore) {

                    highScore = snakeGameGrid.getSnakeSize() - 3;
                }

                // start a new game
                startNewGame();
            }

        }



    }


    // method updates the speed of the snake depending on the score
    // speed changes by changing the frame rate
    // frame rate increases by 2 at every interval of 10 in the players score
    public void updateSpeed(){

        frameRate(10 + (snakeGameGrid.getSnakeSize() - 3 )/10 * 2);
    }

    // start a new game by replacing the snakeGameGrid property
    // with a new grid object
    public void startNewGame(){

        snakeGameGrid = new Grid();
    }

    // methods that are called for each cycle
    public void playSnakeGame(){

        // set onePress as true so player can make a new action
        onePress = true;

        // move the snake
        snakeGameGrid.moveSnake();

        // check if the snake has eaten itself and if the player lost
        snakeGameGrid.checkLose();

        // check if the snake has eaten food
        snakeGameGrid.checkEaten();

        // update speed of game depending on player score
        updateSpeed();

        // update the foodArray on the grid
        snakeGameGrid.updateFood();

        // update the grid to the new values in the snake and food arrays
        snakeGameGrid.updateGrid();

        // color in the user interface depending on the new values of the grid
        colorGrid(snakeGameGrid.getGameGrid());

    }

    public void draw() {

        background(255,255,255);

        // play the game
        playSnakeGame();

        // display the players score at the top of the window
        textFont(font, 35);
        fill(255,255,255);
        textAlign(CENTER);
        text("Score: " + (snakeGameGrid.getSnakeSize() - 3), 375, 70);
        textFont(font, 20);

        // display the current level of the game at the bottom right corner of the window
        // speed of the snake increases at each level of the game
        fill(255,255,255);
        text("High Score: " + (highScore),100 , 720);
        textFont(font, 50);
        text("LEVEL " + (((snakeGameGrid.getSnakeSize() - 3 )/10) + 1),640 , 720);

        // if snake is dead display the a message at the centre of the screen
        if(!snakeGameGrid.getAlive()){

            // message to tell player that they have lost
            textFont(font, 75);
            fill(153,0,0);
            textAlign(CENTER);
            text("YOU LOSE!", 375, 375);
            textFont(font, 20);

            // message to tell player how to start a new game
            fill(255,255,255);
            text("Hit the SpaceBar to Start a New Game", 375, 420);
        }

        // if the preGame property of the snakeGameGrid object is true
        // we display a message to the player on how to start the game
        // at the center of the screen
        if(snakeGameGrid.getPreGame()) {

            textFont(font, 35);
            fill(153,0,0);
            textAlign(CENTER);
            text("Hit the Left or Right Arrow Keys to Start", 375, 375);
            }

        }
    }



