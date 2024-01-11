package jtrash.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import jtrash.model.User;
import jtrash.view.Home;

public class UserController {

	private Home home = new Home();
	private boolean elimina;
	private boolean modifica;
	private User userModificare;



	public void handleRegistra(User user) {

		List<User> listaUser = leggiUtenteDaFile();
		if(!elimina) {
			if(modifica) {
				for(int i =0; i<listaUser.size(); i++) {
					if(listaUser.get(i).getNome().equals(userModificare.getNome())) {
						listaUser.set(i, user);
					}
				}
			}else {
				listaUser.add(user);
			}

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
		try (FileInputStream fileIn = new FileInputStream("D:\\Progetti\\Privati\\GitHub\\JTrash\\JTrash\\resources\\persistence\\user.txt");
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

	public void handleModifica(User user,User modifiedUser) {
		modifica = true;
		this.userModificare = user;
		handleRegistra(modifiedUser);
	}

}
