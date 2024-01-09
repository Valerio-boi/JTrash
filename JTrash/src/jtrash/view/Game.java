package jtrash.view;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jtrash.controller.GameController;
import jtrash.model.Card;
import jtrash.model.User;
import utility.Constants;

public class Game {
	private User user;
	private GameController gameController;
	private GridPane gameTable;
	private int numberOfPlayers;
	private Card cardSostituita;
	private boolean needToChangeTurn;

	public void gamePageShow(Stage stage, User user) {
		this.user = user;
		this.gameController = new GameController();
		this.gameController.creaMazzo();

		StackPane contentPane = new StackPane();
		contentPane.setAlignment(Pos.TOP_CENTER);

		// Testo di benvenuto con effetto e animazione
		Text welcomeText = new Text("Benvenuto, " + user.getNickname() + "!");
		welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
		LinearGradient welcomeGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
				new Stop(0, Color.DARKORANGE), new Stop(1, Color.GOLD));
		welcomeText.setFill(welcomeGradient);

		// Testo per la selezione del numero di giocatori
		Text selectPlayersText = new Text("Seleziona con quante persone vuoi giocare:");
		selectPlayersText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		LinearGradient selectPlayersGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
				new Stop(0, Color.DARKORANGE), new Stop(1, Color.GOLD));
		selectPlayersText.setFill(selectPlayersGradient);

		// Effetto ombra per i testi
		DropShadow shadow = new DropShadow();
		shadow.setColor(Color.BLACK);
		welcomeText.setEffect(shadow);
		selectPlayersText.setEffect(shadow);

		// Animazione di scaling per il testo di benvenuto
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), welcomeText);
		scaleTransition.setToX(1.5);
		scaleTransition.setToY(1.5);
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(Timeline.INDEFINITE);
		scaleTransition.play();

		// Pulsanti per la scelta del numero di giocatori
		Button player1Button = createStyledButton("Contro 1", 20);
		Button player3Button = createStyledButton("Contro 2", 20);
		Button player5Button = createStyledButton("Contro 4", 20);

		player1Button.setOnAction(e -> createGameTable(3, stage));
		player3Button.setOnAction(e -> createGameTable(4, stage));
		player5Button.setOnAction(e -> createGameTable(6, stage));

		// Layout per i pulsanti
		HBox buttonsBox = new HBox(20);
		buttonsBox.setAlignment(Pos.CENTER);
		buttonsBox.getChildren().addAll(player1Button, player3Button, player5Button);

		// Layout principale
		VBox mainLayout = new VBox(20);
		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.getChildren().addAll(welcomeText, selectPlayersText, buttonsBox);

		contentPane.getChildren().addAll(new StackPane(setBackground(stage)), mainLayout);

		Scene currentScene = stage.getScene();
		currentScene.setRoot(contentPane);
	}

	public void createGameTable(int numberOfPlayers, Stage stage) {
		this.gameController.assegnaMazzo(numberOfPlayers);
		this.numberOfPlayers = numberOfPlayers;
		StackPane contentPane = new StackPane();
		contentPane.setAlignment(Pos.TOP_CENTER);

		gameTable = new GridPane();
		gameTable.setAlignment(Pos.CENTER);

		int rows = numberOfPlayers / 2 + numberOfPlayers % 2;
		int cols = 2;

		// Vincoli di layout per far si che le celle si adattino alla grandezza della finestra
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
				boolean isControlPanel = true;

				if (i == 0 && j == 0) {
					playerName = user.getNickname();
				} else if ((i + 1) == rows && (j + 1) == cols) {
					playerName = "Pannel control";
					isControlPanel = false;
				} else {
					playerName = "Player " + (i * cols + j + 1);
				}

				VBox playerArea = createPlayerArea(playerName, isControlPanel,
						gameController.listaGiocatori.get(i * cols + j < gameController.listaGiocatori.size() ? i * cols + j : 0));

				if (i * cols + j < numberOfPlayers) {
					gameTable.add(playerArea, j, i);
				} else if (numberOfPlayers % 2 == 1 && j == 0) {
					gameTable.add(new Region(), 1, i); // Aggiungi una regione vuota nella colonna 2 per centrare
				}
			}
		}

		contentPane.getChildren().addAll(new StackPane(setBackground(stage)), gameTable);


		Scene currentScene = stage.getScene();
		currentScene.setRoot(contentPane);
	}

	private VBox createPlayerArea(String playerName, boolean controllPannel, User player) {
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
				ImageView cardImage = new ImageView(new Image(getClass().getResource(Constants.Path.BACKED_CARD).toExternalForm()));
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
			for (int i = 5; i < 10; i++) {
				// Aggiungi l'immagine delle carte sotto
				ImageView cardImage = new ImageView(new Image(getClass().getResource(Constants.Path.BACKED_CARD).toExternalForm()));
				// Imposta le dimensioni delle immagini delle carte
				cardImage.setFitWidth(100); // Larghezza maggiore
				cardImage.setFitHeight(160); // Altezza maggiore
				bottomCards.getChildren().add(cardImage);
			}
			playerArea.getChildren().addAll(nameLabel, topCards, space, bottomCards);
		}else {
			ImageView deckImage = new ImageView(new Image(getClass().getResource(Constants.Path.BACKED_CARD).toExternalForm()));
			deckImage.setFitWidth(100); 
			deckImage.setFitHeight(160); 

			HBox deckAndDrawnCard = new HBox(20);
			deckAndDrawnCard.setAlignment(Pos.CENTER_LEFT); 

			deckAndDrawnCard.getChildren().addAll(deckImage);

			ImageView cartaSostituitaImageView = new ImageView();
			cartaSostituitaImageView.setFitWidth(100);
			cartaSostituitaImageView.setFitHeight(160);

			HBox deckAndReplacement = new HBox(20);
			deckAndReplacement.setAlignment(Pos.CENTER_LEFT);
			deckAndReplacement.getChildren().addAll(deckImage, cartaSostituitaImageView);


			deckImage.setOnMouseClicked(event -> {
				Card carta = gameController.pescaCarta(); // Pesca una carta dal mazzo
				int cardIndexToReplace = gameController.posizioneCarta(carta); // Questo è un esempio, potresti ottenere il numero da qualche altra parte
				if (cardIndexToReplace >= 1 && cardIndexToReplace <= 10) {


					// Ottieni l'immagine corrispondente alla carta pescata dal mazzo
					ImageView cartaPescataImageView = new ImageView(new Image(getClass().getResource(Constants.Path.CARD + carta.getNameCard()).toExternalForm()));
					cartaPescataImageView.setFitWidth(100);
					cartaPescataImageView.setFitHeight(160);

					// Trova l'indice del giocatore nel GridPane (es. il primo giocatore)
					int playerIndex = 2;

					// Ottenere il pannello del giocatore corrispondente all'indice trovato
					VBox area = (VBox) gameTable.getChildren().get(playerIndex);

					if (cardIndexToReplace <= 5) { // Se il numero è tra 1 e 5, sostituisci la carta sopra
						replaceCardAbove(cardIndexToReplace - 1, cartaPescataImageView.getImage(), area);
					} else { // Altrimenti, sostituisci la carta sotto
						replaceCardBelow(cardIndexToReplace - 6, cartaPescataImageView.getImage(), area);
					}
				}else {
					needToChangeTurn = true;
					// Pulisci l'immagine della carta sostituita nel pannello di controllo
					cartaSostituitaImageView.setImage(null);
					showChangeTurnMessage(playerArea);
				}
				cardSostituita = gameController.sostituisciCarta(carta, cardIndexToReplace);
				// Aggiungi l'immagine della carta sostituita nel pannello di controllo accanto al mazzo
				cartaSostituitaImageView.setImage((new Image(getClass().getResource(Constants.Path.CARD +  cardSostituita.getNameCard()).toExternalForm())));
				needToChangeTurn = false; // Imposta a false quando viene cliccato il mazzo
				playerArea.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Cambio turno"));
			});

			cartaSostituitaImageView.setOnMouseClicked(event -> {
				Card carta = cardSostituita; // Pesca una carta dal mazzo
				int cardIndexToReplace = gameController.posizioneCarta(carta); // Questo è un esempio, potresti ottenere il numero da qualche altra parte
				if (cardIndexToReplace >= 1 && cardIndexToReplace <= 10) {

					// Ottieni l'immagine corrispondente alla carta pescata dal mazzo
					ImageView cartaPescataImageView = new ImageView(new Image(getClass().getResource(Constants.Path.CARD + carta.getNameCard()).toExternalForm()));
					cartaPescataImageView.setFitWidth(100);
					cartaPescataImageView.setFitHeight(160);

					// Trova l'indice del giocatore nel GridPane (es. il primo giocatore)
					int playerIndex = 2;

					// Ottenere il pannello del giocatore corrispondente all'indice trovato
					VBox area = (VBox) gameTable.getChildren().get(playerIndex);

					if (cardIndexToReplace <= 5) { // Se il numero è tra 1 e 5, sostituisci la carta sopra
						replaceCardAbove(cardIndexToReplace - 1, cartaPescataImageView.getImage(), area);
					} else { // Altrimenti, sostituisci la carta sotto
						replaceCardBelow(cardIndexToReplace - 6, cartaPescataImageView.getImage(), area);
					}
					needToChangeTurn = false; // Imposta a false quando viene cliccato il mazzo
					playerArea.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Cambio turno"));
				}else {
					needToChangeTurn = true;
					// Pulisci l'immagine della carta sostituita nel pannello di controllo
					cartaSostituitaImageView.setImage(null);
					showChangeTurnMessage(playerArea);
				}

				cardSostituita = gameController.sostituisciCarta(carta, cardIndexToReplace);
				// Aggiungi l'immagine della carta sostituita nel pannello di controllo accanto al mazzo
				cartaSostituitaImageView.setImage((new Image(getClass().getResource(Constants.Path.CARD +  cardSostituita.getNameCard()).toExternalForm())));
			});

			VBox controlPanel = new VBox(5);
			controlPanel.setAlignment(Pos.CENTER_LEFT);

			Label controlLabel = new Label(playerName);
			controlLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
			controlLabel.setAlignment(Pos.CENTER);
			controlLabel.setMaxWidth(Double.MAX_VALUE);
			VBox.setVgrow(controlLabel, Priority.ALWAYS);
			VBox.setMargin(controlPanel, new Insets(0, 0, 0, 20));
			controlLabel.setTextFill(textGradient);

			controlPanel.getChildren().addAll(nameLabel, deckAndReplacement);

			playerArea.getChildren().addAll(controlPanel);
		}			

		return playerArea;
	}

	public void showChangeTurnMessage(VBox playerArea) {
		if (needToChangeTurn) {
			Label changeTurnLabel = new Label("Cambio turno");
			changeTurnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
			changeTurnLabel.setTextFill(Color.WHITE);

			// Rimuovi eventuali messaggi precedenti prima di aggiungere il nuovo messaggio
			playerArea.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Cambio turno"));

			// Aggiungi il messaggio al pannello di controllo
			playerArea.getChildren().add(changeTurnLabel);
		}
	}

	// Metodo per sostituire la carta sopra del giocatore
	private void replaceCardAbove(int cardIndex, Image newCardImage, VBox playerArea) {
		if (playerArea.getChildren().size() >= 2 && playerArea.getChildren().get(1) instanceof HBox) {
			HBox cardsAbove = (HBox) playerArea.getChildren().get(1); // Sezione delle carte sopra

			if (cardIndex >= 0 && cardIndex < 5) {
				Node cardNodeToReplace = cardsAbove.getChildren().get(cardIndex);
				if (cardNodeToReplace instanceof ImageView) {
					ImageView cardToReplace = (ImageView) cardNodeToReplace;
					cardToReplace.setImage(newCardImage);

					// Esegui altre operazioni qui, se necessario
				}
			}
		}
	}

	// Metodo per sostituire la carta sotto del giocatore
	private void replaceCardBelow(int cardIndex, Image newCardImage, VBox playerArea) {
		if (playerArea.getChildren().size() >= 4 && playerArea.getChildren().get(3) instanceof HBox) {
			HBox cardsBelow = (HBox) playerArea.getChildren().get(3); // Sezione delle carte sotto

			if (cardIndex >= 0 && cardIndex < 5) {
				Node cardNodeToReplace = cardsBelow.getChildren().get(cardIndex);
				if (cardNodeToReplace instanceof ImageView) {
					ImageView cardToReplace = (ImageView) cardNodeToReplace;
					cardToReplace.setImage(newCardImage);

					// Esegui altre operazioni qui, se necessario
				}
			}
		}
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
