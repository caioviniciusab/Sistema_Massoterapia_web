package com.massoterapia.sistemaweb.service;

import com.massoterapia.sistemaweb.model.Massoterapeuta;
import com.massoterapia.sistemaweb.repository.MassoterapeutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MassoterapeutaService {

    @Autowired
    private MassoterapeutaRepository massoterapeutaRepository;

    private final PasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public boolean autenticar(String usuario, String senha) {

        Massoterapeuta massoterapeuta =
                massoterapeutaRepository.findByUsuario(usuario);

        if (massoterapeuta == null) {
            return false;
        }

        return passwordEncoder.matches(
                senha,
                massoterapeuta.getSenha()
        );
    }
}