package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.Evento;

@Repository
@Transactional
public interface EventoRepository extends JpaRepository<Evento, Long> {
	
}
