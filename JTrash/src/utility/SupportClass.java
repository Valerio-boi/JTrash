package utility;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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

public class SupportClass {
	
	
	 public Text createStyledText(String text, int fontSize) {
	        Text styledText = new Text(text);
	        styledText.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
	        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
	                new Stop(0, Color.DARKORANGE), new Stop(1, Color.GOLD));
	        styledText.setFill(gradient);
	        return styledText;
	    }

	    public Button createStyledButton(String text, int fontSize) {
	        Button button = new Button(text);
	        String originalStyle = "-fx-font-size: " + fontSize + "px; -fx-background-color: linear-gradient(to bottom right, #8B4513, #D2691E); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 10 20;";
	        button.setStyle(originalStyle);
	        button.setOnMouseEntered(e -> button.setStyle(originalStyle + "-fx-background-color: linear-gradient(to bottom right, #A0522D, #FF8C00);"));
	        button.setOnMouseExited(e -> button.setStyle(originalStyle + "-fx-background-color: linear-gradient(to bottom right, #8B4513, #D2691E);"));
	        return button;
	    }

	    public HBox createHBox(double spacing, Node... children) {
	        HBox hbox = new HBox(spacing);
	        hbox.setAlignment(Pos.CENTER);
	        hbox.getChildren().addAll(children);
	        return hbox;
	    }

	    public VBox createVBox(double spacing, Node... children) {
	        VBox vbox = new VBox(spacing);
	        vbox.setAlignment(Pos.CENTER);
	        vbox.getChildren().addAll(children);
	        return vbox;
	    }

	    public ScaleTransition createScaleTransition(Text text) {
	        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), text);
	        scaleTransition.setToX(1.5);
	        scaleTransition.setToY(1.5);
	        scaleTransition.setAutoReverse(true);
	        scaleTransition.setCycleCount(Timeline.INDEFINITE);
	        scaleTransition.play();
	        return scaleTransition;
	    }

	    public void applyShadow(Text text, DropShadow shadow) {
	        text.setEffect(shadow);
	    }

		public Region setBackground(Stage stage, String backGround) {
			Image sfondo = new Image(getClass().getResource(backGround).toExternalForm());
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
