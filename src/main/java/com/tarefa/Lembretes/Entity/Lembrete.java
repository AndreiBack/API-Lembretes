package com.tarefa.Lembretes.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_lembrete", schema = "public")
public class Lembrete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recado", nullable = false, length = 250)
    private String recado;

    @ManyToOne
    @JoinColumn(name = "pessoa_fk", nullable = false)
    @JsonBackReference
    private Pessoa pessoa;

    public Lembrete() {
    }

    public Lembrete(Long id, String recado, Pessoa pessoa) {
        this.id = id;
        this.recado = recado;
        this.pessoa = pessoa;
    }


}

