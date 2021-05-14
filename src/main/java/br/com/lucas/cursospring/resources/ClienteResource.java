package br.com.lucas.cursospring.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucas.cursospring.domain.Cliente;
import br.com.lucas.cursospring.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Cliente>> listar(@PathVariable Integer id) {
		Optional<Cliente> cliente = clienteService.find(id);
		return ResponseEntity.ok().body(cliente);
	}
	
}
