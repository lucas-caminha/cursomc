package br.com.lucas.cursospring.services;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lucas.cursospring.domain.Cliente;
import br.com.lucas.cursospring.repository.ClienteRepository;
import br.com.lucas.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Optional<Cliente> cliente = clienteRepo.findByEmail(email);
		
		if (cliente.isEmpty()) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.get().setSenha(pe.encode(newPass));
		
		clienteRepo.save(cliente.get());
		emailService.sendNewPasswordEmail(cliente.get(), newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		} else {
			return (char) (rand.nextInt(26) + 97);
		}
		
	}
	
}
