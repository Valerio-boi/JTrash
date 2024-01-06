package jtrash.view;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jtrash.model.User;
import jtrash.controller.UserController;
import utility.Constants;

public class ModificaUserDialog extends Dialog<User> {

    private UserController userController;
    private TextField nomeField;
    private TextField cognomeField;
    private TextField nicknameField;
    private DatePicker dataDiNascitaPicker;

    public ModificaUserDialog(User user) {
        userController = new UserController();

        setTitle("Modifica utente");
        setHeaderText(null);

        GridPane formPane = new GridPane();
        formPane.setHgap(20);
        formPane.setVgap(10);

        Label nomeLabel = new Label(Constants.Label.NOME);
        nomeField = new TextField(user.getNome());
        Label cognomeLabel = new Label(Constants.Label.COGNOME);
        cognomeField = new TextField(user.getCognome());
        Label dataNascitaLabel = new Label(Constants.Label.DATA_NASCITA);
        dataDiNascitaPicker = new DatePicker(user.getDataNascita());
        Label nicknameLabel = new Label(Constants.Label.NICKNAME);
        nicknameField = new TextField(user.getNickname());
        // Aggiungi altri campi del form con i valori dell'utente

        // Aggiungi i campi al GridPane
    	formPane.add(nomeLabel, 0, 1);
		formPane.add(nomeField, 1, 1);
		formPane.add(cognomeLabel, 0, 2);
		formPane.add(cognomeField, 1, 2);
		formPane.add(dataNascitaLabel, 0, 3);
		formPane.add(dataDiNascitaPicker, 1, 3);
		formPane.add(nicknameLabel, 0, 4);
		formPane.add(nicknameField, 1, 4);


        getDialogPane().setContent(formPane);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);

        okButton.getStyleClass().add("button-ok");
        cancelButton.getStyleClass().add("button-cancel");
        getDialogPane().getStylesheets().add(getClass().getResource(Constants.Path.CSS_DIALOG).toExternalForm());


        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                // Logic for updating the user with modified details
                User modifiedUser = new User(nomeField.getText(), cognomeField.getText(), nicknameField.getText(), dataDiNascitaPicker.getValue());
                userController.handleModifica(user, modifiedUser);
                return modifiedUser;
            }
            return null;
        });
    }
}