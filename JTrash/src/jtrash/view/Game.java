package jtrash.view;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jtrash.model.User;
import utility.Constants;

public class Game {

	private User user;

	public void gamePageShow(Stage stage, User user) {
		StackPane contenuto = new StackPane();
		this.user = user;
		contenuto.setAlignment(Pos.TOP_CENTER);

		// Testo per il benvenuto e l'username con gradiente
		Text benvenutoText = new Text("Benvenuto, " + user.getNickname() + "!");
		benvenutoText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
		LinearGradient benvenutoGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
				new Stop(0, Color.DARKORANGE), new Stop(1, Color.GOLD));
		benvenutoText.setFill(benvenutoGradient);

		Text selezionaText = new Text("Seleziona con quante persone vuoi giocare:");
		selezionaText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		LinearGradient selezionaGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
				new Stop(0, Color.DARKORANGE), new Stop(1, Color.GOLD));
		selezionaText.setFill(selezionaGradient);

		// Aggiungi effetto ombra
		DropShadow shadow = new DropShadow();
		shadow.setColor(Color.BLACK);
		benvenutoText.setEffect(shadow);
		selezionaText.setEffect(shadow);

		// Effetti e animazioni per il benvenuto
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), benvenutoText);
		scaleTransition.setToX(1.5);
		scaleTransition.setToY(1.5);
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(Timeline.INDEFINITE);
		scaleTransition.play();

		// Bottoni per la scelta del numero di giocatori
		Button contro1Button = createStyledButton("Contro 1", 20);
		Button contro3Button = createStyledButton("Contro 2", 20);
		Button contro5Button = createStyledButton("Contro 4", 20);

		contro1Button.setOnAction(e -> createGameTable(3, stage));
		contro3Button.setOnAction(e -> createGameTable(4, stage));
		contro5Button.setOnAction(e -> createGameTable(6, stage));




		// Layout per i bottoni
		HBox buttonsBox = new HBox(20);
		buttonsBox.setAlignment(Pos.CENTER);
		buttonsBox.getChildren().addAll(contro1Button, contro3Button, contro5Button);

		// Layout principale
		VBox mainLayout = new VBox(20);
		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.getChildren().addAll(benvenutoText, selezionaText, buttonsBox);

		contenuto.getChildren().addAll(new StackPane(setBackground(stage)), mainLayout);

		Scene sceneCorrente = stage.getScene();
		sceneCorrente.setRoot(contenuto);
	}

	public void createGameTable(int numberPlayers, Stage stage) {
		StackPane contenuto = new StackPane();
		contenuto.setAlignment(Pos.TOP_CENTER);

		GridPane gameTable = new GridPane();
		gameTable.setAlignment(Pos.CENTER);

		int rows = numberPlayers / 2 + numberPlayers % 2;
		int cols = 2;

		// Vincoli di layout per far sì che le celle si adattino alla grandezza della finestra
		for (int i = 0; i < rows; i++) {
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setVgrow(Priority.ALWAYS);
			rowConstraints.setFillHeight(true);
			gameTable.getRowConstraints().add(rowConstraints);
		}
		for (int i = 0; i < cols; i++) {
			ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setHgrow(Priority.ALWAYS);
			colConstraints.setFillWidth(true);
			gameTable.getColumnConstraints().add(colConstraints);
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				String playerName = "";
				boolean pannel = true;
				if(i==0 && j == 0){
					playerName = user.getNickname();
				}else if((i+1) == rows && (j+1) == cols) {
					playerName = "Pannel control";
					pannel = false;
				}else {
					playerName = "Player " + (i * cols + j + 1);
				}
				VBox playerArea = createPlayerArea(playerName, pannel);
				if (i * cols + j < numberPlayers) {
					gameTable.add(playerArea, j, i);
				} else if (numberPlayers % 2 == 1 && j == 0) {
					gameTable.add(new Region(), 1, i); // Aggiungi una regione vuota nella colonna 2 per centrare
				}
			}
		}

		contenuto.getChildren().addAll(new StackPane(setBackground(stage)), gameTable);

		Scene sceneCorrente = stage.getScene();
		sceneCorrente.setRoot(contenuto);
	}

	private VBox createPlayerArea(String playerName, boolean controllPannel) {
		VBox playerArea = new VBox();
		playerArea.setPrefSize(200, 200); // Imposta le dimensioni dell'area del giocatore
		playerArea.setStyle("-fx-border-color: white; -fx-border-width: 1px;"); // Bordo per l'area del giocatore

		// Label per il nickname del giocatore
		Label nameLabel = new Label(playerName);
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Testo più grande
		nameLabel.setAlignment(Pos.CENTER); // Allineamento del testo al centro
		nameLabel.setMaxWidth(Double.MAX_VALUE); // Per far sì che il testo si estenda su tutta la larghezza
		VBox.setVgrow(nameLabel, Priority.ALWAYS); // Permette all'etichetta di espandersi verticalmente

		// Applica uno stile con gradiente al testo
		LinearGradient textGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
				new Stop(0, Color.GOLD), new Stop(1, Color.DARKORANGE));
		nameLabel.setTextFill(textGradient);

		// Animazione sul testo usando la scala
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), nameLabel);
		scaleTransition.setToX(1.2); // Ingrandisce il testo del 20%
		scaleTransition.setToY(1.2); // Ingrandisce il testo del 20%
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(Timeline.INDEFINITE);
		scaleTransition.play();

		playerArea.setSpacing(5);

		if(controllPannel) {
			// Sezione per le carte sopra
			HBox topCards = new HBox(20); // 20 di spazio tra le carte
			topCards.setAlignment(Pos.CENTER);

			// Aggiungi le carte sopra
			for (int i = 0; i < 5; i++) {
				// Aggiungi l'immagine delle carte sopra
				ImageView cardImage = new ImageView(new Image(getClass().getResource(Constants.Path.EXMPLE_CARD).toExternalForm()));
				// Imposta le dimensioni delle immagini delle carte
				cardImage.setFitWidth(100); // Larghezza maggiore
				cardImage.setFitHeight(160); // Altezza maggiore
				topCards.getChildren().add(cardImage);
			}

			// Aggiungi spazio tra le carte sopra e sotto
			Region space = new Region();
			space.setPrefHeight(20); // Altezza dello spazio tra le due righe

			// Sezione per le carte sotto
			HBox bottomCards = new HBox(20); // 20 di spazio tra le carte
			bottomCards.setAlignment(Pos.CENTER);

			// Aggiungi le carte sotto
			for (int i = 0; i < 5; i++) {
				// Aggiungi l'immagine delle carte sotto
				ImageView cardImage = new ImageView(new Image(getClass().getResource(Constants.Path.EXMPLE_CARD).toExternalForm()));
				// Imposta le dimensioni delle immagini delle carte
				cardImage.setFitWidth(100); // Larghezza maggiore
				cardImage.setFitHeight(160); // Altezza maggiore
				bottomCards.getChildren().add(cardImage);
			}
			playerArea.getChildren().addAll(nameLabel, topCards, space, bottomCards);
		}else {
			    ImageView deckImage = new ImageView(new Image(getClass().getResource(Constants.Path.EXMPLE_CARD).toExternalForm()));
		        deckImage.setFitWidth(100); // Larghezza dell'immagine del mazzo coperto
		        deckImage.setFitHeight(160); // Altezza dell'immagine del mazzo coperto

		        // Crea una VBox per contenere il titolo e l'immagine del mazzo coperto
		        VBox controlPanel = new VBox(5);
		        controlPanel.setAlignment(Pos.CENTER_LEFT); // Allineamento a sinistra

		        // Aggiungi il titolo "Pannello di Controllo"
		        Label controlLabel = new Label(playerName);
		        controlLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		        controlLabel.setAlignment(Pos.CENTER);
		        controlLabel.setMaxWidth(Double.MAX_VALUE);
		        VBox.setVgrow(controlLabel, Priority.ALWAYS);
		        controlLabel.setTextFill(textGradient);

		        VBox.setMargin(deckImage, new Insets(0, 0, 0, 20));
		        controlPanel.getChildren().addAll(nameLabel, deckImage); // Aggiungi prima nameLabel e poi deckImage

		        playerArea.getChildren().addAll(controlPanel);
		}
			

		return playerArea;
	}


	// Metodo per creare bottoni con stile personalizzato
	private Button createStyledButton(String text, int size) {
		Button button = new Button(text);
		String originalStyle = "-fx-font-size: " + size + "px; -fx-background-color: linear-gradient(to bottom right, #8B4513, #D2691E); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 10 20;";
		button.setStyle(originalStyle);

		// Effetti del passaggio del mouse
		button.setOnMouseEntered(e -> button.setStyle(originalStyle + "-fx-background-color: linear-gradient(to bottom right, #A0522D, #FF8C00);"));
		button.setOnMouseExited(e -> button.setStyle(originalStyle + "-fx-background-color: linear-gradient(to bottom right, #8B4513, #D2691E);"));

		return button;
	}


	public Region setBackground(Stage stage) {
		Image sfondo = new Image(getClass().getResource(Constants.Path.GAME_BACKGROUND).toExternalForm());
		Region contenuto = new Region();

		BackgroundImage sfondoImage = new BackgroundImage(sfondo,
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
		Background sfondoBackground = new Background(sfondoImage);
		contenuto.setBackground(sfondoBackground);

		contenuto.prefWidthProperty().bind(stage.widthProperty());
		contenuto.prefHeightProperty().bind(stage.heightProperty());

		return contenuto;
	}




}
