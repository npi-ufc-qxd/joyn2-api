package br.ufc.npi.joynrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joynrest.model.Evento;
import br.ufc.npi.joynrest.repository.EventoRepository;

@Service
public class EventoService {

	@Autowired
	EventoRepository eventoRepo;
	
	public Evento salvarEvento(Evento evento){
		Evento eventoSalvo = eventoRepo.save(evento);
		return eventoSalvo;
	}
	
	public Evento buscarEvento(Long id){
		return eventoRepo.findOne(id);
	}
}
