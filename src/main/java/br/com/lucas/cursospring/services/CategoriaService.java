package br.com.lucas.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.cursospring.domain.Categoria;
import br.com.lucas.cursospring.repository.CategoriaRepository;
import br.com.lucas.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Optional<Categoria> busca(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		if (categoria.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado. | ID: " + id + " | Tipo: " + Categoria.class.getName());
		}
		
		return categoria;
	}
	
}