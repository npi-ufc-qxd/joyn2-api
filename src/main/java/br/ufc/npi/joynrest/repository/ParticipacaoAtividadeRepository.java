package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.ParticipacaoAtividade;

@Repository
@Transactional
public interface ParticipacaoAtividadeRepository extends JpaRepository<ParticipacaoAtividade, Long> {

	@Query("select pa from ParticipacaoAtividade pa where pa.usuario.id = ?1 and pa.atividade.id = ?2")
	public ParticipacaoAtividade getParticipacaoAtividade(Long usuarioId, Long atividadeId);
	
	@Override
	@PreAuthorize("#participacaoAtividade?.atividade != null and #participacaoAtividade?.usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	<S extends ParticipacaoAtividade> S save(@Param("participacaoAtividade") S participacaoAtividade);

	@Override
	@PreAuthorize("#participacaoAtividade?.usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	void delete(@Param("participacaoAtividade") ParticipacaoAtividade participacaoAtividade);
}
