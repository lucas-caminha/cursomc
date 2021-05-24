package br.com.lucas.cursospring.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.lucas.cursospring.security.UserSpringSecurity;

public class UserService {

	public static UserSpringSecurity authenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
