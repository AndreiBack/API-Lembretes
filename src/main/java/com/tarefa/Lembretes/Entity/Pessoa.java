package com.tarefa.Lembretes.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import java.util.List;

@Getter
@Setter
@Table(name = "tb_pessoa", schema = "public")
@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @OneToMany
    @JoinColumn(name = "idLembrete")
    @JsonManagedReference
    private List<Lembrete> lembretes;

    public Pessoa() {
    }

    public Pessoa(String nome, List<Lembrete> lembretes) {
        this.nome = nome;
        this.lembretes = lembretes;
    }

    public Pessoa(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
