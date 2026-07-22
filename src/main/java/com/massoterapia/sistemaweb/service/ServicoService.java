package com.massoterapia.sistemaweb.service;

import com.massoterapia.sistemaweb.model.Servico;
import com.massoterapia.sistemaweb.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> listarTodos() {

        return servicoRepository.findAll();

    }

    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }

}