package com.tarefa.Lembretes.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter @Setter
@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    @OneToMany
    private List<Lembrete> lembretes;
}
