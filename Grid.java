// grid class where the game is played
// contains most of the game logic
public class Grid {

    // grid where game will be played
    // each cell will have value of 0,1,2
    // these correspond to the three states
    // three states: 0 - board, 1 - snake, 2 - food
    private int[][] gameGrid;

    // array to store location/ coordinates of each food object on the grid
    private Food[] foodArray;

    // array to store location/ coordinates of each snake object on the grid
    private Snake[] snakeArray;

    // current direction the snake is moving in
    private int newDirection;

    // direction the snake previously moved in
    // before the player changed direction
    // old direction will be used to validate if the player
    // has entered a valid change in direction
    private int oldDirection;

    // number of food currently in grid
    private int numberOfFood;

    // size of snake
    private int snakeSize;

    // boolean value of to store whether the snake is still alive
    private boolean alive;

    // boolean value to check if the game has started
    // true before game starts
    // false once game has start or once game ends when snake/player dies
    private boolean preGame;

    // relevant getter methods
    public int[][] getGameGrid(){
        return gameGrid;
    }

    public boolean getPreGame() {
        return preGame;
    }

    public boolean getAlive(){
        return alive;
    }

    public int getSnakeSize(){
        return snakeSize;
    }

    // grid object constructor
    Grid( ) {

        // snake is alive when a grid object is created, and before game is played
        alive = true;

        // max number of food has been set to 5
        foodArray = new Food[5];

        // initial size of snake is 3
        snakeArray = new Snake[3];

        // size of grid has been set to a 25 x 25 array for each game
        gameGrid = new int[25][25];

        // snake has not moved yet at start of game
        // has no previous direction
        oldDirection = -1;

        // initialize the initial snake object at the beginnings of the game
        // the snake will be located at the centre of the grid
        snakeArray[0] = new Snake(25 / 2,25 / 2);
        snakeArray[1] = new Snake(25 / 2,25 / 2 - 1);
        snakeArray[2] = new Snake(25 / 2,25 / 2 - 2);

        // there is no food when the grid is first initialized
        numberOfFood = 0;

        // initial size of snake is 3
        snakeSize = 3;

        // when the grid object is first initialized,
        // game is yet to start so preGame will be set to true
        preGame = true;


    }

    // method to move snake
    public void moveSnake() {

        // x and y integer variables
        // these will be the value by which the snake will move
        int x,y;

        // switch statement to check the value of newDirection
        // to determine the direction the snake is supposed to move in
        switch (newDirection) {

            case 1:
                // left: move one space left
                x = -1;
                y = 0;
                break;

            case 2:
                // up: move one space up
                x = 0;
                y = -1;
                break;

            case 3:
                // right: move one space right
                x = 1;
                y = 0;
                break;

            case 4:
                // down: move one space down
                x = 0;
                y = 1;
                break;

            default:
                // default: don't move
                x = 0;
                y = 0;
                break;
        }

        // snake only moves when the game has started
        // and the snake is alive
        // to move the snake, we make a new head and
        // add this to the front of the snake array
        // and remove the last element of the snake array
        if (alive && !preGame) {

            // first create a snake object to be the new head of the snake
            // the coordinates of this new head will be
            // x + the x coordinate of the current first element in the snake array
            // y + the y coordinate of the current first element in the snake array
            // we also use the floorMod method from the Math class after calculating the coordinates
            // to ensure that the snake object remains within the grid
            // if the coordinate exceeds the x,y values of the grid, this will wrap it back to zero
            // likewise if the coordinates become negative, it will wrap it back around to the max values (25)
            Snake newHead = new Snake(Math.floorMod(snakeArray[0].getXCoor() + x, 25), Math.floorMod(snakeArray[0].getYCoor() + y, 25));

            // create a temporary array to store the new snake array
            Snake[] temp = new Snake[snakeArray.length];

            // make the new head the first element in this temporary array
            temp[0] = newHead;

            // copy the first to second last element of the previous snake array
            // to the temp array
            System.arraycopy(snakeArray, 0, temp, 1, snakeArray.length - 1);

            // replace the old snake array with the temp array
            // to update the snake array with the new snake array
            snakeArray = temp;


        }
    }

    // private method to validate the direction the snake is moving
    // if the newDirection is the opposite of the previous direction
    // i.e left/right and up/down
    // the snake doesnt change direction
    private void validateDirection() {

        // as the values of each direction is 2 from its opposite direction
        // we can check if the difference divides by 2
        // to check if the directions are opposite from each other
        if ((oldDirection - newDirection) % 2 == 0 ) {

            // set newDirection to be same as oldDirection
            // snake won't move
            newDirection = oldDirection;
        }

    }

