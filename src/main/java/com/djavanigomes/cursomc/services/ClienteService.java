package com.djavanigomes.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djavanigomes.cursomc.domain.Cliente;
import com.djavanigomes.cursomc.repositories.ClienteRepository;
import com.djavanigomes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id +
					", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

}
