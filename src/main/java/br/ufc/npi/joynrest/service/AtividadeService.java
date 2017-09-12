package br.ufc.npi.joynrest.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joynrest.model.Atividade;
import br.ufc.npi.joynrest.repository.AtividadeRepository;


@Service
public class AtividadeService {
	
	@Autowired
	AtividadeRepository atividadeRepo;
	
	public Atividade buscarAtividade(Long id){
		return atividadeRepo.findOne(id);
	}

	public Atividade salvarAtividade(Atividade atividade){
		Atividade atividadeSalva = atividadeRepo.save(atividade);
		return atividadeSalva;
	}
	
	public void removerAtividade(Atividade atividade){
		atividadeRepo.delete(atividade);
	}
	
	public Atividade getAtividade(String codigo){
		return atividadeRepo.atividadeCodigo(codigo);
	}
}
