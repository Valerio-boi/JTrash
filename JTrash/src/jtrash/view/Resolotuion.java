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
import jtrash.controller.ResolutionController;
import utility.Constants;

public class Resolotuion {

	private Alert alert;
	private ResolutionController resolutionController;

	public void mostraDialogoOpzioni(Stage stage) {
		alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(""); 
		alert.setHeaderText(Constants.Label.CONFERMA); 

		Label header = new Label(Constants.Label.RISOLUZIONE_DESIDERATA);
		header.setFont(Font.font(Constants.Font.ARIAL_FONT, FontWeight.BOLD, 25)); 
		header.setStyle(Constants.Css.CLASSIC_CSS); 

		alert.getDialogPane().setHeaderText(null); 
		alert.getDialogPane().setGraphic(null); 

		VBox content = new VBox();
		content.setAlignment(Pos.CENTER);
		content.setSpacing(20);

		content.getChildren().addAll(header);
		alert.getDialogPane().setContent(content);

		ButtonType resolution1 = new ButtonType(Constants.Label.RIS_1920);
		ButtonType resolution2 = new ButtonType(Constants.Label.RIS_1280);
		ButtonType resolution3 = new ButtonType(Constants.Label.RIS_1366);
		ButtonType resolution4 = new ButtonType(Constants.Label.RIS_1024);
		ButtonType resolution5 = new ButtonType(Constants.Label.RIS_800);

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
		dialogPane.setStyle(Constants.Css.STYLE_DIALOG);

		Optional<ButtonType> result = alert.showAndWait();
		resolutionController = new ResolutionController();
		resolutionController.setRisoluzione(result, stage);
		
		
	}

	
	private void setStyle(ButtonType buttonType) {
		Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
		button.getStylesheets().add(getClass().getResource(Constants.Path.CSS).toExternalForm());
	}


}
