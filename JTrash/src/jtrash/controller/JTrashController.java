package jtrash.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import jtrash.view.Home;

public class JTrashController extends Application{
	
    private Home home;
    
    @Override
    public void init() throws Exception {
    	super.init();
    	
    	if(home == null) {
    		home = new Home();
    	}
    }

	public void start(Stage stage){
    	
    	home.home(stage);
    	
    }


    public static void main(String[] args){
        launch(args);
    }
	


}
