package com.massoterapia.sistemaweb.service;

import com.massoterapia.sistemaweb.exception.AgendamentoException;
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


    public void salvar(String nomeServico) {

        String nomeTratado = nomeServico.trim();

        if (servicoRepository.existsByNomeservicoIgnoreCase(nomeTratado)) {
            throw new AgendamentoException(
                    "Já existe um serviço com esse nome."
            );
        }

        Servico servico = new Servico();

        servico.setNomeServico(nomeTratado);

        servicoRepository.save(servico);
    }

    public void editarServico(Integer id, String nomeServico) {

        Servico servico = servicoRepository.findById(id).orElseThrow(() -> new AgendamentoException("Serviço não encontrado."));

        String nomeTratado = nomeServico.trim();

        if(nomeTratado.isBlank()){
            throw new AgendamentoException("O nome do serviço é obrigatório.");
        }

        servico.setNomeServico(nomeTratado);

        servicoRepository.save(servico);
    }


    public Servico buscarPorId(Integer id) {

        return servicoRepository.findById(id)
                .orElseThrow(() ->
                        new AgendamentoException(
                                "Serviço não encontrado."
                        )
                );
    }

}