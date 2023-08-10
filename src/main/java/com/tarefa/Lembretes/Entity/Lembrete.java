package com.tarefa.Lembretes.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter @Setter
public class Lembrete {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String recado;

    @ManyToOne
    private Pessoa pessoa;

}
