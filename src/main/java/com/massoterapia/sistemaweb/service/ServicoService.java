package com.massoterapia.sistemaweb.service;

import com.massoterapia.sistemaweb.exception.AgendamentoException;
import com.massoterapia.sistemaweb.model.Servico;
import com.massoterapia.sistemaweb.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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


    public void salvar(String nomeServico, BigDecimal preco) {

        String nomeTratado = nomeServico.trim();

        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AgendamentoException(
                    "O preço deve ser maior que zero."
            );
        }

        if (servicoRepository.existsByNomeservicoIgnoreCase(nomeTratado)) {
            throw new AgendamentoException(
                    "Já existe um serviço com esse nome."
            );
        }

        Servico servico = new Servico();

        servico.setNomeServico(nomeTratado);
        servico.setPreco(preco);

        servicoRepository.save(servico);
    }

    public void editarServico(Integer id, String nomeServico,  BigDecimal preco) {

        Servico servico = servicoRepository.findById(id).orElseThrow(() -> new AgendamentoException("Serviço não encontrado."));

        String nomeTratado = nomeServico.trim();

        if(nomeTratado.isBlank()){
            throw new AgendamentoException("O nome do serviço é obrigatório.");
        }

        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AgendamentoException(
                    "O preço deve ser maior que zero."
            );
        }

        servico.setNomeServico(nomeTratado);
        servico.setPreco(preco);

        servicoRepository.save(servico);
    }

    public void excluirServico(Integer id) {

        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() ->
                        new AgendamentoException(
                                "Serviço não encontrado."
                        )
                );

        servicoRepository.delete(servico);
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