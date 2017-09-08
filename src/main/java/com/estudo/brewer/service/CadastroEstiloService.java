package com.estudo.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estudo.brewer.model.Estilo;
import com.estudo.brewer.repository.Estilos;
import com.estudo.brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {
	
	@Autowired
	private Estilos estilos;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {
		Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());
		
		if(estiloOptional.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		}
		
		/* saveAndFlush --> Salva e retorna o estilo com o recém id criado, se faz pra atender o cadastro rápido */
		return estilos.saveAndFlush(estilo);
	}

}
