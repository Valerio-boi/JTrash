package jtrash.view;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import utility.SupportClass;

public class Game {

	private User user;
	private GameController gameController;
	private GridPane gameTable;
	private int numberOfPlayers;
	private Card cardSostituita;
	private boolean needToChangeTurn;
	private boolean carteMazzoDisabilitate = false;
	private ImageView cardDisabled;
	private SupportClass support;

	public void gamePageShow(Stage stage, User user) {
		this.user = user;
		this.gameController = new GameController();
		this.support = new SupportClass();

		StackPane contentPane = new StackPane();
		contentPane.setAlignment(Pos.TOP_CENTER);

		Text welcomeText = support.createStyledText("Benvenuto, " + user.getNickname() + "!", 50);
		Text selectPlayersText = support.createStyledText("Seleziona con quante persone vuoi giocare:", 20);

		DropShadow shadow = new DropShadow();
		shadow.setColor(Color.BLACK);
		support.applyShadow(welcomeText, shadow);
		support.applyShadow(selectPlayersText, shadow);

		ScaleTransition scaleTransition = support.createScaleTransition(welcomeText);

		Button player1Button = support.createStyledButton("Contro 1", 20);
		Button player3Button = support.createStyledButton("Contro 2", 20);
		Button player5Button = support.createStyledButton("Contro 3", 20);

		player1Button.setOnAction(e -> createGameTable(3, stage));
		player3Button.setOnAction(e -> createGameTable(4, stage));
		player5Button.setOnAction(e -> createGameTable(5, stage));

		HBox buttonsBox = support.createHBox(20, player1Button, player3Button, player5Button);

		VBox mainLayout = support.createVBox(20, welcomeText, selectPlayersText, buttonsBox);

		contentPane.getChildren().addAll(new StackPane(support.setBackground(stage, Constants.Path.GAME_BACKGROUND)), mainLayout);

		Scene currentScene = stage.getScene();
		currentScene.setRoot(contentPane);
	}

