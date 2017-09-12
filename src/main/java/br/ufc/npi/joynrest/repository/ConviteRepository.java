package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joynrest.model.Convite;

@RepositoryRestResource(collectionResourceRel = "convite", path = "convite")
public interface ConviteRepository extends JpaRepository<Convite, Long>{

	@Override
	@PreAuthorize("#convite?.email != null")
	<S extends Convite> S save(@Param("convite") S convite);

	@Override
	@PreAuthorize("false")
	void delete(@Param("convite") Convite convite);
}
