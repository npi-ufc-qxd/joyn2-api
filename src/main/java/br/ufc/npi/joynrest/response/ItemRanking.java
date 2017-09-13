package br.ufc.npi.joynrest.response;

public class ItemRanking {

	private String nome;
	private int pontos;
	
	public ItemRanking(){
	}
	
	public ItemRanking(String nome, int pontos) {
		this.nome = nome;
		this.pontos = pontos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	
}
