package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joynrest.model.ParticipacaoEvento;

@RepositoryRestResource(collectionResourceRel = "participacaoEvento", path = "participacaoEvento")
public interface ParticipacaoEventoRepository extends JpaRepository<ParticipacaoEvento, Long> {
	
	@Query("select pe from ParticipacaoEvento pe where pe.usuario.id = ?1 and pe.evento.id = ?2")
	public ParticipacaoEvento getParticipacaoEvento(Long usuarioId, Long eventoId);
	
	@Override
	@PreAuthorize("#participacaoEvento?.evento != null and #participacaoEvento?.usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	<S extends ParticipacaoEvento> S save(@Param("participacaoEvento") S participacaoEvento);

	@Override
	@PreAuthorize("#participacaoEvento?.usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	void delete(@Param("participacaoEvento") ParticipacaoEvento participacaoEvento);

}
