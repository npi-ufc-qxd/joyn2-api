package br.ufc.npi.joynrest.response;

public class MensagemResgate {
	private String mensagem;
	private int pontos;
	
	public MensagemResgate(){	
	}
	
	public MensagemResgate(String mensagem, int pontos) {
		this.mensagem = mensagem;
		this.pontos = pontos;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

}