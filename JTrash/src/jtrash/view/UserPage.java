package jtrash.view;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserPage {


	public void userPageShow(Stage stage) {
		VBox nuovoContenuto = new VBox();
		Scene sceneCorrente = stage.getScene();
		sceneCorrente.setRoot(nuovoContenuto);

		Home home = new Home();
		home.setBackground(stage);
		stage.setScene(new Scene(new StackPane(home.setBackground(stage),caricaNuovoContenuto())));
	}


	private GridPane caricaNuovoContenuto() {
		// Creazione del form a sinistra
		GridPane formPane = new GridPane();
		formPane.setHgap(60);
		formPane.setVgap(20);
		formPane.setPadding(new Insets(20));

		//	    VBox vboxRegistrazione = new VBox();
		//	    vboxRegistrazione.setAlignment(Pos.CENTER);

		Label titleRegistrazione = new Label("Registrazione");
		titleRegistrazione.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleRegistrazione.setStyle("-fx-text-fill: linear-gradient(to bottom, #FF00FF, #007BA7);");
		formPane.add(titleRegistrazione, 0, 0, 2, 1); 


		// Aggiunta dei campi del form
		Label nomeLabel = new Label("Nome:");
		nomeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: linear-gradient(to bottom, #FF00FF, #007BA7);");
		TextField nomeField = new TextField();
		nomeField.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

		Label cognomeLabel = new Label("Cognome:");
		cognomeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: linear-gradient(to bottom, #FF00FF, #007BA7);");
		TextField cognomeField = new TextField();
		cognomeField.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

		Label dataDiNascitaLabel = new Label("Data di Nascita:");
		dataDiNascitaLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: linear-gradient(to bottom, #FF00FF, #007BA7);");
		DatePicker dataDiNascitaPicker = new DatePicker();
		
		Label nicknameLabel = new Label("Nickname:");
		nicknameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: linear-gradient(to bottom, #FF00FF, #007BA7);");
		TextField nicknameField = new TextField();
		nicknameField.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");

		HBox avatarSection = new HBox(15); // Imposta lo spazio tra gli avatar

		// Creazione degli avatar come esempio (immagini fittizie)
		ImageView avatar1 = new ImageView(new Image(getClass().getResource("/images/donna.jpg").toExternalForm()));
		avatar1.setFitWidth(50);
		avatar1.setFitHeight(50);
		ImageView avatar2 = new ImageView(new Image(getClass().getResource("/images/occhialetto.jpg").toExternalForm()));
		avatar2.setFitWidth(50);
		avatar2.setFitHeight(50);
		ImageView avatar3 = new ImageView(new Image(getClass().getResource("/images/9440461.jpg").toExternalForm()));
		avatar3.setFitWidth(50);
		avatar3.setFitHeight(50);

		// Aggiunta degli avatar all'HBox
		avatarSection.getChildren().addAll(avatar1, avatar2, avatar3);
		
		HBox buttonSection = new HBox(15);
		
		Button registraButton = new Button("Registra");
		registraButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF00FF, #007BA7); -fx-text-fill: white;");
		registraButton.setOnAction(e -> handleRegistra()); // Aggiungi l'handler appropriato per il bottone "Registra"

		Button tornaHomeButton = new Button("Torna alla Home");
		tornaHomeButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF00FF, #007BA7); -fx-text-fill: white;");
		tornaHomeButton.setOnAction(e -> handleTornaHome()); // Aggiungi l'handler appropriato per il bottone "Torna alla Home"

		buttonSection.getChildren().addAll(registraButton, tornaHomeButton);


		// Aggiungi altri campi del form come la selezione dell'avatar

		// Posizionamento dei campi nel GridPane
		formPane.add(nomeLabel, 0, 1);
		formPane.add(nomeField, 1, 1);
		formPane.add(cognomeLabel, 0, 2);
		formPane.add(cognomeField, 1, 2);
		formPane.add(dataDiNascitaLabel, 0, 3);
		formPane.add(dataDiNascitaPicker, 1, 3);
		formPane.add(nicknameLabel, 0, 4);
		formPane.add(nicknameField, 1, 4);
		formPane.add(avatarSection, 1, 5, 2, 1);
		formPane.add(buttonSection, 1, 6);

		// Creazione del titolo degli utenti creati a destra
		//	    VBox titoloUtentiCreati = new VBox();
		//	    titoloUtentiCreati.setAlignment(Pos.CENTER);
		//	    titoloUtentiCreati.getChildren().add(new Label("Utenti Creati"));



		// Creazione del layout generale (GridPane) e disposizione dei componenti
		GridPane contenuto = new GridPane();
		contenuto.setHgap(20);
		contenuto.add(formPane, 0, 0);
		//	    contenuto.add(titoloUtentiCreati, 1, 0);


		return contenuto;

	}
	
	private void handleRegistra() {
		
	}
	
	private void handleTornaHome() {
		
	}

}
