package jtrash.view;

import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utility.Constants;

public class Resolotuion {

	private Alert alert;

	public void mostraDialogoOpzioni(Stage stage) {
		alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(""); 
		alert.setHeaderText("Conferma"); 

		Label header = new Label("Seleziona la resoluzione desiderata");
		header.setFont(Font.font("Arial", FontWeight.BOLD, 25)); 
		header.setStyle("-fx-text-fill: linear-gradient(to bottom, #FF00FF, #007BA7);"); 

		alert.getDialogPane().setHeaderText(null); 
		alert.getDialogPane().setGraphic(null); 

		VBox content = new VBox();
		content.setAlignment(Pos.CENTER);
		content.setSpacing(20);

		content.getChildren().addAll(header);
		alert.getDialogPane().setContent(content);

		ButtonType resolution1 = new ButtonType("1920x1080");
		ButtonType resolution2 = new ButtonType("1280x720");
		ButtonType resolution3 = new ButtonType("1366x768");
		ButtonType resolution4 = new ButtonType("1024x768");
		ButtonType resolution5 = new ButtonType("800x600");

		ButtonType cancel = new ButtonType("Annulla", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(resolution1, resolution2, resolution3, 
			 resolution4, resolution5, cancel);
		
		setStyle(resolution1);
		setStyle(resolution2);
		setStyle(resolution3);
		setStyle(resolution4);
		setStyle(resolution5);
		setStyle(cancel);
		
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setStyle(
				"-fx-background-color: #333333; " +
				"-fx-font-size: 18px;");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {	    	
			if (result.get() == resolution1) {
				impostaRisoluzione(1920, 1080, stage);
			}else if (result.get() == resolution2) {
				impostaRisoluzione(1280, 720, stage);
			}else if (result.get() == resolution3) {
				impostaRisoluzione(1366, 768, stage);
			}else if (result.get() == resolution4) {
				impostaRisoluzione(1024, 768, stage);
			}else if (result.get() == resolution5) {
				impostaRisoluzione(800, 600, stage);
			}
		}
		

	}

	public void impostaRisoluzione(double width, double height, Stage stage) {
		stage.setWidth(width);
		stage.setHeight(height);
		stage.setFullScreen(false);
	}
	
	private void setStyle(ButtonType buttonType) {
		Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
		button.getStylesheets().add(getClass().getResource(Constants.Path.CSS).toExternalForm());
	}


}
