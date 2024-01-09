package jtrash.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


public class User implements Serializable{

	private static final long serialVersionUID = 4598207288789573868L;
	
	private String nome;
	private String cognome;
	private String nickname;
	private LocalDate dataNascita;
	private Integer livello=1;
	private List<Card> listaCarte;
	
	
	public User() {}
	
	public User(String nome, String cognome, String nickname, LocalDate dataNascita) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.nickname = nickname;
		this.dataNascita = dataNascita;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Integer getLivello() {
		return livello;
	}
	public void setLivello(Integer livello) {
		this.livello = livello;
	}
	
	public List<Card> getListaCarte() {
		return listaCarte;
	}

	public void setListaCarte(List<Card> listaCarte) {
		this.listaCarte = listaCarte;
	}

	@Override
	public String toString() {
		return "User [nome=" + nome + ", cognome=" + cognome + ", nickname=" + nickname + ", dataNascita=" + dataNascita
				+ ", livello=" + livello + "]";
	}
	
	
}
