package com.djavanigomes.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djavanigomes.cursomc.domain.ItemPedido;
import com.djavanigomes.cursomc.domain.PagamentoComBoleto;
import com.djavanigomes.cursomc.domain.Pedido;
import com.djavanigomes.cursomc.domain.enums.EstadoPagamento;
import com.djavanigomes.cursomc.repositories.ClienteRepository;
import com.djavanigomes.cursomc.repositories.ItemPedidoRepository;
import com.djavanigomes.cursomc.repositories.PagamentoRepository;
import com.djavanigomes.cursomc.repositories.PedidoRepository;
import com.djavanigomes.cursomc.repositories.ProdutoRepository;
import com.djavanigomes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmailService emailService;
	
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id +
					", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	 public Pedido insert(Pedido obj) {
		 obj.setId(null);
		 obj.setInstante(new Date());
		 obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
		 obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		 obj.getPagamento().setPedido(obj);
		 if(obj.getPagamento() instanceof PagamentoComBoleto) {
			 PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			 boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		 }
		 obj = repo.save(obj);
		 pagamentoRepository.save(obj.getPagamento());
		 for (ItemPedido ip : obj.getItens()) {
			 ip.setDesconto(0.0);
			 ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
			 ip.setPreco(ip.getProduto().getPreco());
			 ip.setPedido(obj); 
		 }
		 itemPedidoRepository.save(obj.getItens());
		 emailService.sendOrderConfirmationEmail(obj);
		 return obj;
	 }

}
