package br.com.lucas.cursospring.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.lucas.cursospring.services.validation.ClienteInsert;

@ClienteInsert
public class ClienteNewDTO  {

	@Size(min = 5, max = 120, message = "O Tamanho deve estar entre 5 e 80 caracteres.")
	@NotNull(message = "Preenchimento obrigatório")
	private String nome;
	@NotNull(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
	@NotNull(message = "Preenchimento obrigatório")
	private String cpfCnpj;
	private Integer tipo;
	@NotNull(message = "Preenchimento obrigatório")
	private String senha;
	
	@NotNull(message = "Preenchimento obrigatório")
	private String logradouro;
	@NotNull(message = "Preenchimento obrigatório")
	private String numero;
	private String complemento;
	@NotNull(message = "Preenchimento obrigatório")
	private String bairro;
	@NotNull(message = "Preenchimento obrigatório")
	private String cep; 
	
	@NotNull(message = "Preenchimento obrigatório")
	private String telefone1;
	private String telefone2;
	private String telefone3;
	
	private Integer cidadeId;
	
	public ClienteNewDTO() {
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

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
