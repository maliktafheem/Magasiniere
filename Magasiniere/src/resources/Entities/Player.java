package resources.Entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.Windows.Main;
import resources.Static.Data;

public class Player extends ImageView {
    private int x = 0;
    private int y = 0;

    // false if player can not move
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    // Loads images of player
    private Image playerFront = new Image(getClass().getResourceAsStream("/resources/Assets/playerFront.png"));
    private Image playerBack = new Image(getClass().getResourceAsStream("/resources/Assets/playerBack.png"));
    private Image playerRight = new Image(getClass().getResourceAsStream("/resources/Assets/playerRight.png"));
    private Image playerLeft = new Image(getClass().getResourceAsStream("/resources/Assets/playerLeft.png"));


    public Player (int x, int y) {
        this.x = x;
        this.y = y;

        this.setX(y * Data.blockWidth);
        this.setY(x * Data.blockHeight);

        this.setFitHeight(Data.blockHeight);
        this.setFitWidth(Data.blockWidth);

    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        this.setX(y * Data.blockWidth);
        this.setY(x * Data.blockHeight);
        this.checkDirections();
    }

    public void getPlayerImage(int i){
        if(i == 0) this.setImage(playerBack);
        if(i == 1) this.setImage(playerLeft);
        if(i == 2) this.setImage(playerFront);
        if(i == 3) this.setImage(playerRight);
    }

    // checks if the player can move in the direction
    public void checkDirections () {
        if(x > 0){
            if(Main.blocks[x - 1][y] == 0 ||
                    Main.blocks[x - 1][y] == 3 ||
                    Main.blocks[x - 1][y] == 4){

                this.up = true;
            } else {
                this.up = false;
            }
        } else {
            this.up = false;
        }
        if(x < 9){
            if(Main.blocks[x + 1][y] == 0 ||
                    Main.blocks[x + 1][y] == 3 ||
                    Main.blocks[x + 1][y] == 4){

                this.down = true;
            } else {
                this.down = false;
            }
        } else {
            this.down = false;
        }
        if(y > 0){
            if(Main.blocks[x][y - 1] == 0 ||
                    Main.blocks[x][y - 1] == 3 ||
                    Main.blocks[x][y - 1] == 4){
                this.left = true;
            } else {
                this.left = false;
            }
        } else {
            this.left = false;
        }
        if(y < 9){
            if(Main.blocks[x][y + 1] == 0 ||
                    Main.blocks[x][y + 1] == 3 ||
                    Main.blocks[x][y + 1] == 4) {
                this.right = true;
            } else {
                this.right = false;
            }
        } else {
            this.right = false;
        }
    }

    // moves player up.
    public void moveUp (int i) {
        Main.blocks[this.x][this.y] = 0; // clears the place where player land
        this.x--; // decreases the number of row
        if(Main.blocks[this.x][this.y] == 4){
            Crate temp = Main.getCrateAt(this.x, this.y); // refer to that particular crate on blocks
            if(temp.isUp()) {
                temp.moveUp(); // move up from Crate class
            } else {
                this.x++; // restricts player to move above crate if its not movable
            }
        }
        Main.blocks[this.x][this.y] = 5;
        this.setY(this.x * Data.blockHeight); // updates image Y coordinate

        this.getPlayerImage(i);
        this.checkDirections();
    }

    public void moveDown (int i) {
        Main.blocks[this.x][this.y] = 0;
        this.x++;
        if(Main.blocks[this.x][this.y] == 4){
            Crate temp = Main.getCrateAt(this.x, this.y);
            if(temp.isDown()) {
                temp.moveDown();
            } else {
                this.x--;
            }
        }
        Main.blocks[this.x][this.y] = 5;
        this.setY(this.x * Data.blockHeight);

        this.getPlayerImage(i);
        this.checkDirections();
    }

    public void moveLeft (int i) {
        Main.blocks[this.x][this.y] = 0;
        this.y--;
        if(Main.blocks[this.x][this.y] == 4){
            Crate temp = Main.getCrateAt(this.x, this.y);
            if(temp.isLeft()) {
                temp.moveLeft();
            } else {
                this.y++;
            }
        }
        Main.blocks[this.x][this.y] = 5;
        this.setX(this.y * Data.blockWidth);

        this.getPlayerImage(i);
        this.checkDirections();
    }

    public void moveRight (int i) {
        Main.blocks[this.x][this.y] = 0;
        this.y++;
        if(Main.blocks[this.x][this.y] == 4){
            Crate temp = Main.getCrateAt(this.x, this.y);
            if(temp.isRight()) {
                temp.moveRight();
            } else {
                this.y--;
            }
        }
        Main.blocks[this.x][this.y] = 5;
        this.setX(this.y * Data.blockWidth);

        this.getPlayerImage(i);
        this.checkDirections();
    }


    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }


    public boolean isLeft() {
        return left;
    }


    public boolean isRight() {
        return right;
    }

} // Ends Player.java
