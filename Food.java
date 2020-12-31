// Food class
public class Food {

    // x and y coordinates of each food object
    private int xCoor, yCoor;

    // food object constructor
    Food(int xCoor, int yCoor) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    // getter method to obtain x coordinate of food object
    public int getXCoor() {
        return xCoor;
    }

    // getter method to obtain y coordinate of food object
    public int getYCoor() {
        return yCoor;
    }

}