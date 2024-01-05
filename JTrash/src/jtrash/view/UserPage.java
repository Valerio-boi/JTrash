package jtrash.view;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import jtrash.controller.UserController;
import jtrash.model.User;
import utility.Constants;

public class UserPage {

	private UserController userController;
	private TextField nomeField;
	private TextField cognomeField;
	private TextField nicknameField;
	private DatePicker dataDiNascitaPicker;

	public void userPageShow(Stage stage) {
		VBox nuovoContenuto = new VBox();
		Scene sceneCorrente = stage.getScene();
		sceneCorrente.setRoot(nuovoContenuto);

		Home home = new Home();
		home.setBackground(stage);
		stage.setScene(new Scene(new StackPane(home.setBackground(stage),createContent())));
	}


	private GridPane createRegistrazionForm() {
		// Creazione del form a sinistra
		GridPane formPane = new GridPane();
		formPane.setHgap(60);
		formPane.setVgap(20);
		formPane.setPadding(new Insets(20));

		//	    VBox vboxRegistrazione = new VBox();
		//	    vboxRegistrazione.setAlignment(Pos.CENTER);

		Label titleRegistrazione = new Label(Constants.Label.REGISTRA);
		titleRegistrazione.setFont(Font.font(Constants.Font.ARIAL_FONT, FontWeight.BOLD, 30));
		titleRegistrazione.setStyle(Constants.Css.BOLD_CLASSIC);
		formPane.add(titleRegistrazione, 0, 0, 2, 1); 


		Label nomeLabel = new Label(Constants.Label.NOME);
		nomeLabel.setStyle(Constants.Css.BOLD_CLASSIC);
		nomeField = new TextField();
		nomeField.setStyle(Constants.Css.STYLE_FIELD);

		Label cognomeLabel = new Label(Constants.Label.COGNOME);
		cognomeLabel.setStyle(Constants.Css.BOLD_CLASSIC);
		cognomeField = new TextField();
		cognomeField.setStyle(Constants.Css.STYLE_FIELD);

		Label dataDiNascitaLabel = new Label(Constants.Label.DATA_NASCITA);
		dataDiNascitaLabel.setStyle(Constants.Css.BOLD_CLASSIC);
		dataDiNascitaPicker = new DatePicker();
		
		Label nicknameLabel = new Label(Constants.Label.NICKNAME);
		nicknameLabel.setStyle(Constants.Css.BOLD_CLASSIC);
		nicknameField = new TextField();
		nicknameField.setStyle(Constants.Css.STYLE_FIELD);
		HBox avatarSection = new HBox(15); // Imposta lo spazio tra gli avatar

		//Setto lo user per la logica del controller 
		User user = new User(nomeField.getText(), cognomeField.getText(), nicknameField.getText(), dataDiNascitaPicker.getValue());

		
		
		// Creazione degli avatar come esempio (immagini fittizie)
		ImageView avatar1 = new ImageView(new Image(getClass().getResource(Constants.Path.DONNA).toExternalForm()));
		avatar1.setFitWidth(50);
		avatar1.setFitHeight(50);
		ImageView avatar2 = new ImageView(new Image(getClass().getResource(Constants.Path.OCCHIALETTO).toExternalForm()));
		avatar2.setFitWidth(50);
		avatar2.setFitHeight(50);
		ImageView avatar3 = new ImageView(new Image(getClass().getResource(Constants.Path.CAPELLONE).toExternalForm()));
		avatar3.setFitWidth(50);
		avatar3.setFitHeight(50);

		// Aggiunta degli avatar all'HBox
		avatarSection.getChildren().addAll(avatar1, avatar2, avatar3);
		
		HBox buttonSection = new HBox(15);
		
		this.userController = new UserController();
		
		Button registraButton = new Button(Constants.Label.BUTTON_REGISTRA);
		registraButton.setStyle(Constants.Css.CLASSIC_CSS_TEXT);
		registraButton.setOnAction(e -> registra()); // Aggiungi l'handler appropriato per il bottone "Registra"

		Button tornaHomeButton = new Button(Constants.Label.HOME);
		tornaHomeButton.setStyle(Constants.Css.CLASSIC_CSS_TEXT);
		tornaHomeButton.setOnAction(e -> userController.handleTornaHome()); // Aggiungi l'handler appropriato per il bottone "Torna alla Home"

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
	
	private VBox createContent() {
	    VBox content = new VBox();
	    content.setSpacing(20);
	    content.setPadding(new Insets(20));

	    GridPane registrazionePane = createRegistrazionForm(); // Form di registrazione

	    // Creazione delle due sezioni vuote "Selezione Giocatore" e "Classifica"
	    GridPane sezioneGiocatorePane = createEmptySection("Selezione Giocatore");
	    GridPane classificaPane = createEmptySection("Classifica");
	    TableView<String> table = createTable();

	    // Aggiungi le tre sezioni affiancate (registrazione a sinistra, sezione giocatore e classifica a destra)
	    GridPane layoutPane = new GridPane();
	    layoutPane.setHgap(20);
	    layoutPane.add(registrazionePane, 0, 0);
	    layoutPane.add(sezioneGiocatorePane, 1, 0);
	    layoutPane.add(classificaPane, 2, 0);
	    
	    spazivuoti(layoutPane);
	    layoutPane.add(table, 0, 10, 2, 1); 
	    content.getChildren().add(layoutPane);

	    return content;
	}
	
	private GridPane createEmptySection(String sectionTitle) {
	    GridPane sectionPane = new GridPane();
	    sectionPane.setPadding(new Insets(20));
//	    sectionPane.setStyle("-fx-border-color: white; -fx-border-width: 1px;"); // Aggiungi una bordatura per visualizzare l'area

	    Label titleLabel = new Label(sectionTitle);
	    titleLabel.setFont(Font.font(Constants.Font.ARIAL_FONT, FontWeight.BOLD, 30));
	    titleLabel.setStyle(Constants.Css.BOLD_CLASSIC);
	    

	    // Aggiungi altri componenti o layout a questa sezione

	    sectionPane.add(titleLabel, 0, 0);

	    return sectionPane;
	}
	
	
	private TableView createTable() {
		
		TableView<User> tableView = new TableView<>();
		ObservableList<User> userList = FXCollections.observableArrayList();
		
		tableView.setFixedCellSize(30); // Imposta l'altezza fissa per ogni riga
	    tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(6.1));

		TableColumn<User, String> nomeColumn = new TableColumn<>("Nome");
		nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		adaptColumnTable(nomeColumn);
		
		TableColumn<User, String> cognomeColumn = new TableColumn<>("Cognome");
		cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		adaptColumnTable(cognomeColumn);

		TableColumn<User, LocalDate> dataNascitaColumn = new TableColumn<>("Data di nascita");
		dataNascitaColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));
		dataNascitaColumn.setMinWidth(100);
		dataNascitaColumn.setPrefWidth(150);
		
		TableColumn<User, String> nicknameColumn = new TableColumn<>("Nickname");
		nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
		adaptColumnTable(nicknameColumn);
		tableView.setStyle("-fx-padding: 0;");
		
		TableColumn<User, Void> eliminaColumn = new TableColumn<>("Elimina");
		eliminaColumn.setCellFactory(param -> {
		    Button eliminaButton = new Button("Elimina");
		    TableCell<User, Void> cell = new TableCell<>() {
		        @Override
		        protected void updateItem(Void item, boolean empty) {
		            super.updateItem(item, empty);
		            if (empty) {
		                setGraphic(null);
		                setText(null);
		            } else {
		                eliminaButton.setOnAction(event -> {
		                    User user = getTableView().getItems().get(getIndex());
		                    // Logica per eliminare l'utente dalla tabella e dal file
		                    // userController.handleElimina(user);
		                });
		                setGraphic(eliminaButton);
		                setText(null);
		            }
		        }
		    };
		    return cell;
		});

		TableColumn<User, Void> modificaColumn = new TableColumn<>("Modifica");
		modificaColumn.setCellFactory(param -> {
		    Button modificaButton = new Button("Modifica");
		    TableCell<User, Void> cell = new TableCell<>() {
		        @Override
		        protected void updateItem(Void item, boolean empty) {
		            super.updateItem(item, empty);
		            if (empty) {
		                setGraphic(null);
		                setText(null);
		            } else {
		                modificaButton.setOnAction(event -> {
		                    User user = getTableView().getItems().get(getIndex());
		                    // Logica per modificare l'utente
		                    // userController.handleModifica(user);
		                });
		                setGraphic(modificaButton);
		                setText(null);
		            }
		        }
		    };
		    return cell;
		});
		
		
		tableView.getColumns().addAll(nomeColumn, cognomeColumn, dataNascitaColumn, nicknameColumn, eliminaColumn, modificaColumn);
		
		userList.addAll(userController.leggiUtenteDaFile());
		tableView.setItems(userList);
		
		return tableView;
	}
	
	public void registra() {
		User user = new User(nomeField.getText(), cognomeField.getText(), nicknameField.getText(), dataDiNascitaPicker.getValue());
		userController.handleRegistra(user);
		
	}
	
	private void adaptColumnTable(TableColumn<User, String> nomeColumn) {
		nomeColumn.setMinWidth(100);
		nomeColumn.setPrefWidth(150);
	}
	
	private void spazivuoti(GridPane layoutPane) {
	    layoutPane.add(new Label(""), 0, 3); // Questo aggiunge una riga vuota tra il form e la tabella
	    layoutPane.add(new Label(""), 0, 4); 
	    layoutPane.add(new Label(""), 0, 5); 
	    layoutPane.add(new Label(""), 0, 6); 
	    layoutPane.add(new Label(""), 0, 7); 
	    layoutPane.add(new Label(""), 0, 8); 
	    layoutPane.add(new Label(""), 0, 9); 
	}

}
