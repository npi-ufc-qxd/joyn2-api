package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.Convite;

@Repository
@Transactional
public interface ConviteRepository extends JpaRepository<Convite, Long>{
	
	public Convite findByEmail(String email);
}
