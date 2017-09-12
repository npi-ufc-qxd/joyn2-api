package br.ufc.npi.joynrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joynrest.model.Token;

@RepositoryRestResource(collectionResourceRel = "token", path = "token")
public interface TokenRepository extends JpaRepository<Token, String>{

	@Override
	@PreAuthorize("#token?.token != null and #token?.usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	<S extends Token> S save(@Param("token") S token);

	@Override
	@PreAuthorize("#token?.usuario?.id == @jwtEvaluator.usuarioToken()?.id")
	void delete(@Param("token") Token token);
}
