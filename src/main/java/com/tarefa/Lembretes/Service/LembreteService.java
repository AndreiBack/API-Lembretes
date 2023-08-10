package com.tarefa.Lembretes.Service;

import com.tarefa.Lembretes.Entity.Lembrete;
import com.tarefa.Lembretes.Entity.Pessoa;
import com.tarefa.Lembretes.Repository.LembreteRepository;
import com.tarefa.Lembretes.Repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LembreteService {

    private  final LembreteRepository lembreteRepository;
    private  final PessoaService pessoaService;

    private  final PessoaRepository pessoaRepository ;


    @Autowired
    public LembreteService(LembreteRepository lembreteRepository, PessoaService pessoaService, PessoaRepository pessoaRepository) {
        this.lembreteRepository = lembreteRepository;
        this.pessoaService = pessoaService;
        this.pessoaRepository = pessoaRepository;
    }

    public Lembrete createLembrete(Long pessoaId, Lembrete lembrete) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElse(null);
        if (pessoa != null) {
            lembrete.setPessoa(pessoa);
            return lembreteRepository.save(lembrete);
        }
        return null;
    }

    public Lembrete getLembreteById(Long id) {
        return lembreteRepository.findById(id).orElse(null);
    }
}
