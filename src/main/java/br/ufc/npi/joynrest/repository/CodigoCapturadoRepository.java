package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.CodigoCapturado;

@Repository
@Transactional
public interface CodigoCapturadoRepository extends JpaRepository<CodigoCapturado, Long>{

	@Override
	@PreAuthorize("#codigoCapturado?.codigo != null and #codigoCapturado?.atividade?.usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	<S extends CodigoCapturado> S save(@Param("codigoCapturado") S codigoCapturado);

	@Override
	@PreAuthorize("#codigoCapturado?.atividade?.usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	void delete(@Param("codigoCapturado") CodigoCapturado codigoCapturado);
}
