package br.com.lucas.cursospring.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.lucas.cursospring.domain.Cliente;
import br.com.lucas.cursospring.domain.enums.TipoCliente;
import br.com.lucas.cursospring.dto.ClienteNewDTO;
import br.com.lucas.cursospring.repository.ClienteRepository;
import br.com.lucas.cursospring.resources.exception.FieldMessage;
import br.com.lucas.cursospring.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepo;

	@Override
	public void initialize(ClienteInsert constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO cliDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<FieldMessage>();
		
		if (cliDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(cliDto.getCpfCnpj())) {
			list.add(new FieldMessage("cpfCnpj", "CPF Inválido"));
		}
		
		if (cliDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(cliDto.getCpfCnpj())) {
			list.add(new FieldMessage("cpfCnpj", "CNPJ Inválido"));
		}
		
		Optional<Cliente> clienteEmail = clienteRepo.findByEmail(cliDto.getEmail());
		 
		if (clienteEmail.isPresent() && clienteEmail.get().getEmail() != null) {
			list.add(new FieldMessage("email", "E-mail já existente."));
		}
		
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return list.isEmpty();
	}
 
	
	
}
