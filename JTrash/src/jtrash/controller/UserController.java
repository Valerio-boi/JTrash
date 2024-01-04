package jtrash.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jtrash.model.User;
import jtrash.view.Home;
import utility.Constants;

public class UserController {

	private Home home = new Home();

	public void handleRegistra(User user){

		   System.out.println(user.toString());
		    try {
		        Path path = Paths.get(getClass().getResource(Constants.Path.PERSISTENCE_USER).toURI()); 

		        // Serializzazione dell'oggetto User
		        try (FileOutputStream fileOut = new FileOutputStream(path.toFile());
		             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
		            out.writeObject(user);
		        }
		    } catch (IOException e) {
		    	System.out.println("ciaooooooooo");
		        e.printStackTrace();
		    } catch (URISyntaxException e) {
		    	System.out.println("ciaooooooooo");
				e.printStackTrace();
			}

	}

	public void handleTornaHome() {
		System.out.println("Torno alla home");
		home.home(JTrashController.stage);
	}

}
