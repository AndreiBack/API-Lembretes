package com.tarefa.Lembretes.Controller;

import com.tarefa.Lembretes.Entity.Pessoa;
import com.tarefa.Lembretes.Service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService personService) {
        this.pessoaService = personService;
    }

    @GetMapping("/{id}")

    public Pessoa getPersonById(@PathVariable Long id) {
        return pessoaService.getPessoaById(id);
    }

    @GetMapping
    public List<Pessoa> getAllPeople() {
        return pessoaService.getAll();
    }

    @GetMapping("/lembretes/{name}")
    public Pessoa GetPessoa(@PathVariable String name) {
        return pessoaService.getLembreteByNome(name);
    }

    @PostMapping
    public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaService.createPessoa(pessoa);
    }


}