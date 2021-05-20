package br.com.lucas.cursospring.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.cursospring.domain.ItemPedido;
import br.com.lucas.cursospring.domain.PagamentoComBoleto;
import br.com.lucas.cursospring.domain.Pedido;
import br.com.lucas.cursospring.domain.enums.EstadoPagamento;
import br.com.lucas.cursospring.repository.ItemPedidoRepository;
import br.com.lucas.cursospring.repository.PagamentoRepository;
import br.com.lucas.cursospring.repository.PedidoRepository;
import br.com.lucas.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	public Optional<Pedido> find(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		if (pedido.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado. | ID: " + id + " | Tipo: " + Pedido.class.getName());
		}
		return pedido;
	}

	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstate(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamento(pagto, pedido.getInstate());
		}
		
		pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).get().getPreco());
			ip.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		return pedido;
	}
}
