package jtrash.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
	
	
	
	public void creaMazzo() {
		System.out.println("creo il mazzo");
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
		
		mazzo.add(new Card("joker", "black_joker.png"));
		mazzo.add(new Card("joker", "red_joker.png"));
		
		
     
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
			  carta.setBacked(false);
			  card.setBacked(false);
			  listaGiocatori.get(currentPlayerIndex).getListaCarte().set(posizione -1, carta);
		   }
		   return card;
	   }
	   
	   public int posizioneCarta(Card carta) {
		   String prime = "" + carta.getNameCard().charAt(0)+carta.getNameCard().charAt(1);
		   for(int i= 1; i<=13; i++) {
			   if(!prime.contains("_") && Integer.parseInt(prime) > 9) {
				   if(prime.equals("" + i)) {
					   System.out.println(carta.getNameCard() + "   Dajee " + i);
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
	   
	   public boolean checkExistCard(Card card) {
		   System.out.println("Posizione carta di arrivo --> " + posizioneCarta(card) + " Posizione carta che ci sta ---> " + posizioneCarta(listaGiocatori.get(currentPlayerIndex).getListaCarte().get(posizioneCarta(card) - 1)));
		   if(posizioneCarta(card) == posizioneCarta(listaGiocatori.get(currentPlayerIndex).getListaCarte().get(posizioneCarta(card) - 1))) {
			   System.out.println("La carta è gia stata impostata");
			   return true;
		   }
		   
		   return false;
	   }

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}
	
}
