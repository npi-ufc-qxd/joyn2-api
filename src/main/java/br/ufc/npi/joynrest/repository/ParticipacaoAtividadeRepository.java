package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.ParticipacaoAtividade;

@Repository
@Transactional
public interface ParticipacaoAtividadeRepository extends JpaRepository<ParticipacaoAtividade, Long> {

	@Query("select pa from ParticipacaoAtividade pa where pa.usuario.id = ?1 and pa.atividade.id = ?2")
	public ParticipacaoAtividade getParticipacaoAtividade(Long usuarioId, Long atividadeId);
}
