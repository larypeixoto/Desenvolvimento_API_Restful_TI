package org.serratec.TrabalhoIndividual.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.TrabalhoIndividual.domain.Funcionario;
import org.serratec.TrabalhoIndividual.repository.FuncionarioRepository;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@GetMapping
	public ResponseEntity<List<Funcionario>> listar() {
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		return ResponseEntity.ok(funcionarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Funcionario> buscar(@PathVariable Long id) {
		Optional<Funcionario> funcionariosOpt = funcionarioRepository.findById(id);
		if (funcionariosOpt.isPresent()) {
			Funcionario funcionario = funcionariosOpt.get();
			return ResponseEntity.ok(funcionario);
		}
		return ResponseEntity.notFound().build();

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Funcionario inserir(@RequestBody @Valid Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Funcionario> atualizar(@PathVariable Long id, @Valid @RequestBody Funcionario funcionario) {
		boolean funcionarioExists = funcionarioRepository.existsById(id);
		if (funcionarioExists) {
			funcionario.setId(id);

			funcionarioRepository.save(funcionario);
			return ResponseEntity.ok(funcionario);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		boolean funcionarioExists = funcionarioRepository.existsById(id);
		if (funcionarioExists) {
			funcionarioRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
