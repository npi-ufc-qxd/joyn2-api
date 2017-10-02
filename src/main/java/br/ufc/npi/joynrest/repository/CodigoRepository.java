package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.Codigo;

@Repository
@Transactional
public interface CodigoRepository extends JpaRepository<Codigo, Long> {

	@Override
	@PreAuthorize("#codigo?.eventoId != null and #codigo?.atividadeId != null and #codigo?.checkin != null")
	<S extends Codigo> S save(@Param("codigo") S codigo);

	@Override
	@PreAuthorize("false")
	void delete(@Param("codigo") Codigo codigo);
}
