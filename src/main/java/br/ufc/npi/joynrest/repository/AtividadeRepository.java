package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joynrest.model.Atividade;

import java.lang.Long;
import java.lang.String;

@RepositoryRestResource(collectionResourceRel = "atividade", path = "atividade")
public interface AtividadeRepository extends JpaRepository<Atividade, Long>{
	
	@Override
	@PreAuthorize("#atividade?.nome != null and #atividade?.vagas != null and #atividade?.dias != null and #atividade?.pontuacao != null")
	<S extends Atividade> S save(@Param("atividade") S atividade);

	@Override
	@PreAuthorize("#atividade?.nome != null and #atividade?.vagas != null and #atividade?.dias != null and #atividade?.pontuacao != null")
	void delete(@Param("atividade") Atividade atividade);
	
	@Query("from Atividade a where a.codeCheckin = ?1 or a.codeCheckout = ?1")
	public Atividade atividadeCodigo(String code);
}
