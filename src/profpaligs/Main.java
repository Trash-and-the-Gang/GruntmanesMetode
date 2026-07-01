package profpaligs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	Parent root = FXMLLoader.load(Main.class.getResource("/profpaligs/Main.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("/profpaligs/profpaligs_icon.png"));
        
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("PROFPalīgs");
        primaryStage.setFullScreenExitHint("Pilnekrāna režīms. Lai izietu, spied ESC");
        primaryStage.setResizable(false);
        primaryStage.setMinWidth(640);
        primaryStage.setMinHeight(480);
        
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}