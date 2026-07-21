package com.massoterapia.sistemaweb.repository;

import com.massoterapia.sistemaweb.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository
        extends JpaRepository<Servico, Integer> {

    Servico findByNomeservico(String nomeservico);
}
