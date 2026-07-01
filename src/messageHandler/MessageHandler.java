package messageHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class MessageHandler {
	
	@FXML
	private Label message;
	
	@FXML
	private Pane image; 
	
	private void setupAlert(String textToDisplay, MessageType type) {
		this.message.setText(textToDisplay);
		
		// 1. Map your custom Enum cases to the built-in JavaFX AlertTypes
		AlertType alertType = switch(type) {
			case Error -> AlertType.ERROR;
			case Warning -> AlertType.WARNING;
			case Info -> AlertType.INFORMATION;
			case Success -> AlertType.INFORMATION; // System doesn't have a unique success icon, uses info
		};
		
		// 2. Instantiate a temporary default alert and steal its system graphic Node
		Alert tempAlert = new Alert(alertType);
		Node systemIcon = tempAlert.getGraphic();
		
		// 3. Inject the stolen system icon node cleanly into your custom layout container
		if (systemIcon != null && image != null) {
			image.getChildren().clear();
			image.getChildren().add(systemIcon);
		}
	}
	
	@FXML
	private void closePopup(ActionEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	public static void showMessage(String textToDisplay, MessageType type) {
	    if (!Platform.isFxApplicationThread()) {
	        Platform.runLater(() -> showMessage(textToDisplay, type));
	        return;
	    }

	    try {
	        FXMLLoader loader = new FXMLLoader(MessageHandler.class.getResource("Handler.fxml"));
	        Parent root = loader.load();

	        // Update to call our new composite setup methods instead of just setMessage
	        MessageHandler controller = loader.getController();
	        controller.setupAlert(textToDisplay, type);

	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        
	        stage.setResizable(false);
	        
	        switch(type) {
	        	case Error -> stage.setTitle("Kļūdas paziņojums");
	        	case Warning -> stage.setTitle("Brīdinājuma paziņojums");
	        	case Info -> stage.setTitle("Info paziņojums");
	        	case Success -> stage.setTitle("Veiksmes paziņojums");
	        }
	        
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}