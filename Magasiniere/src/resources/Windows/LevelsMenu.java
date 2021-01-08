package resources.Windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import resources.Static.Data;
import resources.Static.Sounds;

public class LevelsMenu extends Application {
    Stage primaryStageSaved;
    AnchorPane root;
    Scene scene;

    GridPane levelGrid = new GridPane();

    Button[][] levels = new Button[3][5];
    Button prev, next;
    Text label;
    public static Boolean backAudio = true;
    public int startingPoint = 0;

    double gridWidth, gridHeight, blockWidth, blockHeight;

    public void start(Stage primaryStage) {
        try {
            primaryStageSaved = primaryStage;
            root = new AnchorPane();
            root.setId("pane");
            scene = new Scene(root, Data.sceneWidth, Data.sceneHeight);

            createElements();

            primaryStageSaved.setTitle(Data.title);
            primaryStageSaved.setScene(scene);
            primaryStageSaved.show();

        } catch(Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    void createElements() {
        scene.getStylesheets().add("/resources/StylingCSS/main.css");

        gridWidth = 400;
        gridHeight = 300;
        blockWidth = gridWidth / 5;
        blockHeight = gridHeight / 3 - 10;

        levelGrid.setPrefWidth(gridWidth);
        levelGrid.setPrefHeight(gridHeight);

        levelGrid.setHgap(10);
        levelGrid.setVgap(10);

        levelGrid.setLayoutX((Data.sceneWidth - gridWidth) / 2);
        levelGrid.setLayoutY((Data.sceneHeight - gridHeight) / 2);

        resetButtons();

        prev = new Button("◄");
        next = new Button("►");

        prev.setPrefWidth(blockWidth);
        prev.setPrefHeight(blockHeight);
        prev.setLayoutX(10);
        prev.setLayoutY((Data.sceneHeight - blockHeight) / 2 - 5);
        prev.setStyle("-fx-font-size: 40px");

        next.setPrefWidth(blockWidth);
        next.setPrefHeight(blockHeight);
        next.setLayoutX(Data.sceneWidth - blockWidth - 10);
        next.setLayoutY((Data.sceneHeight - blockHeight) / 2 - 5);
        next.setStyle("-fx-font-size: 40px");

        label = new Text(0, (Data.sceneHeight - gridHeight) / 2 - 50, "Levels");
        label.setWrappingWidth(Data.sceneWidth);
        label.setId("label");
        label.setTextAlignment(TextAlignment.CENTER);

        setListeners();

        root.getChildren().add(levelGrid);
        root.getChildren().add(prev);
        root.getChildren().add(next);
        root.getChildren().add(label);
    }

    void resetButtons(){
        levelGrid.getChildren().clear();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                int index = startingPoint + i * 5 + j;

                levels[i][j] = new Button("" + (index + 1));
                levels[i][j].setPrefWidth(blockWidth);
                levels[i][j].setPrefHeight(blockHeight);
                levels[i][j].setStyle("-fx-font-size: 33px");

                if(index >= Data.levels.size()){
                    levels[i][j].setVisible(false);
                }

                levels[i][j].setOnAction(e -> {
                    Sounds.playsButtonAudio();
                    Main main = new Main();
                    main.level = index;
                    main.start(primaryStageSaved);
                });

                levelGrid.getChildren().add(levels[i][j]);
                levelGrid.setConstraints(levels[i][j], j, i);
            }
        }
    }

    void setListeners(){
        prev.setOnAction(e -> {
            startingPoint -= 15;
            if(startingPoint < 0) startingPoint = 0;
            resetButtons();
            Sounds.playsButtonAudio();
        });
        next.setOnAction(e -> {
            startingPoint += 15;
            if(startingPoint >= Data.levels.size()) startingPoint -= 15;
            resetButtons();
            Sounds.playsButtonAudio();
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                backAudio = false;
                MainMenu main = new MainMenu();
                main.start(primaryStageSaved);
                ke.consume();
                Sounds.playsButtonAudio();
            }
        });
    }
} // Ends LevelsMenu.java

