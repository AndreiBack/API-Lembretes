package com.tarefa.Lembretes.Service;

import com.tarefa.Lembretes.DTO.PessoaDTO;
import com.tarefa.Lembretes.Entity.Pessoa;
import com.tarefa.Lembretes.Repository.LembreteRepository;
import com.tarefa.Lembretes.Repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import org.springframework.util.Assert;

import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa findById(final Long id) {
        return this.pessoaRepository.findById(id).orElseThrow();
    }

    public Pessoa findByName(final String nome) {
        return this.pessoaRepository.findByNome(nome);
    }

    public List<Pessoa> findAll() {
        return this.pessoaRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(PessoaDTO pessoaDTO) {
        Assert.isTrue(!pessoaDTO.getNome().isBlank(), "Nome não pode ser nulo!");

        pessoaRepository.save(this.toPessoa(pessoaDTO));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, PessoaDTO pessoaDTO) {
        PessoaDTO pessoaDatabase = toPessoaDTO(findById(id));
        Assert.notNull(pessoaDatabase, "Pessoa não encontrado!");
        Assert.isTrue(pessoaDatabase.getId().equals(pessoaDTO.getId()), "Pessoas não conferem!");
        Assert.isTrue(!pessoaDTO.getNome().isBlank(), "Nome não pode ser nulo!");

        pessoaRepository.save(toPessoa(pessoaDTO));
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        PessoaDTO pessoaDTO = toPessoaDTO(findById(id));
        Assert.notNull(pessoaDTO, "Pessoa não encontrado!");

        pessoaRepository.delete(toPessoa(pessoaDTO));
    }

    public Pessoa toPessoa(PessoaDTO pessoaDTO) {
        return new Pessoa(pessoaDTO.getId(), pessoaDTO.getNome());
    }

    public PessoaDTO toPessoaDTO(Pessoa pessoa) {
        return new PessoaDTO(pessoa.getId(), pessoa.getNome());
    }
}