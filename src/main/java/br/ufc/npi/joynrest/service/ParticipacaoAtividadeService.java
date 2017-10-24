package br.ufc.npi.joynrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joynrest.model.Atividade;
import br.ufc.npi.joynrest.model.Papel;
import br.ufc.npi.joynrest.model.ParticipacaoAtividade;
import br.ufc.npi.joynrest.model.ParticipacaoEvento;
import br.ufc.npi.joynrest.model.Usuario;
import br.ufc.npi.joynrest.repository.ParticipacaoAtividadeRepository;

@Service
public class ParticipacaoAtividadeService {
	
	
	@Autowired
	AtividadeService atividadeService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ParticipacaoAtividadeRepository participacaoRepo;
	
	@Autowired
	ParticipacaoEventoService participacaoEvento;
	
	public ParticipacaoAtividade adicionarAtividade(Usuario usuario, Atividade atividade, Papel papel){
		ParticipacaoAtividade participacaoAtividade = new ParticipacaoAtividade();
		
		participacaoAtividade.setUsuario(usuario);
		participacaoAtividade.setAtividade(atividade);
		participacaoAtividade.setPapel(papel);
		
		if (atividade.getVagas() == null) {
			participacaoAtividade.setStatus(true);
		}else if (atividade.getParticipantes().size() < atividade.getVagas()){
			participacaoAtividade.setStatus(true);
		}else{
			participacaoAtividade.setStatus(false);
		}

		ParticipacaoAtividade paSalvo = participacaoRepo.save(participacaoAtividade);
		usuario.getParticipacaoAtividade().add(paSalvo);
		atividade.getParticipantes().add(paSalvo);
		usuarioService.atualizaUsuario(usuario);
		atividadeService.salvarAtividade(atividade);
		if(participacaoEvento.verificarParticipacaoEvento(usuario, atividade.getEvento()) == false){
			participacaoEvento.addParticipacaoEvento(new ParticipacaoEvento(usuario, atividade.getEvento(), Papel.PARTICIPANTE, true));
		}
		return paSalvo; 
	}
	
	public ParticipacaoAtividade getParticipacaoAtividade(Long id){
		return participacaoRepo.findOne(id);
	}
	
	public ParticipacaoAtividade getParticipacaoAtividade(Long idUsuario, Long idAtividade){
		return participacaoRepo.getParticipacaoAtividade(idUsuario, idAtividade);
	}
	
	public void removerAtividade(ParticipacaoAtividade pa){
		participacaoRepo.delete(pa);
	}
	
	public void atualizarParticipacaoAtividade(ParticipacaoAtividade pa){
		participacaoRepo.save(pa);
	}
	
	public boolean verificarParticipacaoAtividade(Usuario usuario, Atividade atividade){
		for (ParticipacaoAtividade participacaoAtividade : atividade.getParticipantes()) {
			if(usuario.getId() == participacaoAtividade.getUsuario().getId()){
				return true;
			}
		}
		return false;
	}

}
