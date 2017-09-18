package br.ufc.npi.joynrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joynrest.model.Convite;
import br.ufc.npi.joynrest.repository.ConviteRepository;


@Service
public class ConviteService {

	@Autowired
	ConviteRepository conviteRepository;
	
	public Convite getConvite(String email){
		return conviteRepository.findByEmail(email);
	}
}
