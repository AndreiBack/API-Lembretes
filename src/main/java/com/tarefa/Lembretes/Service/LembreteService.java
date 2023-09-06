package com.tarefa.Lembretes.Service;

import com.tarefa.Lembretes.DTO.LembreteDTO;
import com.tarefa.Lembretes.Entity.Lembrete;
import com.tarefa.Lembretes.Entity.Pessoa;
import com.tarefa.Lembretes.Repository.LembreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class LembreteService {
    @Autowired
    private LembreteRepository lembreteRepository;

    @Autowired
    private PessoaService pessoaService;

    public Lembrete findById(final Long id) {
        return this.lembreteRepository.findById(id).orElseThrow();
    }

    public List<Lembrete> findAll() {
        return this.lembreteRepository.findAll();
    }

    public List<Lembrete> findAllOfPessoa(final String nome) {
        return this.lembreteRepository.findByPessoa(pessoaService.findByName(nome).getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(LembreteDTO lembreteDTO) {
        Assert.notNull(lembreteDTO.getPessoa(), "Lembrete deve ser vinculado a uma pessoa!");
        Assert.notNull(lembreteDTO.getRecado(), "O campo 'recado' não pode ser nulo!");

        // Verifique se a pessoa associada ao lembrete existe
        Pessoa pessoa = pessoaService.findById(lembreteDTO.getPessoa().getId());

        lembreteRepository.save(toLembrete(lembreteDTO));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, LembreteDTO lembreteDTO) {
        LembreteDTO lembreteDatabase = toLembreteDTO(findById(id));
        Assert.notNull(lembreteDatabase, "Lembrete não encontrado!");
        Assert.isTrue(lembreteDatabase.getId().equals(lembreteDTO.getId()), "Lembretes não conferem!");
        Assert.isTrue(!lembreteDTO.getRecado().isBlank(), "Mensagem não pode ser nula!");
        Assert.notNull(lembreteDTO.getPessoa(), "Lembrete deve ser vinculado a uma pessoa!");

        lembreteRepository.save(toLembrete(lembreteDTO));
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        LembreteDTO lembreteDTO = toLembreteDTO(findById(id));
        Assert.notNull(lembreteDTO, "Lembrete não encontrado!");

        lembreteRepository.delete(toLembrete(lembreteDTO));
    }

    public Lembrete toLembrete(LembreteDTO lembreteDTO) {
        return new Lembrete(lembreteDTO.getId(), lembreteDTO.getRecado(), pessoaService.toPessoa(lembreteDTO.getPessoa()));
    }

    public LembreteDTO toLembreteDTO(Lembrete lembrete) {
        return new LembreteDTO(lembrete.getId(), lembrete.getRecado(), pessoaService.toPessoaDTO(lembrete.getPessoa()));
    }
}