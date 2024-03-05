package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Curso Spring Boot API: " + name + "!";
	}

	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuarioRepository.save(usuario);
		return "Olá Mundo " + nome;
	}

	@GetMapping(value = "listartodos")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Usuario>> listaUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
	@PostMapping(value = "salvar")
	@ResponseBody
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		Usuario user = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "deletar")
	@ResponseBody
	public ResponseEntity<String> deletar(@RequestParam Long idUser) {
		usuarioRepository.deleteById(idUser);
		return new ResponseEntity<String>("Usuário deletado com sucesso!", HttpStatus.OK);
	}
	
	
	@GetMapping(value = "buscaruserid")
	@ResponseBody
	public ResponseEntity<Usuario> buscarPorId(@RequestParam("idUser") Long idUser) {
		Usuario usuario = usuarioRepository.findById(idUser).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@PutMapping(value = "atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
		
		if(usuario.getId() == null) {
			return new ResponseEntity<String>("Id não informado para atualização!", HttpStatus.OK);
		}
		
		Usuario u = usuarioRepository.findById(usuario.getId()).orElse(null);
		
		if(u == null) {
			return new ResponseEntity<String>("Usuário não encontrado com o Id informado para atualização!", HttpStatus.OK);
		}
		
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	@GetMapping(value = "buscarPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam("nome") String nome) {
		List<Usuario> usuarios = usuarioRepository.buscaPorNome(nome.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

}