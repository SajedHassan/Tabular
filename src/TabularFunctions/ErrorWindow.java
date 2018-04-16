package TabularFunctions;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorWindow {
	public static void errorMessage(String title, String message) {

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		Label label = new Label(message);
		Button button = new Button("Ok");
		button.setOnAction(e -> window.close());
		VBox layout = new VBox(2);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(label, button);
		Scene displayScene = new Scene(layout, 400, 250);
		window.setTitle(title);
		window.setScene(displayScene);
		window.showAndWait();
	}

}
