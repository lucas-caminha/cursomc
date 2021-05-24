package br.com.lucas.cursospring.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EmailDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Email(message = "E-mail inválido.")
	private String email;
	
	public EmailDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
