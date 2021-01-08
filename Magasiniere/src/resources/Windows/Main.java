package resources.Windows;


import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import resources.Entities.Area;
import resources.Entities.Crate;
import resources.Entities.Player;
import resources.Static.Data;
import resources.Static.Sounds;

public class Main extends Application {

    AnchorPane root;  // Root Pane
    AnchorPane areaLayer, cratesLayer, playerLayer; // Layers

    // Scene parameters
    Stage primaryStageSaved;
    Scene scene;

    // Image places 10x10
    ImageView[][] images; // to replace the level values with object images ref: Data.java

    // Crates and Areas holders
    // creating an generic lists ( restricts the type of object to be stored)
    public static List<Crate> crates; // static because used in Crate class
    List<Area> areas;

    // Player 1 instance only
    Player player;

    // Level as 10x10 int
    public static int[][] blocks;

    // Level number
    public int level = 0;
    int levelMax = 0;

    public boolean rerun = true;
    public static Boolean backAudio = true;

    // block names to find them from src/res/Assets/
    String[] blockNames = new String[]{
            "floor.png",
            "externaltiles.png",
            "wall.png",
            "area.png",
            "floor.png",
            "floor.png"
    };


    // JavaFX standard main method
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        try {
            primaryStageSaved = primaryStage;
            root = new AnchorPane();
            scene = new Scene(root, Data.sceneWidth, Data.sceneHeight); // values of width and height from Data.java

            firstRun();
            restart();

            primaryStageSaved.setTitle(Data.title);
            primaryStageSaved.setScene(scene);
            primaryStageSaved.show();

        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    // Only runs once at the beginning of program
    public void firstRun(){

        Data.getBlockSize(); // Block size is scene size divided by 10

        player = new Player(2,2);
        images = new ImageView[10][10];

        Image imgnull = new Image(getClass().getResourceAsStream("/resources/Assets/null.png"));

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                images[i][j] = new ImageView(imgnull);

                images[i][j].setFitHeight(Data.blockHeight);
                images[i][j].setFitWidth(Data.blockWidth);

                images[i][j].setX(j* Data.blockWidth);
                images[i][j].setY(i* Data.blockHeight);
            }
        }

        crates = new ArrayList<>();
        areas = new ArrayList<>();

        playerLayer = new AnchorPane();
        playerLayer.getChildren().add(player);

