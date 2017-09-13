package br.ufc.npi.joynrest.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufc.npi.joynrest.response.MensagemRetorno;
import br.ufc.npi.joynrest.response.TokenException;
import br.ufc.npi.joynrest.util.Constants;

public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		Authentication authentication = null;
		try{
			authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		}catch (TokenException e) {
			ObjectMapper mapper = new ObjectMapper();
			String json =  mapper.writeValueAsString(new MensagemRetorno(Constants.STAUTS_TOKEN_INVALIDO, "Token invalido"));
			HttpServletResponse res = (HttpServletResponse) response;
			res.reset();
			res.setHeader("Content-Type", "application/json;charset=UTF-8");
			res.setStatus(Constants.STAUTS_TOKEN_INVALIDO);
			res.getWriter().write(json);
		}
		
	}

}