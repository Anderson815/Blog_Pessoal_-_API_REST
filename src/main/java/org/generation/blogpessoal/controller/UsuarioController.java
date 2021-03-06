package org.generation.blogpessoal.controller;

import java.util.Optional;

import org.generation.blogpessoal.model.UserLogin;
import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.generation.blogpessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
    private UsuarioRepository repository;
	
	@ApiOperation(value = "Busca um usuário pelo ID")
	
	@GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Esse usuário não existe na plataformaa")
        })
    public ResponseEntity<Usuario> GetById(@PathVariable long id) {
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
    }
	
	@ApiOperation(value = "Cadastra um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "O usuário foi cadastrado"),
            @ApiResponse(code = 400, message = "Esse usuário já existe na plataforma")
        })
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Usuario> post(@RequestBody Usuario usuario){
		Optional<Usuario> user = usuarioService.cadastrarUsuario(usuario);
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(user.get());
		}catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@ApiOperation(value = "Loga o usuário na plataforma")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Esse usuário não existe na plataformaa")
        })
	@PostMapping(value = "/logar", produces = "application/json")
	public ResponseEntity<UserLogin> autentication(@RequestBody Optional<UserLogin> user){
		return usuarioService.logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@ApiOperation(value = "Atualiza o registro do usuário")
    @ApiResponse(code = 200, message = "OK")
	@PutMapping(value = "/atualizar", produces = "application/json")
	public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.atualizarUsuario(usuario));
	}
	
}
