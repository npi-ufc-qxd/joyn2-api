package br.ufc.npi.joynrest.response;

public class MensagemRetorno {
	private int codigo;
	private String mensagem;
	
	public MensagemRetorno(){	
	}
	
	public MensagemRetorno(int codigo, String mensagem) {
		this.mensagem = mensagem;
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}