package jtrash.controller;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import utility.Constants;

public class ResolutionController {


	public void setRisoluzione(Optional<ButtonType> result, Stage stage) {

		switch(result.get().getText()) {

			case Constants.Label.RIS_1920:
				impostaRisoluzione(1920, 1080, stage);
				break;
			case Constants.Label.RIS_1280:
				impostaRisoluzione(1280, 720, stage);
				break;
			case Constants.Label.RIS_1366:
				impostaRisoluzione(1366, 768, stage);
				break;
			case Constants.Label.RIS_1024:
				impostaRisoluzione(1024, 768, stage);
				break;
			case Constants.Label.RIS_800:
				impostaRisoluzione(800, 600, stage);
				break;

		}

	}



	private void impostaRisoluzione(double width, double height, Stage stage) {
		stage.setWidth(width);
		stage.setHeight(height);
		stage.setFullScreen(false);
	}



}
