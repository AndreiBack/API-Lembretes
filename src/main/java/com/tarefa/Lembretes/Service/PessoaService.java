package com.tarefa.Lembretes.Service;

import com.tarefa.Lembretes.Entity.Pessoa;
import com.tarefa.Lembretes.Repository.LembreteRepository;
import com.tarefa.Lembretes.Repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final LembreteRepository lembreteRepository;


    public PessoaService(PessoaRepository pessoaRepository, LembreteRepository lembreteRepository) {
        this.pessoaRepository = pessoaRepository;
        this.lembreteRepository = lembreteRepository;
    }

    public Pessoa createPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa getPessoaById(Long id) {
        return pessoaRepository.findById(id).orElse(null);
    }

    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa getLembreteByNome(String nome) {
        Pessoa pessoa = pessoaRepository.findByNome(nome);
        if (pessoa != null) {
            return new Pessoa(pessoa.getNome(), pessoa.getLembretes());
        }
        return null;
    }


}