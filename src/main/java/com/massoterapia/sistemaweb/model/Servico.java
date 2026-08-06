package com.massoterapia.sistemaweb.model;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomeservico;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
