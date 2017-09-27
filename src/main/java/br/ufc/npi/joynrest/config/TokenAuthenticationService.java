package br.ufc.npi.joynrest.config;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import br.ufc.npi.joynrest.response.TokenException;
import br.ufc.npi.joynrest.util.Constants;
import io.jsonwebtoken.Jwts;

public class TokenAuthenticationService {
	
	static Authentication getAuthentication(HttpServletRequest request) throws TokenException {
		String token = request.getHeader(Constants.HEADER_STRING);
		
		if (token != null) {
			String user = null;
			try {
				user = Jwts.parser()
						.setSigningKey(Constants.CHAVE_SECRETA)
						.parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""))
						.getBody()
						.getSubject();
			}catch (Exception e) {
				throw new TokenException("Token invalido");
			}
			
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		}
		return null;
	}
}
