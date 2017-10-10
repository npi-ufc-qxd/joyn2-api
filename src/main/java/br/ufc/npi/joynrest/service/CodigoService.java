package br.ufc.npi.joynrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joynrest.model.CodigosTurno;
import br.ufc.npi.joynrest.repository.CodigoRepository;

@Service
public class CodigoService {

	@Autowired
	CodigoRepository codigoRepository;
	
	public CodigosTurno salvar(CodigosTurno codigo){
		return codigoRepository.save(codigo);
	}
}