        setListeners();
    }

    // Runs each time when new level and reset
    public void restart(){
        blocks = copyLevel(Data.levels.get(level));
        levelMax = Data.levels.size() - 1;

        areaLayer = new AnchorPane();
        cratesLayer = new AnchorPane();

        crates.clear();
        areas.clear();

        root.getChildren().clear();

        createElements();

        root.getChildren().add(areaLayer);
        root.getChildren().add(cratesLayer);
        root.getChildren().add(playerLayer);

        getImages();
        checkArea();
    }

    // Copies level from static script values to this script (Not changing static var)
    int[][] copyLevel(int[][] lvlins){
        int[][] lvlcopy = new int[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                lvlcopy[i][j] = lvlins[i][j];
            }
        }
        return lvlcopy;
    }

    // Managing all elements
    void createElements() {
        if(rerun)
            root.getChildren().clear();

        getBackgroundImage();
        manageAllBlocks();

        rerun = false;
    }

    // Getting background image (Background)
    public void getBackgroundImage(){
        Image image = new Image(getClass().getResourceAsStream("/resources/Assets/background.png"));
        ImageView imageView = new ImageView(image);

        imageView.setX(0);
        imageView.setY(0);

        imageView.setFitHeight(Data.sceneHeight);
        imageView.setFitWidth(Data.sceneWidth);

        root.getChildren().add(imageView);
    }

    // Getting all block images and block objects
    public void manageAllBlocks(){
        for(int j = 0; j < 10; j++){
            for(int i = 0; i < 10; i++){
                if(blocks[i][j] == 4) // 4 in blocks is replaced by crates.
                {
                    Crate tempCrate = new Crate(i,j);
                    crates.add(tempCrate);
                    cratesLayer.getChildren().add(tempCrate);
                }
                if(blocks[i][j] == 5) // Sets player's starting position
                {
                    player.setPosition(i,j);
                }
                if(blocks[i][j] == 3)  // replaces 3 in level array with areas
                {
                    Area tempArea = new Area(i,j);
                    areas.add(tempArea); // Areas added to Areas list
                    areaLayer.getChildren().add(tempArea);
                    continue;
                }
                if(blocks[i][j] == 6) // when area and crate both are at same coordinates
                {
                    Area tempArea = new Area(i,j);
                    areas.add(tempArea);
                    areaLayer.getChildren().add(tempArea);

                    Crate tempCrate = new Crate(i,j);
                    crates.add(tempCrate);
                    cratesLayer.getChildren().add(tempCrate);

                    blocks[i][j] = 4;
                    continue;
                }

                // Adds images of ground and walls
                Image image = new Image(getClass().getResourceAsStream("/resources/Assets/" + blockNames[blocks[i][j]]));
                images[i][j].setImage(image); // add respective image to the images array ( of type ImageView)

                if(rerun) {
                    root.getChildren().add(images[i][j]);
                }
            }
        }
    }

    // Getting crate by position (for Crate script)
    public static Crate getCrateAt(int x, int y) {

        for(int i = 0; i < crates.size(); i++){
            // here .x is used from Crate class
            // and crates is list from main class
            // crates.get(i) gives the object at i index in array list of crates
            if(crates.get(i).get_X() == x && crates.get(i).get_Y() == y)
            {
                return(crates.get(i));
            }
        }

        return null;
    }

    // Setting movement (W,A,S,D), reset (R), prev-next level (B,N) buttons
    void setListeners(){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.W) {
                    move(0);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.A) {
                    move(1);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.S) {
                    move(2);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.D) {
                    move(3);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.B) {
                    levelChange(-1);
                    ke.consume();
                    Sounds.playsPreviousAudio();
                }
                if (ke.getCode() == KeyCode.N) {
                    levelChange(1);
                    ke.consume();
                    Sounds.playsNextAudio();
                }
                if (ke.getCode() == KeyCode.R) {
                    levelChange(0);
                    ke.consume();
                    Sounds.playsRestartAudio();
                }
                if (ke.getCode() == KeyCode.ESCAPE) {
                    backAudio = false;
                    MainMenu menu = new MainMenu();
                    menu.start(primaryStageSaved);
                    ke.consume();
                    Sounds.playsButtonAudio();
                }
            }
        });
    }

    // Moving player
    void move(int i){
        if(i == 0 && player.isUp()){
            player.moveUp(i);
        }
        if(i == 1 && player.isLeft()){
            player.moveLeft(i);
        }
        if(i == 2 && player.isDown()){
            player.moveDown(i);
        }
        if(i == 3 && player.isRight()){
            player.moveRight(i);
        }
        checkAllCrateDirections();
        checkArea();
    }

    // Getting image of areas, player and crates
    void getImages(){
        for(int i = 0; i < areas.size(); i++){
            areas.get(i).getAreaImage();
        }
        for(int i = 0; i < crates.size(); i++){
            crates.get(i).getCrateImage();
        }
        player.getPlayerImage(2);
    }

    // Checking all Areas if they are closed
    void checkArea(){
        int areaCoveredTemp = 0;

        for(int i = 0; i < areas.size(); i++){
            for(int j = 0; j < crates.size(); j++){
                if(crates.get(i).get_X() == areas.get(j).get_X() && crates.get(i).get_Y() == areas.get(j).get_Y()){
                    crates.get(i).setEffect(new Glow(0.8));
                    areaCoveredTemp++;
                    break;
                } else {
                    crates.get(i).setEffect(null);
                }
            }
        }

        if(areaCoveredTemp == areas.size()){
            Sounds.playsLevelAudio();
            levelChange(1);
        }
    }

    // Changing level prev, next, reset
    void levelChange(int i){
        rerun = true;
        level += i;

        if(level > levelMax) level = 0;
        if(level < 0) level = levelMax;

        restart();
    }

    // Check all crate movement directions
    public static void checkAllCrateDirections(){
        for(int i = 0; i < crates.size(); i++){
            crates.get(i).checkDirections();
        }
    }

} // Ends Main.java
