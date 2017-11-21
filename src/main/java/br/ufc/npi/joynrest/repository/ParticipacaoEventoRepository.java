package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.ParticipacaoEvento;

@Repository
@Transactional
public interface ParticipacaoEventoRepository extends JpaRepository<ParticipacaoEvento, Long> {
	
	@Query("select pe from ParticipacaoEvento pe where pe.usuario.id = ?1 and pe.evento.id = ?2")
	public ParticipacaoEvento getParticipacaoEvento(Long usuarioId, Long eventoId);
}
