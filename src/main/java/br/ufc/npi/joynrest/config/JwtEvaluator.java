package br.ufc.npi.joynrest.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufc.npi.joynrest.model.Usuario;
import br.ufc.npi.joynrest.service.UsuarioService;
import br.ufc.npi.joynrest.util.Constants;
import io.jsonwebtoken.Jwts;

@Component(value="jwtEvaluator")
public class JwtEvaluator {
	
	@Autowired
	UsuarioService usuarioService;
	
	private @Autowired HttpServletRequest request;
	
	public Usuario usuarioToken() throws ServletException{
		String token = request.getHeader(Constants.HEADER_STRING);
        if (token != null) {
			String email = null;
			try {
				email = Jwts.parser()
						.setSigningKey(Constants.CHAVE_SECRETA)
						.parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""))
						.getBody()
						.getSubject();
				System.out.println("--jwtEvaluator-- Email: " + email);
				return usuarioService.getUsuario(email);
			}catch (Exception e) {
				throw new ServletException("Token invalido");
			}
        }
        
        throw new ServletException("Token invalido");
	}
}
