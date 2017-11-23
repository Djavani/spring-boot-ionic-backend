package com.djavanigomes.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djavanigomes.cursomc.domain.Pedido;
import com.djavanigomes.cursomc.repositories.PedidoRepository;
import com.djavanigomes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id +
					", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}

}
