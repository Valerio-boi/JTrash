package jtrash.model;


public class Card {
	
	private String seme;
	private String nameCard;
	
	public Card() {}
	
	public Card(String seme, String nameCard) {
		super();
		this.seme = seme;
		this.nameCard = nameCard;
	}
	
	public String getSeme() {
		return seme;
	}
	public void setSeme(String seme) {
		this.seme = seme;
	}
	
	public String getNameCard() {
		return nameCard;
	}
	public void setNameCard(String nameCard) {
		this.nameCard = nameCard;
	}


	

}
