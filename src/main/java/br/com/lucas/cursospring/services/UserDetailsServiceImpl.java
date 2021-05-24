package br.com.lucas.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.lucas.cursospring.domain.Cliente;
import br.com.lucas.cursospring.repository.ClienteRepository;
import br.com.lucas.cursospring.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private ClienteRepository repo;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Cliente> cliente = repo.findByEmail(email);
		
		if (cliente.isEmpty()) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSpringSecurity(cliente.get().getId(), cliente.get().getEmail(), cliente.get().getSenha(), cliente.get().getPerfis());
	}
	
	

}
