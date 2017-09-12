package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joynrest.model.Evento;

@RepositoryRestResource(collectionResourceRel = "evento", path = "evento")
public interface EventoRepository extends JpaRepository<Evento, Long> {
	
	@Override
	@PreAuthorize("#evento?.nome != null and #evento?.vagas != null")
	<S extends Evento> S save(@Param("evento") S evento);

	@Override
	@PreAuthorize("#evento?.nome != null and #evento?.vagas != null")
	void delete(@Param("evento") Evento evento);
}
