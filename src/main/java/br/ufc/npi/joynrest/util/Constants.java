package br.ufc.npi.joynrest.util;

public class Constants {
	public static final int TAM_MAX_IMG_64 = 7000000;
	public static final long TOKEN_EXPIRAR_MINUTOS = 24 * 60 * 7;	
	public static final String HEADER_STRING = "Authorization";
	public static final String TOKEN_PREFIX = "Joyn";
	public static final String CHAVE_SECRETA = "secretkeyjoyn";
	
	//Codigo status
	public static final int STATUS_OK = 200;
	public static final int STATUS_BAD_REQUEST = 400;
	public static final int STATUS_QRCODE_PROCESSADO = 208;
	public static final int STAUTS_TOKEN_INVALIDO = 498;
}