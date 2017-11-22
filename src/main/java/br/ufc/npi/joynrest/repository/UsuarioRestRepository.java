package br.ufc.npi.joynrest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.npi.joynrest.model.Usuario;

@Repository
@Transactional
public interface UsuarioRestRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByEmail(String email);
	
	public Usuario findByKeyFacebook(String keyFacebook);
}