	public void createGameTable(int numberOfPlayers, Stage stage) {
		this.gameController.creaMazzo(numberOfPlayers - 1);
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
				} else if ((i * cols + j) == numberOfPlayers - 1) {
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

		contentPane.getChildren().addAll(new StackPane(support.setBackground(stage, Constants.Path.GAME_BACKGROUND)), gameTable);


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

		HBox topCards = new HBox(20); // 20 di spazio tra le carte
		topCards.setAlignment(Pos.CENTER);
		if(controllPannel) {
			// Sezione per le carte sopra

			// Aggiungi le carte sopra
			for (int i = 0; i < 5; i++) {
				// Aggiungi l'immagine delle carte sopra
				ImageView cardImageTop = new ImageView(new Image(getClass().getResource(Constants.Path.BACKED_CARD).toExternalForm()));
				// Imposta le dimensioni delle immagini delle carte
				cardImageTop.setFitWidth(100); // Larghezza maggiore
				cardImageTop.setFitHeight(160); // Altezza maggiore

				topCards.getChildren().add(cardImageTop);
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
				ImageView cardImageBottom = new ImageView(new Image(getClass().getResource(Constants.Path.BACKED_CARD).toExternalForm()));
				// Imposta le dimensioni delle immagini delle carte
				cardImageBottom.setFitWidth(100); // Larghezza maggiore
				cardImageBottom.setFitHeight(160); // Altezza maggiore

				bottomCards.getChildren().add(cardImageBottom);
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

			ImageView cartaScartata = new ImageView();
			cartaScartata.setFitWidth(100);
			cartaScartata.setFitHeight(160);

			HBox deckAndReplacement = new HBox(20);
			deckAndReplacement.setAlignment(Pos.CENTER_LEFT);
			deckAndReplacement.getChildren().addAll(deckImage, cartaSostituitaImageView, cartaScartata);


			deckImage.setOnMouseClicked(event -> {
				logicDeck(playerArea, deckAndReplacement, cartaSostituitaImageView, cartaScartata,false);
			});

			cartaSostituitaImageView.setOnMouseClicked(event -> {
				logicDeckScartato(playerArea, deckAndReplacement, cartaSostituitaImageView, cartaScartata, false);

			});

			cartaScartata.setDisable(true);
			cartaScartata.setOpacity(0.5);

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

	private void showChangeTurnMessage(VBox playerArea, HBox deckAndReplacement, ImageView cartaSostituitaImageView, ImageView cartaScartata) {
		if (needToChangeTurn) {
			if((numberOfPlayers - 2) == gameController.getCurrentPlayerIndex()) {
				gameController.setCurrentPlayerIndex(0);
			}else {
				gameController.setCurrentPlayerIndex(gameController.getCurrentPlayerIndex() + 1);
			}
			
			carteMazzoDisabilitate = false;
			String nameTurn = gameController.getCurrentPlayerIndex() == 0 ? user.getNickname() : "Player " + (gameController.getCurrentPlayerIndex() + 1);
			Label changeTurnLabel = new Label("Turno di: " + nameTurn);
			changeTurnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
			changeTurnLabel.setTextFill(Color.WHITE);

			playerArea.getChildren().add(changeTurnLabel);

			if (cardDisabled != null) {
				cardDisabled.setDisable(false);
				cardDisabled.setOpacity(1.0);
			}

			System.out.println("Player in gioco con cambio turno ---> " + gameController.getCurrentPlayerIndex());
			if(gameController.getCurrentPlayerIndex() != 0) {
				cardDisabled.setDisable(true);
				cardDisabled.setOpacity(0.5);
				automateBot(playerArea, deckAndReplacement, cartaSostituitaImageView, cartaScartata);
			}
		}
	}


	private void showTrashMessage(VBox playerArea) {
		String nameTurn = gameController.getCurrentPlayerIndex() == 0 ? user.getNickname() : "Player " + (gameController.getCurrentPlayerIndex() + 1);
		Label changeTurnLabel = new Label("TRASH: " + nameTurn);
		changeTurnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		changeTurnLabel.setTextFill(Color.WHITE);

		// Aggiungi il messaggio al pannello di controllo
		playerArea.getChildren().add(changeTurnLabel);

			
	}

	// Metodo per sostituire la carta sopra del giocatore
	private void replaceCardAbove(int cardIndex, Image newCardImage, VBox playerArea) {
		if (playerArea.getChildren().size() >= 2 && playerArea.getChildren().get(1) instanceof HBox) {
			HBox cardsAbove = (HBox) playerArea.getChildren().get(1); // Sezione delle carte sopra

			if (cardIndex >= 0 && cardIndex < 5) {
				Node cardNodeToReplace = cardsAbove.getChildren().get(cardIndex);
				cardNodeToReplace.setDisable(true);
				if (cardNodeToReplace instanceof ImageView) {
					ImageView cardToReplace = (ImageView) cardNodeToReplace;
					cardToReplace.setImage(newCardImage);
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
				cardNodeToReplace.setDisable(true);
				if (cardNodeToReplace instanceof ImageView) {
					ImageView cardToReplace = (ImageView) cardNodeToReplace;
					cardToReplace.setImage(newCardImage);
				}
			}

		}
	}


	private void showSelectList(VBox playerArea, Card card, ImageView cartaSostituita) {

		Label selectNumberLabel = new Label("Seleziona un numero da 1 a 10:");
		selectNumberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		selectNumberLabel.setTextFill(Color.WHITE);

		// Creazione della Select List con opzioni da 1 a 10
		ComboBox<Integer> selectList = new ComboBox<>();
		for (int i = 1; i <= 10; i++) {
			selectList.getItems().add(i);
		}

		// Stile per la Select List
		selectList.setStyle("-fx-font-size: 16px;  -fx-text-fill: white;");

		// Aggiungi spazio dal bordo sinistro
		VBox.setMargin(selectList, new Insets(0, 0, 0, 15));

		cartaSostituita.setDisable(true);
		cartaSostituita.setOpacity(0.5);

		// Aggiungi un gestore degli eventi per gestire la selezione dalla Select List
		selectList.setOnAction(e -> handleSelectListSelection(selectList.getValue(), playerArea, card, cartaSostituita));

		// Aggiungi etichetta e Select List al layout del giocatore
		playerArea.getChildren().addAll(selectNumberLabel, selectList);
	}

	private void handleSelectListSelection(int selectedNumber, VBox playerArea, Card card, ImageView cartaSostituita) {
		// Gestisci la selezione dalla Select List
		System.out.println("Numero selezionato: " + selectedNumber);

		VBox area = (VBox) gameTable.getChildren().get(gameController.getCurrentPlayerIndex());
		ImageView cartaPescataImageView = new ImageView(new Image(getClass().getResource(Constants.Path.CARD + card.getNameCard()).toExternalForm()));
		if (selectedNumber <= 5) { // Se il numero è tra 1 e 5, sostituisci la carta sopra
			replaceCardAbove(selectedNumber - 1, cartaPescataImageView.getImage(), area);
		} else { // Altrimenti, sostituisci la carta sotto
			replaceCardBelow(selectedNumber - 6, cartaPescataImageView.getImage(), area);
		}

		cardSostituita = gameController.sostituisciCarta(card, selectedNumber);
		cartaSostituita.setImage((new Image(getClass().getResource(Constants.Path.CARD +  cardSostituita.getNameCard()).toExternalForm())));
		// Puoi eseguire ulteriori azioni qui, se necessario
		ImageView cartaSostituitaImageView = new ImageView();
		if (gameController.posizioneCarta(cardSostituita) == 14 || gameController.posizioneCarta(cardSostituita)==12 && gameController.getCurrentPlayerIndex() == 0) {
			// Mostra la Select List per scegliere un numero da 1 a 10
			cartaSostituitaImageView.setImage(new Image(getClass().getResource(Constants.Path.CARD + cardSostituita.getNameCard()).toExternalForm()));
			showSelectList(playerArea, cardSostituita, cartaSostituitaImageView);
		}else if(gameController.posizioneCarta(cardSostituita) == 14 || gameController.posizioneCarta(cardSostituita)==12) {
			int firstBackedCard = gameController.getFirstBacked(area) + 1;
			handleSelectListSelection(firstBackedCard,  playerArea,  cardSostituita,  cartaSostituita);
		}

		cartaSostituita.setDisable(false);
		cartaSostituita.setOpacity(1.0);

		if(gameController.checkIsTrash(area)) {
			System.out.println("Trash");
			showTrashMessage(playerArea);
		}

		// Rimuovi la Select List dal layout del giocatore
		playerArea.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().startsWith("Seleziona un numero"));
		if(gameController.getCurrentPlayerIndex() == 0) {
			playerArea.getChildren().remove(1);
		}
			
	}

	
	public void automateBot(VBox playerArea, HBox deckAndReplacement, ImageView cartaSostituitaImageView, ImageView cartaScartata) {
		logicDeck(playerArea, deckAndReplacement, cartaSostituitaImageView, cartaScartata, true);
		logicDeckScartato(playerArea, deckAndReplacement, cartaSostituitaImageView, cartaScartata, true);
	}


	private void logicDeck(VBox playerArea, HBox deckAndReplacement, ImageView cartaSostituitaImageView, ImageView cartaScartata, boolean automate) {
		playerArea.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().contains("Turno di: "));
		// Trova l'indice del giocatore nel GridPane (es. il primo giocatore)
		int playerIndex = gameController.getCurrentPlayerIndex();
		cardDisabled = (ImageView) deckAndReplacement.getChildren().get(0);
		cardDisabled.setDisable(true);
		cardDisabled.setOpacity(0.5); // Imposta l'opacità a metà per indicare che è disabilitata

		if (!carteMazzoDisabilitate) {

			Card carta = gameController.pescaCarta(); // Pesca una carta dal mazzo
			int cardIndexToReplace = gameController.posizioneCarta(carta);

			// Ottenere il pannello del giocatore corrispondente all'indice trovato
			VBox area = (VBox) gameTable.getChildren().get(playerIndex);
			boolean isCoperta = false;
			System.out.println("MA COME STA MESSA ---> " + carta.toString());

			if (cardIndexToReplace <= 5) { // Se il numero è tra 1 e 5, sostituisci la carta sopra
				isCoperta = gameController.checkCartaCoperta(cardIndexToReplace - 1,  area, true);
			} else if(cardIndexToReplace <= 10){ // Altrimenti, sostituisci la carta sotto
				isCoperta = gameController.checkCartaCoperta(cardIndexToReplace - 6, area, false);
			}
			
			System.out.println("Dimme che sta coperta -> " + isCoperta);

			if (((cardIndexToReplace >= 1 && cardIndexToReplace <= 10)) && !gameController.checkExistCard(carta, !isCoperta)) {
				// Ottieni l'immagine corrispondente alla carta pescata dal mazzo
				ImageView cartaPescataImageView = new ImageView(new Image(getClass().getResource(Constants.Path.CARD + carta.getNameCard()).toExternalForm()));
				cartaPescataImageView.setFitWidth(100);
				cartaPescataImageView.setFitHeight(160);


				if (cardIndexToReplace <= 5) { // Se il numero è tra 1 e 5, sostituisci la carta sopra
					replaceCardAbove(cardIndexToReplace - 1, cartaPescataImageView.getImage(), area);
				} else { // Altrimenti, sostituisci la carta sotto
					replaceCardBelow(cardIndexToReplace - 6, cartaPescataImageView.getImage(), area);
				}

				cardSostituita = gameController.sostituisciCarta(carta, cardIndexToReplace);

				// Aggiungi l'immagine della carta sostituita nel pannello di controllo accanto al mazzo
				if(cardSostituita != null) {
					if (gameController.posizioneCarta(cardSostituita)==14 || gameController.posizioneCarta(cardSostituita)==12) {
						if(automate) {
							int firstBackedCard = gameController.getFirstBacked(area) + 1;
							handleSelectListSelection(firstBackedCard, playerArea, cardSostituita, cartaSostituitaImageView);
						}else {
							showSelectList(playerArea, cardSostituita, cartaSostituitaImageView);
						}
					}
					cartaSostituitaImageView.setImage((new Image(getClass().getResource(Constants.Path.CARD +  cardSostituita.getNameCard()).toExternalForm())));
				}else if(gameController.posizioneCarta(carta)==14 || gameController.posizioneCarta(carta)==12) {
					cartaSostituitaImageView.setImage((new Image(getClass().getResource(Constants.Path.CARD +  carta.getNameCard()).toExternalForm())));
				}
				carteMazzoDisabilitate = true;
				needToChangeTurn = false; // Imposta a false quando viene cliccato il mazzo
			}else {
				if (gameController.posizioneCarta(carta)==14 || gameController.posizioneCarta(carta)==12) {
					
					if(automate) {
						int firstBackedCard = gameController.getFirstBacked(area) + 1;
						handleSelectListSelection(firstBackedCard, playerArea, carta, cartaSostituitaImageView);
					}else {
						showSelectList(playerArea, carta, cartaSostituitaImageView);
					}
					// Mostra la Select List per scegliere un numero da 1 a 10
					cartaSostituitaImageView.setImage((new Image(getClass().getResource(Constants.Path.CARD +  carta.getNameCard()).toExternalForm())));
					cartaSostituitaImageView.setDisable(true);
					cartaSostituitaImageView.setOpacity(0.5);
				}else {
					//aggiungi carta nel mazzo scartato
					needToChangeTurn = true;
					cartaSostituitaImageView.setImage(null);
					cartaScartata.setImage((new Image(getClass().getResource(Constants.Path.CARD +  carta.getNameCard()).toExternalForm())));
					showChangeTurnMessage(playerArea, deckAndReplacement, cartaSostituitaImageView, cartaScartata);
				}
			}

			if(gameController.checkIsTrash(area)) {
				System.out.println("Trash");
				showTrashMessage(playerArea);
			}
			
		}
	}
	
	public void logicDeckScartato(VBox playerArea, HBox deckAndReplacement, ImageView cartaSostituitaImageView, ImageView cartaScartata, boolean automate) {
		// Trova l'indice del giocatore nel GridPane (es. il primo giocatore)
		int playerIndex = gameController.getCurrentPlayerIndex();
		Card carta = cardSostituita;
		int cardIndexToReplace = gameController.posizioneCarta(carta); // Questo è un esempio, potresti ottenere il numero da qualche altra parte
		VBox area = (VBox) gameTable.getChildren().get(playerIndex);
		boolean isCoperta = false;
		if (cardIndexToReplace <= 5) { // Se il numero è tra 1 e 5, sostituisci la carta sopra
			isCoperta = gameController.checkCartaCoperta(cardIndexToReplace - 1,  area, true);
		} else if(cardIndexToReplace <= 10) { // Altrimenti, sostituisci la carta sotto
			isCoperta = gameController.checkCartaCoperta(cardIndexToReplace - 6, area, false);
		}

		if ((cardIndexToReplace >= 1 && cardIndexToReplace <= 10) && !gameController.checkExistCard(carta, !isCoperta)) {

			needToChangeTurn = false; // Imposta a false quando viene cliccato il mazzo

			// Ottieni l'immagine corrispondente alla carta pescata dal mazzo
			ImageView cartaPescataImageView = new ImageView(new Image(getClass().getResource(Constants.Path.CARD + carta.getNameCard()).toExternalForm()));
			cartaPescataImageView.setFitWidth(100);
			cartaPescataImageView.setFitHeight(160);

			// Ottenere il pannello del giocatore corrispondente all'indice trovato
			if (cardIndexToReplace <= 5) { // Se il numero è tra 1 e 5, sostituisci la carta sopra
				replaceCardAbove(cardIndexToReplace - 1, cartaPescataImageView.getImage(), area);
			} else { // Altrimenti, sostituisci la carta sotto
				replaceCardBelow(cardIndexToReplace - 6, cartaPescataImageView.getImage(), area);
			}
			playerArea.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().contains("Turno di: "));
		}else {
			needToChangeTurn = true;
			// Pulisci l'immagine della carta sostituita nel pannello di controllo
			cartaScartata.setImage((new Image(getClass().getResource(Constants.Path.CARD +  carta.getNameCard()).toExternalForm())));
			cartaSostituitaImageView.setImage(null);
			showChangeTurnMessage(playerArea, deckAndReplacement, cartaSostituitaImageView, cartaScartata);
			return ;
		}

		cardSostituita = gameController.sostituisciCarta(carta, cardIndexToReplace);
		// Aggiungi l'immagine della carta sostituita nel pannello di controllo accanto al mazzo
		if(cardSostituita != null && !needToChangeTurn) {
			cartaSostituitaImageView.setImage((new Image(getClass().getResource(Constants.Path.CARD +  cardSostituita.getNameCard()).toExternalForm())));
			if (gameController.posizioneCarta(cardSostituita) == 14 || gameController.posizioneCarta(cardSostituita)==12) {
				// Mostra la Select List per scegliere un numero da 1 a 10
				if(automate) {
					int firstBackedCard = gameController.getFirstBacked(area) + 1;
					handleSelectListSelection(firstBackedCard, playerArea, cardSostituita, cartaSostituitaImageView);
				}else {
					showSelectList(playerArea, cardSostituita, cartaSostituitaImageView);
				}
			}
		}

		if(gameController.checkIsTrash(area)) {
			System.out.println("Trash");
			showTrashMessage(playerArea);
			return ;
		}
		
		if(automate && cardSostituita != null) {
			logicDeckScartato(playerArea, deckAndReplacement, cartaSostituitaImageView, cartaScartata, automate);
		}
	}
}
