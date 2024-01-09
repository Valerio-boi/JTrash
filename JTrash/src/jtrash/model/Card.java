package jtrash.model;

import java.util.Arrays;

public class Card {
	
	private String seme;
	private String colore;
	private byte[] img;
	
	
	public String getSeme() {
		return seme;
	}
	public void setSeme(String seme) {
		this.seme = seme;
	}
	public String getColore() {
		return colore;
	}
	public void setColore(String colore) {
		this.colore = colore;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	
	@Override
	public String toString() {
		return "Card [seme=" + seme + ", colore=" + colore + ", img=" + Arrays.toString(img) + "]";
	}
	
	

}
