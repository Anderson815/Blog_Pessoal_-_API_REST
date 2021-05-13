package org.generation.blogpessoal.controller;

import java.util.List;

import org.generation.blogpessoal.model.Tema;
import org.generation.blogpessoal.repository.TemaRepository;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/tema")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {
	
	@Autowired
	private TemaRepository repository;
	
	@ApiOperation(value = "Busca todos os temas")
	@GetMapping
	public ResponseEntity<List<Tema>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@ApiOperation(value = "Busca um tema específico através de um ID")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "O ID não existe"),
            @ApiResponse(code = 401, message = "Você precisa de token para fazer essa requisição")
        })
	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable(value = "id") long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation(value = "Busca todos os temas através da parte semelhante do nome")
	@ApiResponse(code = 401, message = "Você precisa de token para fazer essa requisição")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> getAllByNome(@PathVariable(value = "nome") String nome){
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(nome));
	}
	
	@ApiOperation(value = "Faz um tema")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tema feito"),
            @ApiResponse(code = 401, message = "Você precisa de token para fazer essa requisição")
        })
	@PostMapping
	public ResponseEntity<Tema> post(@RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema));
	}
	
	@ApiOperation(value = "Altera um tema (deve ser informado o ID no body)")
	@ApiResponse(code = 401, message = "Você precisa de token para fazer essa requisição")
	@PutMapping
	public ResponseEntity<Tema> put(@RequestBody Tema tema){
		return ResponseEntity.ok(repository.save(tema));
	}
	
	@ApiOperation(value = "Deleta um tema através de um ID. OBS: Todas as postagens referentes ao tema serão deletadas também")
	@ApiResponse(code = 401, message = "Você precisa de token para fazer essa requisição")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(value = "id") long id) {
		repository.deleteById(id);
	}
}
