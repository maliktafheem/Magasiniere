package resources.Entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.Static.Data;

public class Area extends ImageView {
    private int x = 0;
    private int y = 0;

    private boolean covered;

    public Area (int x, int y) {
        this.x = x;
        this.y = y;

        this.setX(y * Data.blockWidth);
        this.setY(x * Data.blockHeight);
        this.setFitHeight(Data.blockHeight);
        this.setFitWidth(Data.blockWidth);
    }

    public void getAreaImage(){
        Image image = new Image(getClass().getResourceAsStream("/resources/Assets/area.png"));
        this.setImage(image);
    }

    public void close(){
        covered = true;
    }

    public void open(){
        covered = false;
    }

    public int get_X() {
        return x;
    }

    public int get_Y() {
        return y;
    }

} // Ends Area.java

