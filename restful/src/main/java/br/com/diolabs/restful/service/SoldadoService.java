package br.com.diolabs.restful.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diolabs.restful.entity.Soldado;
import br.com.diolabs.restful.exception.SoldadoBadRequestException;
import br.com.diolabs.restful.exception.SoldadoNotFoundException;
import br.com.diolabs.restful.repository.SoldadoRepository;

@Service
public class SoldadoService {

    @Autowired SoldadoRepository repository;
    
    public Soldado buscarSoldado(Long id){
        Optional<Soldado> optSoldado = repository.findById(id);
        if (optSoldado.isPresent()) return optSoldado.get();
        throw new SoldadoNotFoundException();        
    }

    public List<Soldado> findAll() {
        List<Soldado> soldados = repository.findAll();
        if (soldados.isEmpty())  throw new SoldadoNotFoundException(); 
        return soldados;          
    }

    public Soldado salvarSoldado(Soldado soldado) {
        try {
            return repository.save(soldado);
        } catch (Exception e) {
           throw new SoldadoBadRequestException(e.getMessage());
        }        
    }

    public Soldado alterarSoldado(Long id, Soldado novoSoldado) {
        Soldado soldado = this.buscarSoldado(id);
        soldado.setCpf(novoSoldado.getCpf());
        soldado.setNome(novoSoldado.getNome());
        soldado.setRaca(novoSoldado.getRaca());
        soldado.setRaca(novoSoldado.getRaca());
        soldado.setStatus(novoSoldado.getStatus());
        return this.salvarSoldado(soldado);
    }

    public void apagarSoldado(Long id) {

        Soldado soldado = this.buscarSoldado(id);
        try {
             repository.delete(soldado);
        } catch (Exception e) {
           throw new SoldadoBadRequestException(e.getMessage());
        }   
    }
}
