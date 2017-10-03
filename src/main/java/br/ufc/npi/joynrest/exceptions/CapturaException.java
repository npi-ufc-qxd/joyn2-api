package br.ufc.npi.joynrest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CapturaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CapturaException(String mensagem){
		super(mensagem);
	}
}
