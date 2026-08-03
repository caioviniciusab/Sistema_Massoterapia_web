package com.massoterapia.sistemaweb.repository;

import com.massoterapia.sistemaweb.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicoRepository
        extends JpaRepository<Servico, Integer> {

    Servico findByNomeservico(String nomeservico);

    Optional<Servico> findById(int id);

    boolean existsByNomeservicoIgnoreCase(String nomeservico);
}
