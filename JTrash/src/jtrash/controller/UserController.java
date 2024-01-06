package jtrash.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jtrash.model.User;
import jtrash.view.Home;
import utility.Constants;

public class UserController {

	private Home home = new Home();
	private boolean elimina;



	public void handleRegistra(User user) {

		List<User> listaUser = leggiUtenteDaFile();
		if(!elimina) {
			listaUser.add(user);
		}else {
			listaUser.removeIf(a -> a.getNome().equals(user.getNome()));
			for (User iter: listaUser) {
				System.out.println("aaaa" + iter);
				
			}
		}
			
		try {
			// Serializzazione dell'oggetto User
			try (FileOutputStream fileOut = new FileOutputStream("/Users/valerio/eclipse/Workspace/Trash/JTrash/resources/persistence/user.txt");
					ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
				out.writeObject(listaUser);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<User> leggiUtenteDaFile() {
		List<User> user = new ArrayList<User>();
		try (FileInputStream fileIn = new FileInputStream("/Users/valerio/eclipse/Workspace/Trash/JTrash/resources/persistence/user.txt");
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			user = (List<User>) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void handlePlay(User user) {
	    // Logica per avviare il gioco per l'utente selezionato
	    // ad esempio:
	    System.out.println("Gioca con " + user.getNickname());
	    // Puoi avviare la logica del gioco qui, aprendo una nuova finestra o una nuova scena, ecc.
	}

	public void handleElimina(User user) {
		elimina = true;
		handleRegistra(user);
	}

	public void handleTornaHome() {
		System.out.println("Torno alla home");
		home.home(JTrashController.stage);
	}

}
