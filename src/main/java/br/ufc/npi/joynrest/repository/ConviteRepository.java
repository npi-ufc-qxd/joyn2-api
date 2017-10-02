package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.Convite;

@Repository
@Transactional
public interface ConviteRepository extends JpaRepository<Convite, Long>{

	@Override
	@PreAuthorize("#convite?.email != null")
	<S extends Convite> S save(@Param("convite") S convite);

	@Override
	@PreAuthorize("false")
	void delete(@Param("convite") Convite convite);
	
	public Convite findByEmail(String email);
}