    // method converts keyboard inputs into snake movement
    public void keyboardActions(int keyCode){

        // after the grid object has been initialized,
        // if left or right keys have been pressed, start the game
        // and set value of preGame to false
        if (keyCode == 37 || keyCode == 39) {
            preGame = false;
        }

        // switch method checks what key has been pressed
        // and changes value of direction accordingly
        // validate direction method is also called after changing direction
        // to ensure that the newDirection is a valid one
        switch (keyCode){

            //left arrow key
            case 37:
                oldDirection = newDirection;
                newDirection = 1;
                validateDirection();
                break;

            // up arrow key
            case 38:
                oldDirection = newDirection;
                newDirection = 2;
                validateDirection();
                break;

            // right arrow key
            case 39:
                oldDirection = newDirection;
                newDirection = 3;
                validateDirection();
                break;

            // down arrow key
            case 40:
                oldDirection = newDirection;
                newDirection = 4;
                validateDirection();
                break;
        }
    }

    // method updates the grid with the new positions
    // of each snake object in the snake array, and
    // of each food object in the food array
    public void updateGrid() {

        // clear the grid
        gameGrid = new int[25][25];

        // update snake
        // iterate through the snake array and
        // retrieve the x,y coordinates of each snake object
        // set the state of the element at that x,y coordinate in the grid
        // to 1
        for (Snake s: snakeArray) {
            if (s != null) {
                gameGrid[s.getXCoor()][s.getYCoor()] = 1;
            }
        }

        // update head of the snake
        gameGrid[snakeArray[0].getXCoor()][snakeArray[0].getYCoor()] = 3;

        // update food
        // iterate through the food array and
        // retrieve the x,y coordinates of each food object
        // set the state of the element at that x,y coordinate in the grid
        // to 2
        for (Food f: foodArray) {
            if (f != null) {
                gameGrid[f.getXCoor()][f.getYCoor()] = 2;
            }
        }


    }




    // method checks if the snake has eaten itself
    public void checkLose(){

        // code only runs if the snake is bigger than its initial size
        if(snakeSize >  3) {

            // create a snake object equal to the head of the snake or the first element in the snake array
            Snake head = new Snake(snakeArray[0].getXCoor() ,snakeArray[0].getYCoor());

            // check if the head is equal to any other elements in the snake array
            for (int i = 1; i < snakeArray.length ; i ++ ){
                if(head.getXCoor() == snakeArray[i].getXCoor() && head.getYCoor() == snakeArray[i].getYCoor() ){

                    // if it is set alive to false
                    // snake is now dead and player loses
                    alive = false;
                    break;
                }

            }
        }
    }

    // check if snake has eaten food
    public void checkEaten(){

        // create a snake object equal to the head of the snake or the first element in the snake array
        Snake head = new Snake(snakeArray[0].getXCoor() ,snakeArray[0].getYCoor());

        // iterate through food array
        // if food object exists in the array and x,y coordinates of food object
        // equals to the x,y coordinates of the head, the snake has eaten a piece of food
        for (int i = 0; i <  5; i++) {
            if (foodArray[i] != null && foodArray[i].getYCoor() == head.getYCoor() && foodArray[i].getXCoor() == head.getXCoor()) {

                // update the size of the snake
                snakeSize += 1;
                Snake[] tempSnake = new Snake[snakeSize];
                tempSnake[0]= head;
                System.arraycopy(snakeArray,0, tempSnake,1,snakeArray.length);
                snakeArray = tempSnake;

                // as the food has been eaten, remove it from the food array
                // and update the numberOfFood variable
                Food[] tempFood = new Food[5];
                numberOfFood -= 1;
                System.arraycopy(foodArray,0, tempFood,0,i);
                System.arraycopy(foodArray,i + 1, tempFood,i ,5 - i - 1);
                foodArray = tempFood;
                break;
            }
        }

    }

    // makeFood method makes a new food
    private void makeFood(){

        int foodXCoor, foodYCoor;

        // generate random coordinates for the food
        foodXCoor = (int) (Math.random() * (25));

        foodYCoor = (int) (Math.random() * (25));

        // add the food to the food array
        foodArray[numberOfFood] = new Food(foodXCoor,foodYCoor);

        // update the numberOfFood variable
        numberOfFood += 1;
    }

    // method to check how many food are currently in grid
    // and to update the grid to make more food if necessary,
    // according to the rules specified below
    public void updateFood() {

            int score = snakeSize - 3;

            // if score less than 15
            // there should be a maximum of one food at all times
            if (score < 15 && numberOfFood == 0) {
                makeFood();

                // if score more than 15 and less than 30
                // the number of food increases to 3
            } else if (score >= 15 && score < 30 && numberOfFood < 3) {
                makeFood();

                // if score more than 30
                // the number of food increases to 5
            } else if (score >= 30 && numberOfFood < 5) {
                makeFood();

            }


    }




}