package br.ufc.npi.joynrest.response;

public class MensagemResgate {
	private String mensagem;
	private int pontuacao;
	
	public MensagemResgate(){	
	}
	
	public MensagemResgate(String mensagem, int pontuacao) {
		this.mensagem = mensagem;
		this.pontuacao = pontuacao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

}