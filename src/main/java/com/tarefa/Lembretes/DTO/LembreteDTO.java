package com.tarefa.Lembretes.DTO;

import lombok.Getter;
import lombok.Setter;

public class LembreteDTO {
    @Getter
    @Setter
    private Long id;

    @Getter @Setter
    private String recado;

    @Getter @Setter
    private PessoaDTO pessoa;

    public LembreteDTO(){}

    public LembreteDTO(Long id, String recado, PessoaDTO pessoa){
        this.id = id;
        this.recado = recado;
        this.pessoa = pessoa;
    }
}