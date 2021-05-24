package br.com.lucas.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lucas.cursospring.domain.Cidade;
import br.com.lucas.cursospring.domain.Cliente;
import br.com.lucas.cursospring.domain.Endereco;
import br.com.lucas.cursospring.domain.enums.TipoCliente;
import br.com.lucas.cursospring.dto.ClienteDTO;
import br.com.lucas.cursospring.dto.ClienteNewDTO;
import br.com.lucas.cursospring.repository.ClienteRepository;
import br.com.lucas.cursospring.services.exceptions.DataIntegrityException;
import br.com.lucas.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Optional<Cliente> find(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado. | ID: " + id + " | Tipo: " + Cliente.class.getName());
		}
		return cliente;
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return clienteRepository.save(cliente);
	}

	public Cliente update(Cliente cliente) {	
		Optional<Cliente> newCli = find(cliente.getId());
		updateData(newCli.get(), cliente);
		return clienteRepository.save(newCli.get());
	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um Cliente porque há Entidades relacionadas.");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO cliDto) {
		return new Cliente(cliDto.getId(), cliDto.getNome(), cliDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO cliDto) {
		Cliente cli = new Cliente(null, cliDto.getNome(), cliDto.getEmail(), cliDto.getCpfCnpj(), TipoCliente.toEnum(cliDto.getTipo()), pe.encode(cliDto.getSenha()));
		
		Cidade cidade = new Cidade(cliDto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, cliDto.getLogradouro(), cliDto.getNumero(), cliDto.getComplemento(), cliDto.getBairro(), cliDto.getCep(), cli, cidade);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(cliDto.getTelefone1());
		if (cliDto.getTelefone2() != null) {
			cli.getTelefones().add(cliDto.getTelefone2());
		}
		if (cliDto.getTelefone3() != null) {
			cli.getTelefones().add(cliDto.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente newCli, Cliente cliente) {
		newCli.setNome(cliente.getNome());
		newCli.setEmail(cliente.getEmail());
		
	}
}
