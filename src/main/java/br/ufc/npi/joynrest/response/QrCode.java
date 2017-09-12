package br.ufc.npi.joynrest.response;

public class QrCode {

	private String codigo;

	public QrCode(){
	}
	
	public QrCode(String codigo) {
		super();
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
