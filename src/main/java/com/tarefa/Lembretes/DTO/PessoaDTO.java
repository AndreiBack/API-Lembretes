package com.tarefa.Lembretes.DTO;

import lombok.Getter;
import lombok.Setter;

public class PessoaDTO {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String nome;

    public PessoaDTO(){}

    public PessoaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
