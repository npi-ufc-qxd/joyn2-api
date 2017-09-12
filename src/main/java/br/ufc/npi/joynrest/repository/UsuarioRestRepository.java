package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joynrest.model.Usuario;

@RepositoryRestResource(collectionResourceRel = "usuario", path = "usuario")
public interface UsuarioRestRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByEmail(String email);
	
	@Override
	@PreAuthorize("#usuario?.email != null and #usuario?.senha != null")
	<S extends Usuario> S save(@Param("usuario") S usuario);

	@Override
	@PreAuthorize("#usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	void delete(@Param("usuario") Usuario usuario);
}
