package jtrash.view;

import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class Modal {


	//TODO: IMPOSTARE LE COSTANTI
	public void mostraDialogoConferma() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(""); 
		alert.setHeaderText("Conferma"); 

		Label header = new Label("Torna al desktop");
		header.setFont(Font.font("Arial", FontWeight.BOLD, 25)); 
		header.setStyle("-fx-text-fill: linear-gradient(to bottom, #FF00FF, #007BA7);"); 

		alert.getDialogPane().setHeaderText(null); 
		alert.getDialogPane().setGraphic(null); 

		VBox content = new VBox();
		content.setAlignment(Pos.CENTER);
		content.setSpacing(20);

		Text testoConferma = new Text("Sei sicuro di voler tornare al desktop?");
		testoConferma.setFont(Font.font("Arial", 18)); 
		testoConferma.setFill(Color.WHITE); 

		content.getChildren().addAll(header, testoConferma);
		alert.getDialogPane().setContent(content);
		
		ButtonType okButtonType = ButtonType.OK;
		Button okButton = (Button) alert.getDialogPane().lookupButton(okButtonType);
		okButton.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		ButtonType cancelButtonType = ButtonType.CANCEL;
		Button cancelButton = (Button) alert.getDialogPane().lookupButton(cancelButtonType);
		cancelButton.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setStyle("-fx-background-color: #333333; -fx-font-size: 18px;");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK){
			System.exit(0);
		}
	}



}
