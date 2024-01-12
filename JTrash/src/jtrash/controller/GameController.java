package jtrash.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jtrash.model.Card;
import jtrash.model.User;
import utility.Constants;

public class GameController {

	public List<Card> mazzo = new ArrayList<Card>();
	public List<User> listaGiocatori = new ArrayList<User>();
	private int currentPlayerIndex = 0;

	ArrayList<String> tipiCarteNum = new ArrayList<String>(Arrays.asList(
			Constants.Card.CLUBS,Constants.Card.DIAMONDS, 
			Constants.Card.HEARTS, Constants.Card.SPADES));



	public void creaMazzo(int numberPlayers) {
		System.out.println("creo il mazzo");
		int numberMazzi = 1;
		if(numberPlayers > 2) {
			numberMazzi = (int) (1+ Math.ceil(numberPlayers/2)); 
		}
		System.out.println("Numero mazzi ----> " + numberMazzi);
		for(int e = 0; e<numberMazzi; e++ ) {
			for(String iter: tipiCarteNum) {
				for(int i=1; i<= 13; i++) {
					Card carta = new Card();
					switch(iter) {
					case Constants.Card.CLUBS:
						carta.setSeme("Fiori");
						break;
					case Constants.Card.DIAMONDS:
						carta.setSeme("Picche");
						break;
					case Constants.Card.HEARTS:
						carta.setSeme("Cuori");
						break;
					case Constants.Card.SPADES:
						carta.setSeme("Spade");
						break;
					}
					carta.setNameCard("" + i + iter + ".png");
					carta.setBacked(true);
					mazzo.add(carta);
				}

			}
			mazzo.add(new Card("joker", "14_black_joker.png", true));
			mazzo.add(new Card("joker", "14_red_joker.png", true));
		}
		
		System.out.println("Quante carta abbiamo ---> " + mazzo.size());

		Collections.shuffle(mazzo);


	}

	public void assegnaMazzo(int giocatori) {

		for(int i = 0; i<giocatori; i++) {
			User user = new User();
			List<Card> listM = new ArrayList<Card>();
			for(int e = 0; e<10; e++) {
				listM.add(this.mazzo.get(e));
				this.mazzo.remove(e);
			}
			
			Collections.shuffle(listM);
			user.setListaCarte(listM);
			
			listaGiocatori.add(user);
		}

	}

	private void nextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % listaGiocatori.size();
		// Aggiungi qui la logica per gestire il turno del giocatore corrente
		// Potresti, ad esempio, mostrare un messaggio, eseguire azioni specifiche per il turno, ecc.
		System.out.println("È il turno di " + listaGiocatori.get(currentPlayerIndex).getNickname());
	}


	public Card pescaCarta() {
		if(mazzo.size() != 0) {
			Card carta = mazzo.get(0);
			mazzo.remove(0);
			return carta;
		}else {
			return null;
		}
	}

	public Card sostituisciCarta(Card carta, int posizione) {
		Card card = null;
		if(posizione <= 10) {
			card = listaGiocatori.get(currentPlayerIndex).getListaCarte().get(posizione - 1);
			listaGiocatori.get(currentPlayerIndex).getListaCarte().set(posizione -1, carta);
		}
		return card;
	}

	public boolean checkCartaCoperta(int cardIndex, VBox playerArea, boolean upDown) {
		ImageView cardToReplace = new ImageView();

		if (upDown) {
			HBox cardsAbove = (HBox) playerArea.getChildren().get(1); // Sezione delle carte sopra

			if (cardIndex >= 0 && cardIndex < 5) {
				Node cardNodeToReplace = cardsAbove.getChildren().get(cardIndex);
				cardNodeToReplace.setDisable(true);
				if (cardNodeToReplace instanceof ImageView) {
					cardToReplace = (ImageView) cardNodeToReplace;
				}
			}

		}else {
			HBox cardsBelow = (HBox) playerArea.getChildren().get(3);

			if (cardIndex >= 0 && cardIndex < 5) {

				Node cardNodeToReplace = cardsBelow.getChildren().get(cardIndex);
				cardNodeToReplace.setDisable(true);
				if (cardNodeToReplace instanceof ImageView) {
					cardToReplace = (ImageView) cardNodeToReplace;
				}
			}
		}

		return cardToReplace.getImage().getUrl().contains("backBlack.png");

	}

	public int posizioneCarta(Card carta) {
		String prime = "" + carta.getNameCard().charAt(0)+carta.getNameCard().charAt(1);
		for(int i= 1; i<=14; i++) {
			if(!prime.contains("_") && Integer.parseInt(prime) > 9) {
				if(prime.equals("" + i)) {
					return i;
				}
			}else {
				if(carta.getNameCard().contains("" + i)) {
					return i;
				}
			}

		}

		return 0;
	}

	public boolean checkExistCard(Card card, boolean isScoperta) {
		if(posizioneCarta(card)==14 || posizioneCarta(card)==12) {
			return false;
		}

		if(isScoperta && (posizioneCarta(card) == posizioneCarta(listaGiocatori.get(currentPlayerIndex).getListaCarte().get(posizioneCarta(card) - 1)))) {
			System.out.println("La carta è gia stata impostata");
			return true;
		}

		return false;
	}


	public boolean checkIsTrash(VBox playerArea) {
		boolean isCoperta = true;
		for(int i = 0; i<10; i++) {
			if (i <= 4) { // Se il numero è tra 1 e 5, sostituisci la carta sopra
				isCoperta = checkCartaCoperta(i,  playerArea, true);
			} else if(i <= 9) { // Altrimenti, sostituisci la carta sotto
				isCoperta = checkCartaCoperta(i - 5, playerArea, false);
			}

			if(isCoperta) {
				return false;
			}
		}

		System.out.println("So tutte scoperte TRASH");
		return true;
	}
	
	public int getFirstBacked(VBox playerArea) {
		boolean isCoperta = false;
		for(int i = 0; i<10; i++) {
			if (i <= 4) { // Se il numero è tra 1 e 5, sostituisci la carta sopra
				isCoperta = checkCartaCoperta(i,  playerArea, true);
			} else if(i <= 9) { // Altrimenti, sostituisci la carta sotto
				isCoperta = checkCartaCoperta(i - 5, playerArea, false);
			}
			
			if(isCoperta) {
				System.out.println("Is copertaaa get fdirts " + i);
				return i;
			}
		}

		return 0;
	}
	
	
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

}
