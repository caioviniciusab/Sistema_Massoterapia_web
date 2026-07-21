package com.massoterapia.sistemaweb.model;


import jakarta.persistence.*;

@Entity
@Table(name = "servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomeservico;

    public Servico() {

    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeServico() {
        return nomeservico;
    }

    public void setNomeServico(String nomeservico) {
        this.nomeservico = nomeservico;
    }
}
