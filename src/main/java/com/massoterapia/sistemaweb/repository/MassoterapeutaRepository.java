package com.massoterapia.sistemaweb.repository;

import com.massoterapia.sistemaweb.model.Massoterapeuta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MassoterapeutaRepository extends JpaRepository<Massoterapeuta, Integer> {

    Massoterapeuta findByUsuario(String usuario);

}
