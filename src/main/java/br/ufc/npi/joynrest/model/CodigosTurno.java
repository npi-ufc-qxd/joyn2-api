package br.ufc.npi.joynrest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CodigosTurno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Atividade atividade;
	
	private int turno;
	
	private String codigoCheckin;
	
	private String codigoCheckout;

	public CodigosTurno() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public String getCodigoCheckin() {
		return codigoCheckin;
	}

	public void setCodigoCheckin(String codigoCheckin) {
		this.codigoCheckin = codigoCheckin;
	}

	public String getCodigoCheckout() {
		return codigoCheckout;
	}

	public void setCodigoCheckout(String codigoCheckout) {
		this.codigoCheckout = codigoCheckout;
	}

}
