package jtrash.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jtrash.model.User;
import utility.Constants;

public class Game {
	
	
	public void gamePageShow(Stage stage, User user) {
		VBox nuovoContenuto = new VBox();
		Scene sceneCorrente = stage.getScene();
		sceneCorrente.setRoot(nuovoContenuto);
		System.out.println(user.toString());

		stage.setScene(new Scene(new StackPane(setBackground(stage))));
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
