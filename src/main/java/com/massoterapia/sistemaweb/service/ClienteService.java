package com.massoterapia.sistemaweb.service;

import com.massoterapia.sistemaweb.model.Cliente;
import com.massoterapia.sistemaweb.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listartodos() {
        return clienteRepository.findAll();
    }

    public List<Cliente> pesquisar(String busca){
        if(busca == null || busca.isBlank()){
            return clienteRepository.findAll();
        }

        String buscaTratada = busca.trim();

        if (buscaTratada.matches("\\d+")) {
            return clienteRepository.findByTelefoneContaining(buscaTratada);
        }

        return clienteRepository.findByNomeContainingIgnoreCase(buscaTratada);
    }
}
