package br.ufc.npi.joynrest.response;

import br.ufc.npi.joynrest.model.Papel;

public class UsuarioData {
	
	private Long id;
	private String nome;
	private String email;
	private String foto64;
	private String keyFacebook;
	private Papel papel;
	
	public UsuarioData(){
	}
	
	public UsuarioData(Long id, String nome, String email, String foto64, String keyFacebook, Papel papel) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.foto64 = foto64;
		this.keyFacebook = keyFacebook;
		this.papel = papel;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFoto64() {
		return foto64;
	}
	public void setFoto64(String foto64) {
		this.foto64 = foto64;
	}
	public String getKeyFacebook() {
		return keyFacebook;
	}
	public void setKeyFacebook(String keyFacebook) {
		this.keyFacebook = keyFacebook;
	}
	public Papel getPapel() {
		return papel;
	}
	public void setPapel(Papel papel) {
		this.papel = papel;
	}

}
