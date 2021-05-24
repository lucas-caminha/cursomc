package br.com.lucas.cursospring.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.lucas.cursospring.domain.Cliente;
import br.com.lucas.cursospring.domain.ItemPedido;
import br.com.lucas.cursospring.domain.PagamentoComBoleto;
import br.com.lucas.cursospring.domain.Pedido;
import br.com.lucas.cursospring.domain.enums.EstadoPagamento;
import br.com.lucas.cursospring.repository.ItemPedidoRepository;
import br.com.lucas.cursospring.repository.PagamentoRepository;
import br.com.lucas.cursospring.repository.PedidoRepository;
import br.com.lucas.cursospring.security.UserSpringSecurity;
import br.com.lucas.cursospring.services.exceptions.AuthorizationException;
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
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
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
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()).get());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamento(pagto, pedido.getInstate());
		}
		
		pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()).get());
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido);
		
		return pedido;
	}
	
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSpringSecurity user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Optional<Cliente> cliente = clienteService.find(user.getId());
		
		if (cliente.isPresent()) {
			return pedidoRepository.findByCliente(cliente.get(), pageRequest);
		}
		return null;
	}
	
	
}
