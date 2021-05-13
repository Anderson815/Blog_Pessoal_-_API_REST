package org.generation.blogpessoal.controller;

import java.util.List;

import org.generation.blogpessoal.model.Postagem;
import org.generation.blogpessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*") // qualquer origem de requizição(Angular, Node, Vue)
public class PostagemController {

	@Autowired
	public PostagemRepository repository;

	@ApiOperation(value = "Busca todas as postagens")
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@ApiOperation(value = "Busca uma postagem específica através de um ID")
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable(value = "id") long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound()
				.build());
	}
	
	@ApiOperation(value = "Busca todas as postagens através da parte semelhantes do título")
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getAllByTitulo(@PathVariable(value = "titulo") String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	//Criação
	
	@ApiOperation(value = "Faz uma postagem")
	@PostMapping
	public ResponseEntity<Postagem> post(@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	//Alteração
	
	@ApiOperation(value = "Altera uma postagem (deve ser informado o ID no body)")
	@PutMapping
	public ResponseEntity<Postagem> put(@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	//Deleção
	
	@ApiOperation(value = "Deleta uma postagem através de um ID")
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable(value = "id") long id){
		repository.deleteById(id);
	}
}
