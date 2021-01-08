package resources.Windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resources.Static.Data;
import resources.Static.Sounds;


public class Instructions extends Application {
    Stage primaryStageSaved;
    AnchorPane root;
    Scene scene;
    public static Boolean backAudio = true;

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
        Image image = new Image("/resources/Assets/instruction.gif");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(Data.sceneHeight);
        imageView.setFitHeight(Data.sceneWidth);

        setListeners();
        root.getChildren().add(imageView);

    }
    void setListeners(){
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
} // Ends Instructions.java