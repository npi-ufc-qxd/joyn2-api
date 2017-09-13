package br.ufc.npi.joynrest.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufc.npi.joynrest.response.AuthToken;
import br.ufc.npi.joynrest.response.TokenException;
import br.ufc.npi.joynrest.util.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	// EXPIRATION_TIME = 7 dias
	static final long EXPIRATION_TIME = 1000 * Constants.TOKEN_EXPIRAR_MINUTOS;
	
	static void addAuthentication(HttpServletResponse response, String username) throws IOException {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, Constants.CHAVE_SECRETA)
				.compact();
		
		//response.getWriter().write(/*Constants.HEADER_STRING,*/ Constants.TOKEN_PREFIX + " " + JWT);
		ObjectMapper mapper = new ObjectMapper();
		String json =  mapper.writeValueAsString(new AuthToken(Constants.TOKEN_PREFIX + " " + JWT));
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.getWriter().write(json);
	}
	
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
