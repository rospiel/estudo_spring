package com.estudo.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.brewer.model.Estilo;

@Repository
public interface Estilos extends JpaRepository<Estilo, Long>{

}
