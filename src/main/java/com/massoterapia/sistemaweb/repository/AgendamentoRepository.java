package com.massoterapia.sistemaweb.repository;

import com.massoterapia.sistemaweb.model.Agendamentos;
import com.massoterapia.sistemaweb.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamentos, Integer> {

    boolean existsByData(LocalDateTime data);

    Long countByStatus(String status);

    Long countByDataBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Agendamentos> findByDataBetweenOrderByDataAsc(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    Optional<Agendamentos> findById(
            Integer id
    );

    List<Agendamentos> findByDataBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<Agendamentos> findByClienteTelefone(
            String telefone
    );

    List<Agendamentos> findByCliente(Cliente cliente);

    long countByStatusAndDataBetween(String status,
                                     LocalDateTime inicio,
                                     LocalDateTime fim);
}
