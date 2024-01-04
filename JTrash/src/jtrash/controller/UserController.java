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

import jtrash.model.User;
import jtrash.view.Home;
import utility.Constants;

public class UserController {

	private Home home = new Home();

	
	
	public void handleRegistra(User user) {
	    try {
	        // Serializzazione dell'oggetto User
	        try (FileOutputStream fileOut = new FileOutputStream("/Users/valerio/eclipse/Workspace/Trash/JTrash/resources/persistence/user.txt");
	             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
	            out.writeObject(user);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    System.out.println("Test funzionamento:");
	    User leggo = leggiUtenteDaFile();
	    System.out.println(leggo.toString());
	}
	
	public User leggiUtenteDaFile() {
	    User user = null;
	    try (FileInputStream fileIn = new FileInputStream("/Users/valerio/eclipse/Workspace/Trash/JTrash/resources/persistence/user.txt");
	         ObjectInputStream in = new ObjectInputStream(fileIn)) {
	        user = (User) in.readObject();
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return user;
	}

		public void handleTornaHome() {
			System.out.println("Torno alla home");
			home.home(JTrashController.stage);
		}

	}
