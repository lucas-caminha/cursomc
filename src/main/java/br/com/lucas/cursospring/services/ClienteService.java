package br.com.lucas.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.cursospring.domain.Cliente;
import br.com.lucas.cursospring.repository.ClienteRepository;
import br.com.lucas.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Optional<Cliente> find(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado. | ID: " + id + " | Tipo: " + Cliente.class.getName());
		}
		return cliente;
	}
	
}
