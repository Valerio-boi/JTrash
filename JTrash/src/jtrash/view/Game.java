package jtrash.view;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
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

	private GridPane playersGrid;

	public void gamePageShow(Stage stage, User user) {
		StackPane contenuto = new StackPane();
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

		contro1Button.setOnAction(e -> {});
		contro3Button.setOnAction(e -> {});
		contro5Button.setOnAction(e -> {});


		

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
