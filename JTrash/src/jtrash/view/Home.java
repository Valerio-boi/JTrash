package jtrash.view;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.Constants;

public class Home{

	private Stage stage;

	public void home(Stage firstStage) {
		
		this.stage = firstStage;
		Text title = new Text(Constants.Title.GAME_TITLE);
       
		firstStage.setWidth(Screen.getPrimary().getBounds().getWidth());
		firstStage.setHeight(Screen.getPrimary().getBounds().getHeight());
		firstStage.setResizable(true);
		firstStage.setTitle(Constants.Title.INITIAL_TITLE);
		firstStage.setScene(new Scene(new StackPane(setBackground(firstStage), createBottomTitle(title))));
		firstStage.getScene().getStylesheets().add(getClass().getResource(Constants.Path.CSS).toExternalForm());
		firstStage.show();
		animationTitle(title);

	}


	private VBox homeButton() {

		Button gioca = new Button(Constants.Label.GIOCA);
		Button opzioni = new Button(Constants.Label.RISOLUZIONE);
		Button tornaDesktop = new Button(Constants.Label.DESKTOP);
		
		UserPage userPage = new UserPage();
		gioca.setOnAction(e -> userPage.userPageShow(stage));
		
		Resolotuion resolotuion = new Resolotuion();
		opzioni.setOnAction(e -> resolotuion.mostraDialogoOpzioni(stage));
		
		Modal modal = new Modal();
		tornaDesktop.setOnAction(e -> modal.mostraDialogoConferma());

		VBox bottoniLayout = new VBox(20);
		bottoniLayout.setAlignment(Pos.CENTER);
		bottoniLayout.getChildren().addAll(gioca, opzioni, tornaDesktop);
		
		return bottoniLayout;

	}


	public Region setBackground(Stage stage) {

		Image sfondo = new Image(getClass().getResource(Constants.Path.BACKGROUND).toExternalForm());
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
	
	private BorderPane createBottomTitle(Text title) {
	    title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
	    title.setStyle(Constants.Css.CLASSIC_CSS);

	    Text creatorInfo = new Text(Constants.Label.CREATORE);
	    creatorInfo.setFont(Font.font(Constants.Font.ARIAL_FONT, 35)); 
	    creatorInfo.setStyle(Constants.Css.CLASSIC_CSS);
	    
	    VBox button = homeButton();

	    BorderPane root = new BorderPane();
	    root.setTop(title);
	    BorderPane.setAlignment(creatorInfo, Pos.BOTTOM_LEFT);
	    root.setBottom(creatorInfo);
	    BorderPane.setAlignment(title, Pos.CENTER);
	    root.setCenter(button);
	    
	    return root;
	}
	
	private void animationTitle(Text title) {
	      TranslateTransition transition = new TranslateTransition(Duration.seconds(2), title);
	      transition.setFromX(Screen.getPrimary().getBounds().getWidth());
	      transition.setToX(0);
	      transition.play();
	}


	

}
