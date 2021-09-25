package br.com.diolabs.restful.controller;

import java.util.List;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.diolabs.restful.entity.Soldado;
import br.com.diolabs.restful.service.SoldadoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/v1/soldado")
public class SoldadoController {

    @Autowired
    SoldadoService soldadoService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<EntityModel<Soldado>> buscarTodosSoldados() {

        /*
        Link representa um recurso. Recurso provê detalhes sobre as informações bem como açõs sobre essas informações(alterar,ler,apagar)        
        */
        // Percorre todos as entidades e transforma-as em uma coleção de resource REST, utiliza o container
        // EntityModel<T> que permiter armazenar as informações da entidade e links adicionais
        List<EntityModel<Soldado>> soldados = StreamSupport.stream(soldadoService.findAll().spliterator(), false)
                .map(soldado -> EntityModel.of(soldado,
                        linkTo(methodOn(SoldadoController.class).buscarSoldado(soldado.getId())).withRel("soldados")))
                .collect(Collectors.toList());

        return CollectionModel.of(soldados,
                linkTo(methodOn(SoldadoController.class).buscarTodosSoldados()).withSelfRel());

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Soldado> buscarSoldado(@Valid @PathVariable Long id) {
        Soldado soldado = soldadoService.buscarSoldado(id);
        return EntityModel.of(soldado,
                linkTo(methodOn(SoldadoController.class).alterarSoldado(soldado.getId(),soldado)).withSelfRel(),
                linkTo(methodOn(SoldadoController.class).buscarTodosSoldados()).withRel("soldados"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Soldado criarSoldado(@Valid @RequestBody Soldado soldado) {
        return soldadoService.salvarSoldado(soldado);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Soldado> alterarSoldado(@Valid @PathVariable Long id, @Valid @RequestBody Soldado novoSoldado) {
        Soldado soldado = soldadoService.alterarSoldado(id,novoSoldado);
        return EntityModel.of(soldado,
                linkTo(methodOn(SoldadoController.class).buscarSoldado(soldado.getId())).withSelfRel(),
                linkTo(methodOn(SoldadoController.class).buscarTodosSoldados()).withRel("soldados"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarSoldado(@Valid @PathVariable Long id) {
        soldadoService.apagarSoldado(id);
    }

}
