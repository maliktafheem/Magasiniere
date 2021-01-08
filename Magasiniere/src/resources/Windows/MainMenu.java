package resources.Windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import resources.Static.Data;
import resources.Static.Sounds;

public class MainMenu extends Application {
    Stage primaryStageSaved;
    AnchorPane root;
    Scene scene;
    VBox vbox; // vertical box
    Text label;
    Button[] buttons = new Button[3];
    public static CheckBox checkBox;

    public void start(Stage primaryStage) {
        try {
            primaryStageSaved = primaryStage;
            root = new AnchorPane();
            root.setId("pane");
            primaryStage.getIcons().add(new Image("/resources/Assets/icon.png"));
            scene = new Scene(root, Data.sceneWidth, Data.sceneHeight);
            if (LevelsMenu.backAudio && Main.backAudio && Instructions.backAudio)
            {
                Sounds.playsBackgroundMusic();
            }
            createElements();
            primaryStageSaved.setTitle(Data.title);
            primaryStageSaved.setScene(scene);
            primaryStageSaved.show();
        } catch(Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }
    void createElements(){
        if(!Data.levelLoaded){
            Data.loadAllLevels();
            Data.levelLoaded = true;
        }
        vbox = new VBox();
        vbox.setPrefWidth(400);
        vbox.setPrefHeight(400);
        vbox.setLayoutX((Data.sceneWidth - 400) / 2);
        vbox.setLayoutY((Data.sceneHeight - 250) / 2);
        vbox.setSpacing(5);
        label = new Text(0, (Data.sceneHeight - 250) / 2 - 50, "Magasinière");
        label.setWrappingWidth(Data.sceneWidth);
        label.setId("title");
        label.setTextAlignment(TextAlignment.CENTER);
        buttons[0] = new Button("New game");
        buttons[1] = new Button("Instructions");
        buttons[2] = new Button("Exit");
        checkBox=new CheckBox("Background Music");
        checkBox.setSelected(Sounds.isCheckBox());
        checkBox.setTextFill(Color.YELLOW);
        checkBox.setLayoutX(230);
        checkBox.setLayoutY(500);
        for(int i = 0; i < 3; i++){
            buttons[i].setPrefWidth(400);
            buttons[i].setPrefHeight(95);
            buttons[i].setFont(new Font(60));
            vbox.getChildren().add(buttons[i]);
        }

        setListeners();
        root.getChildren().add(label);
        root.getChildren().add(vbox);
        scene.getStylesheets().add("/resources/StylingCSS/main.css");
        root.getChildren().add(checkBox);
    }
    void setListeners(){
        buttons[0].setOnAction(e -> {
            Sounds.playsButtonAudio();
            LevelsMenu menu = new LevelsMenu();
            menu.start(primaryStageSaved);
        });
        buttons[1].setOnAction(e -> {
            Sounds.playsButtonAudio();
            Instructions menu = new Instructions();
            menu.start(primaryStageSaved);
        });
        buttons[2].setOnAction(e -> {
            Sounds.playsButtonAudio();
            Platform.exit();
        });
        checkBox.setOnAction(e -> {
            if (!checkBox.isSelected()){
                Sounds.stopBackgroundMusic();
            }
            else
                Sounds.playsBackgroundMusic();
            Sounds.setCheckBox(checkBox.isSelected());
        });

    }
} // Ends MainMenu.java