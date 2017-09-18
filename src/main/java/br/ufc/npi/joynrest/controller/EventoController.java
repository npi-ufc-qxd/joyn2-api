package br.ufc.npi.joynrest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.npi.joynrest.model.Evento;
import br.ufc.npi.joynrest.model.ParticipacaoEvento;
import br.ufc.npi.joynrest.response.ItemRanking;
import br.ufc.npi.joynrest.service.EventoService;

@RestController
public class EventoController {

	@Autowired
	EventoService eventoService;
	
	@RequestMapping(value = "/ranking/{eventoId}")
	@ResponseBody
	public List<ItemRanking> gerarRanking(@PathVariable Long eventoId){
		Evento evento = eventoService.buscarEvento(eventoId);
		
		List<ItemRanking> ranking = new ArrayList<>();
		for(ParticipacaoEvento pe : evento.getParticipantes()){
			ranking.add(new ItemRanking(pe.getUsuario().getNome(), pe.getPontos()));
		}
		
		Collections.sort(ranking, new Comparator<ItemRanking>() {
	        @Override
	        public int compare(ItemRanking item2, ItemRanking item1)
	        {
	            if (item2.getPontos() > item1.getPontos()) return -1;
	            else if (item2.getPontos() < item1.getPontos()) return 1;
	            else return 0;
	        }
	    });
		
		for(int i = 0; i < ranking.size(); i++)
			ranking.get(i).setColocacao(i+1);
		
		return ranking;
	}
}
