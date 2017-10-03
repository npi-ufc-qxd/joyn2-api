package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joynrest.model.CodigoCapturado;

@RepositoryRestResource(collectionResourceRel = "codigoCapturado", path = "codigoCapturado")
public interface CodigoCapturadoRepository extends JpaRepository<CodigoCapturado, Long>{

	@Override
	@PreAuthorize("#codigoCapturado?.codigo != null")
	<S extends CodigoCapturado> S save(@Param("codigoCapturado") S codigoCapturado);

	@Override
	@PreAuthorize("#codigoCapturado?.atividade?.usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	void delete(@Param("codigoCapturado") CodigoCapturado codigoCapturado);
}
