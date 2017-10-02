package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.HorarioAtividade;

@Repository
@Transactional
public interface HorarioAtividadeRepository extends JpaRepository<HorarioAtividade, Long> {
	
	@Override
	@PreAuthorize("false")
	<S extends HorarioAtividade> S save(@Param("horarioAtividade") S horarioAtividade);

	@Override
	@PreAuthorize("false")
	void delete(@Param("horarioAtividade") HorarioAtividade horarioAtividade);

}
