package br.com.lucas.cursospring.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.lucas.cursospring.domain.Cliente;
import br.com.lucas.cursospring.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@Size(min = 5, max = 120, message = "O Tamanho deve estar entre 5 e 80 caracteres.")
	@NotNull(message = "Preenchimento obrigatório")
	private String nome;
	@NotNull
	@Email(message = "E-mail inválido.")
	private String email;
	
	public ClienteDTO() {
	}
	
	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
