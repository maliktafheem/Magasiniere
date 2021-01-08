package resources.Entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.Static.Data;
import resources.Static.Sounds;
import resources.Windows.Main;


public class Crate extends ImageView {
    private int x;
    private int y;

    // Check if crate can move
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;


    public Crate (int x, int y) {

        this.x = x;
        this.y = y;

        this.setX(y * Data.blockWidth);
        this.setY(x * Data.blockHeight);
        this.setFitHeight(Data.blockHeight);
        this.setFitWidth(Data.blockWidth);


        this.checkDirections();
    }

    // gets the image of crate
    public void getCrateImage(){
        Image image = new Image(getClass().getResourceAsStream("/resources/Assets/crate.png"));
        this.setImage(image);
    }

    // checks if the crate can move in the direction
    public void checkDirections () {
        if(x > 0){
            if(Main.blocks[x - 1][y] == 0 ||
                    Main.blocks[x - 1][y] == 3 ||
                    Main.blocks[x - 1][y] == 5){
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
                    Main.blocks[x + 1][y] == 5){
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
                    Main.blocks[x][y - 1] == 5){
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
                    Main.blocks[x][y + 1] == 5) {
                this.right = true;
            } else {
                this.right = false;
            }
        } else {
            this.right = false;
        }

    }

    public void moveUp () {
        Main.blocks[this.x][this.y] = 0; // crate disappears
        this.x--;
        Main.blocks[this.x][this.y] = 4; // crate reappears
        this.setY(this.x * Data.blockHeight);
        Sounds.playsCrateAudio();

    }

    public void moveDown () {
        Main.blocks[this.x][this.y] = 0;
        this.x++;
        Main.blocks[this.x][this.y] = 4;
        this.setY(this.x * Data.blockHeight);
        Sounds.playsCrateAudio();
    }

    public void moveLeft () {
        Main.blocks[this.x][this.y] = 0;
        this.y--;
        Main.blocks[this.x][this.y] = 4;
        this.setX(this.y * Data.blockWidth);
        Sounds.playsCrateAudio();
    }

    public void moveRight () {
        Main.blocks[this.x][this.y] = 0;
        this.y++;
        Main.blocks[this.x][this.y] = 4;
        this.setX(this.y * Data.blockWidth);
        Sounds.playsCrateAudio();

    }


    public int get_X() {
        return x;
    }


    public int get_Y() {
        return y;
    }


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
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

} // Ends Crate.java