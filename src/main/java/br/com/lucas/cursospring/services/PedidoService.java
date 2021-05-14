package br.com.lucas.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.cursospring.domain.Pedido;
import br.com.lucas.cursospring.repository.PedidoRepository;
import br.com.lucas.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository PedidoRepository;
	
	public Optional<Pedido> find(Integer id) {
		Optional<Pedido> pedido = PedidoRepository.findById(id);
		if (pedido.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado. | ID: " + id + " | Tipo: " + Pedido.class.getName());
		}
		return pedido;
	}
}
