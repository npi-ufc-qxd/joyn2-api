package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.CodigoCapturado;

@Repository
@Transactional
public interface CodigoCapturadoRepository extends JpaRepository<CodigoCapturado, Long>{

}
