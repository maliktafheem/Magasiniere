package resources.Static;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import static javafx.scene.media.MediaPlayer.INDEFINITE;

public class Sounds {
    private static String path;
    private static Media sound;
    private static MediaPlayer mediaPlayer, mediaPlayer2;
    private static boolean checkBox= true;


    public static void playsButtonAudio(){
        path= System.getProperty("user.dir") +"\\src\\resources\\Assets\\button.mp3";
        File file=new File(path);
        sound=new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }
    public static void playsBackgroundMusic(){
        int s = INDEFINITE;
        path= System.getProperty("user.dir") +"\\src\\resources\\Assets\\SneakySnitch.mp3";
        File file=new File(path);
        sound = new Media(file.toURI().toString());
        mediaPlayer2 = new MediaPlayer(sound);
        mediaPlayer2.setVolume(.2);
        mediaPlayer2.setCycleCount(s);
        mediaPlayer2.play();
    }
    public static void playsLevelAudio(){
        path= System.getProperty("user.dir") +"\\src\\resources\\Assets\\whoa.wav";
        File file=new File(path);
        sound=new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }
    public static void playsNextAudio(){
        path= System.getProperty("user.dir") +"\\src\\resources\\Assets\\next.mp3";
        File file=new File(path);
        sound=new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }
    public static void playsPreviousAudio(){
        path= System.getProperty("user.dir") +"\\src\\resources\\Assets\\previous.mp3";
        File file=new File(path);
        sound=new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }
    public static void playsRestartAudio(){
        path= System.getProperty("user.dir") +"\\src\\resources\\Assets\\restart.mp3";
        File file=new File(path);
        sound=new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }
    public static void playsCrateAudio(){
        path= System.getProperty("user.dir") +"\\src\\resources\\Assets\\cratemoves.wav";
        File file=new File(path);
        sound=new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }

    public static void stopBackgroundMusic(){
        mediaPlayer2.stop();
    }

    public static boolean isCheckBox() {
        return checkBox;
    }

    public static void setCheckBox(boolean checkBox) {
        Sounds.checkBox = checkBox;
    }
} // Sounds.java ends
