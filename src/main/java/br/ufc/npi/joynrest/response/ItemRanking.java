package br.ufc.npi.joynrest.response;

public class ItemRanking {

	private String nome;
	private String fotoUrl;
	private int pontos;
	
	public ItemRanking(){
	}
	
	public ItemRanking(String nome, String fotoUrl, int pontos) {
		this.nome = nome;
		this.fotoUrl = fotoUrl;
		this.pontos = pontos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	
}
