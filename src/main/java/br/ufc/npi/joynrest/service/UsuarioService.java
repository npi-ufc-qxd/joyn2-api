package br.ufc.npi.joynrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufc.npi.joynrest.model.Usuario;
import br.ufc.npi.joynrest.repository.UsuarioRestRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRestRepository usuarioRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UsuarioService() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}
	
	public Usuario salvarUsuario(Usuario usuario){
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	public boolean logar(String email, String senha){
		Usuario userBanco = usuarioRepository.findByEmail(email);
		if(userBanco != null && new BCryptPasswordEncoder().matches(senha, userBanco.getSenha())) return true;
		else return false;
	}
	
	public Usuario getUsuario(String email){
		return usuarioRepository.findByEmail(email);
	}
	
	public Usuario atualizaUsuario(Usuario usuario){
		return usuarioRepository.save(usuario);
	}

}
