package com.massoterapia.sistemaweb.service;

import com.massoterapia.sistemaweb.model.Massoterapeuta;
import com.massoterapia.sistemaweb.repository.MassoterapeutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MassoterapeutaService {

    @Autowired
    private MassoterapeutaRepository massoterapeutaRepository;


    public boolean autenticar(String usuario, String senha) {

        Massoterapeuta massoterapeuta = massoterapeutaRepository.findByUsuario(usuario);

        if(massoterapeuta == null){
            return false;
        }

        return massoterapeuta.getSenha().equals(senha);
    }
}
