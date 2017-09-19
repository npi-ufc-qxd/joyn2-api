package br.ufc.npi.joynrest.response;

import br.ufc.npi.joynrest.model.Papel;

public class UsuarioData {
	
	private Long id;
	private String nome;
	private String email;
	private String keyFacebook;
	private Papel papel;
	private Integer pontos;
	
	public UsuarioData(){
	}
	
	public UsuarioData(Long id, String nome, String email, String keyFacebook, Papel papel, Integer pontos) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.keyFacebook = keyFacebook;
		this.papel = papel;
		this.pontos = pontos;
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
	public Integer getPontos() {
		return pontos;
	}
	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}

}
