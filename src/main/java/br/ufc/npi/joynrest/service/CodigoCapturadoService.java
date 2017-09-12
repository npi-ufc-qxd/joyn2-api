package br.ufc.npi.joynrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.joynrest.model.CodigoCapturado;
import br.ufc.npi.joynrest.repository.CodigoCapturadoRepository;

@Service
public class CodigoCapturadoService {

	@Autowired
	CodigoCapturadoRepository ccRepository;
	
	public CodigoCapturado salvar(CodigoCapturado codigoCapturado){
		return ccRepository.save(codigoCapturado);
	}
}
