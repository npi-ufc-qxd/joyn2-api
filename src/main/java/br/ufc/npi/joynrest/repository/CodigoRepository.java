package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joynrest.model.Codigo;

@RepositoryRestResource(collectionResourceRel = "codigo", path = "codigo")
public interface CodigoRepository extends JpaRepository<Codigo, Long> {

	@Override
	@PreAuthorize("#codigo?.eventoId != null and #codigo?.atividadeId != null and #codigo?.checkin != null")
	<S extends Codigo> S save(@Param("codigo") S codigo);

	@Override
	@PreAuthorize("false")
	void delete(@Param("codigo") Codigo codigo);
}
