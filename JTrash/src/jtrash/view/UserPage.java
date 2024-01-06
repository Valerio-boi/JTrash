package jtrash.view;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
import javafx.scene.layout.ColumnConstraints;
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
	private ObservableList<User> userList;
	private TableView<User> tableView;
	private GridPane layoutPane;

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
	    GridPane sezioneGiocatorePane = createPlayersSection("Selezione giocatore");
	    GridPane classificaPane = createClassifica("Classifica");
	    TableView<String> table = createTable();

	    // Aggiungi le tre sezioni affiancate (registrazione a sinistra, sezione giocatore e classifica a destra)
	    layoutPane = new GridPane();
	    layoutPane.setHgap(20);
	    layoutPane.add(registrazionePane, 0, 0);
	    layoutPane.add(sezioneGiocatorePane, 1, 0);
	    layoutPane.add(classificaPane, 2, 0);
	    
	    spazivuoti(layoutPane);
	    
	    tableView.setMaxSize(781, 400); // Imposta una dimensione massima per la tabella
	    tableView.setPrefWidth(600); 
	    
	    VBox tableWrapper = new VBox(); // Wrapper per la tabella
	    tableWrapper.getChildren().add(table);
	    tableWrapper.setAlignment(Pos.CENTER); 
	    
	    layoutPane.add(tableWrapper, 0, 10, 3, 1); // Modifica qui per distribuire la tabella su 3 colonne
	    
	    VBox.setVgrow(layoutPane, Priority.ALWAYS);
	    // Aggiungo 3 colonne per distribuire lo spazio uniformemente
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(33.33);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(33.33);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(33.33);
	    layoutPane.getColumnConstraints().addAll(column1, column2, column3);

	    content.getChildren().add(layoutPane);

	    return content;
	}
	
	private GridPane createClassifica(String sectionTitle) {
	    GridPane sectionPane = new GridPane();
	    sectionPane.setPadding(new Insets(30));
	    sectionPane.setVgap(15);
	    sectionPane.setHgap(5);
	    // Aggiungi stili o altre impostazioni necessarie

	    Label titleLabel = new Label(sectionTitle);
	    titleLabel.setFont(Font.font(Constants.Font.ARIAL_FONT, FontWeight.BOLD, 30));
	    titleLabel.setStyle(Constants.Css.BOLD_CLASSIC);
	   
	    List<User> userList = userController.leggiUtenteDaFile();
	    // Ordina la lista degli utenti in base al livello
	    userList.sort(Comparator.comparingInt(User::getLivello).reversed()); // Ordina per livello (in ordine decrescente)
	    
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(10); // Regola la larghezza della prima colonna
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(90); // Regola la larghezza della seconda colonna

	    sectionPane.getColumnConstraints().addAll(column1, column2);
	    sectionPane.add(titleLabel, 0, 0, 3, 1);

	    int userNumber = 1;
	    for (User user : userList) {
	        Label userLabel = new Label(userNumber + ".");
	        userLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #00BFFF; -fx-alignment: center;");
	        Label nicknameLabel = new Label(user.getNickname());
	        nicknameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #00BFFF; -fx-alignment: center-left;");

	        //... Aggiungi altri dettagli dell'utente o azioni necessarie nella classifica

	        sectionPane.add(userLabel, 0, userNumber);
	        sectionPane.add(nicknameLabel, 1, userNumber);
	        userNumber++;
	    }

	    return sectionPane;
	}
	
	private GridPane createPlayersSection(String title) {
	    GridPane playersSection = new GridPane();
	    playersSection.setPadding(new Insets(30));
	    playersSection.setVgap(15);

	    Label titleLabel = new Label(title);
	    titleLabel.setFont(Font.font(Constants.Font.ARIAL_FONT, FontWeight.BOLD, 30));
	    titleLabel.setStyle(Constants.Css.BOLD_CLASSIC);
	    playersSection.add(titleLabel, 0, 0, 6, 1);

	    List<User> userList = userController.leggiUtenteDaFile();
	    int userNumber = 1;

	    for (User user : userList) {
	        Label userLabel = new Label(userNumber + ".");
	        userLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #00BFFF; -fx-alignment: center; -fx-padding: 0 0 0 20;");

	        Label nicknameLabel = new Label(user.getNickname());
	        nicknameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #00BFFF; -fx-alignment: center; -fx-padding: 0 50 0 50;");

	        Button playButton = new Button("Gioca");
	        playButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFFFFF; -fx-background-color: #4CAF50; -fx-background-radius: 15;");
	        playButton.setOnAction(event -> userController.handlePlay(user));

	        HBox buttonBox = new HBox(playButton);
	        buttonBox.setAlignment(Pos.CENTER_LEFT);

	        playersSection.add(userLabel, 0, userNumber);
	        playersSection.add(nicknameLabel, 1, userNumber);
	        playersSection.add(buttonBox, 2, userNumber);

	        userNumber++;
	    }

	    return playersSection;
	}

	private void updatePlayersSection() {
	    GridPane sezioneGiocatorePane = createPlayersSection("Selezione giocatore");
	    layoutPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 0 && GridPane.getColumnIndex(node) == 1); // Rimuove la vecchia sezione dei giocatori
	    layoutPane.add(sezioneGiocatorePane, 1, 0); // Aggiunge la nuova sezione dei giocatori
	}
	
	private TableView createTable() {
		
		tableView = new TableView<>();
		userList = FXCollections.observableArrayList();
		
		tableView.setFixedCellSize(40); // Imposta l'altezza fissa per ogni riga
	    tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(6.5));

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
		    eliminaButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFFFFF; -fx-background-color: #FF0000; -fx-background-radius: 15;");
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
		                    userController.handleElimina(user); // Rimuove l'utente dal file
		                    tableView.getItems().remove(user); // Rimuove l'utente dalla tabella
		                    userList.remove(user);
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
		    modificaButton.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFFFFF; -fx-background-color: #00FF00; -fx-background-radius: 15;");
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
	                        ModificaUserDialog dialog = new ModificaUserDialog(user);
	                        Optional<User> result = dialog.showAndWait();
	                        result.ifPresent(modifiedUser -> {
	  		                    tableView.getItems().remove(user); // Rimuove l'utente dalla tabella
	  		                    userList.add(result.get());
	                            updatePlayersSection();
	                        });
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
	    userList.add(user); // Aggiunge il nuovo utente alla lista
	    refreshTable();
	    updatePlayersSection();
		
	}
	
	private void refreshTable() {
	    tableView.setItems(null); // Cancella gli elementi dalla tabella
	    tableView.setItems(userList); // Aggiorna la tabella con la lista aggiornata
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
