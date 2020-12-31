// Snake Class
public class Snake {

    // X and Y coordinates of each snake object
    private int xCoor, yCoor;

    // Snake Constructor
    Snake(int xCoor, int yCoor) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    // getter method to retrieve x-coordinate
    public int getXCoor() {
        return xCoor;
    }

    // getter method to retrieve y-coordinate
    public int getYCoor() {
        return yCoor;
    }

}