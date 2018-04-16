package TabularFunctions;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationBox {
	static boolean choice ;
	public static boolean errorMessage(String title, String message) {
		
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		Label label = new Label(message);
		
		
		Button yesButton = new Button("Yes");
		yesButton.setOnAction(e -> {
			choice = true;
			window.close();
			
		});
		GridPane.setConstraints(yesButton, 2, 2); 
		
		
		Button noButton = new Button("No");
		noButton.setOnAction(e -> {
			choice = false;
			window.close();
			
		});
		GridPane.setConstraints(noButton, 3, 2);
		GridPane layout = new GridPane();
		layout.setAlignment(Pos.CENTER);
		
		layout.setPadding(new Insets(10,10,10,10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(label, yesButton ,noButton);
		Scene displayScene = new Scene(layout, 300, 100);
		window.setTitle(title);
		window.setScene(displayScene);
		window.showAndWait();
		return choice;
	}

}
