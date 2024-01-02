package jtrash.model;

import java.util.Date;

public class User {

	private String nome;
	private String cognome;
	private String nickname;
	private Date dataNascita;
	private Integer livello;
	
	
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
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Integer getLivello() {
		return livello;
	}
	public void setLivello(Integer livello) {
		this.livello = livello;
	}
	
	
	@Override
	public String toString() {
		return "User [nome=" + nome + ", cognome=" + cognome + ", nickname=" + nickname + ", dataNascita=" + dataNascita
				+ ", livello=" + livello + "]";
	}
	
	
}
