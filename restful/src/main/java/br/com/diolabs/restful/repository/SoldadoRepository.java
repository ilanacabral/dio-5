package br.com.diolabs.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.diolabs.restful.entity.Soldado;

@Repository
public interface SoldadoRepository extends JpaRepository<Soldado, Long> {
    
}
