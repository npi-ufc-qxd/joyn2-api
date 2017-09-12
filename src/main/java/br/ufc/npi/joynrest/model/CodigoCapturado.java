package br.ufc.npi.joynrest.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CodigoCapturado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;
	private Date horarioCaptura;
	
	@ManyToOne
	private ParticipacaoAtividade atividade;
	
	public CodigoCapturado() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getHorarioCaptura() {
		return horarioCaptura;
	}

	public void setHorarioCaptura(Date horarioCaptura) {
		this.horarioCaptura = horarioCaptura;
	}

	public ParticipacaoAtividade getAtividade() {
		return atividade;
	}

	public void setAtividade(ParticipacaoAtividade atividade) {
		this.atividade = atividade;
	}
	
	 

}