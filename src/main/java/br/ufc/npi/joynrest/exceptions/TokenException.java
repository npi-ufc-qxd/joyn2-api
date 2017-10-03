package br.ufc.npi.joynrest.exceptions;

public class TokenException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TokenException(String mensagem){
		super(mensagem);
	}

}
