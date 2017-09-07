package com.estudo.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.brewer.model.Cerveja;

/**
 * Classe responsável por criar entidades do banco em objetos, estamos extendendo JpaRepository
 * para aproveitar os inúmeros métodos de acesso a dados
 * 
 * Requer informar qual classe devemos buscar bem como o tipo da primary key
 * @author Rodrigo
 *
 */

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long>{
	
	public Optional<Cerveja> findBySku(String sku);
}
