package br.ufc.npi.joynrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joynrest.model.Evento;
import br.ufc.npi.joynrest.model.ParticipacaoEvento;
import br.ufc.npi.joynrest.model.Usuario;
import br.ufc.npi.joynrest.repository.ParticipacaoEventoRepository;

@Service
public class ParticipacaoEventoService {

	@Autowired
	ParticipacaoEventoRepository participacaoEventoRepository;
	
	@Autowired
	EventoService eventoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	public ParticipacaoEvento addParticipacaoEvento(ParticipacaoEvento participacaoEvento){
		ParticipacaoEvento peSalvo = participacaoEventoRepository.save(participacaoEvento);
		participacaoEvento.getUsuario().getParticipacaoEvento().add(peSalvo);
		participacaoEvento.getEvento().getParticipantes().add(peSalvo);
		eventoService.salvarEvento(participacaoEvento.getEvento());
		usuarioService.atualizaUsuario(participacaoEvento.getUsuario());
		return peSalvo;
	}
	
	public ParticipacaoEvento getPartipacaoEvento(Long id){
		return participacaoEventoRepository.findOne(id);
	}
	
	public ParticipacaoEvento getParticipacaoEvento(Long idUsuario, Long idEvento){
		return participacaoEventoRepository.getParticipacaoEvento(idUsuario, idEvento);
	}
	
	public void atualizarParticipacaoEvento(ParticipacaoEvento pe){
		participacaoEventoRepository.save(pe);
	}
	
	public boolean verificarParticipacaoEvento(Usuario usuario, Evento evento){
		for (ParticipacaoEvento participacaoEvento : evento.getParticipantes()) {
			if(usuario.getId() == participacaoEvento.getUsuario().getId()){
				return true;
			}
		}
		return false;
	}
}
