package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joynrest.model.HorarioAtividade;

@RepositoryRestResource(collectionResourceRel = "horarioAtividade", path = "horarioAtividade")
public interface HorarioAtividadeRepository extends JpaRepository<HorarioAtividade, Long> {
	
	@Override
	@PreAuthorize("false")
	<S extends HorarioAtividade> S save(@Param("horarioAtividade") S horarioAtividade);

	@Override
	@PreAuthorize("false")
	void delete(@Param("horarioAtividade") HorarioAtividade horarioAtividade);

}
