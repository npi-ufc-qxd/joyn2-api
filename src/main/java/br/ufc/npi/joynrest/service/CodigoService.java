package br.ufc.npi.joynrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joynrest.model.Codigo;
import br.ufc.npi.joynrest.repository.CodigoRepository;

@Service
public class CodigoService {

	@Autowired
	CodigoRepository codigoRepository;
	
	public Codigo salvar(Codigo codigo){
		return codigoRepository.save(codigo);
	}
}
