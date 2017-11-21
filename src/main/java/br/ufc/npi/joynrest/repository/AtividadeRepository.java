package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.Atividade;

import java.lang.Long;
import java.lang.String;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AtividadeRepository extends JpaRepository<Atividade, Long>{
	
	@Query("select ct.atividade from CodigosTurno ct where ct.codigoCheckin = ?1 or ct.codigoCheckout = ?1")
	public Atividade atividadeCodigo(String code);
}

