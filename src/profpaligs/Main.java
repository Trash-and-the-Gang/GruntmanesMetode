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
        Parent root = FXMLLoader.load(getClass().getResource("javafx_test.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("/profpaligs/profpaligs_icon.png"));
        
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("PROFPalīgs");
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("Pilnekrāna režīms");
        
        
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}