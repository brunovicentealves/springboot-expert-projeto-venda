package io.github.brunovicentealves.rest.controller;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;


    @Autowired
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @GetMapping("/{id}")
  public Cliente getClienteById(@PathVariable("id")  Integer id){
                   return clienteRepository.findById(id)
                           .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Não encontrou o cliente !"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save (@RequestBody Cliente cliente){
         return clienteRepository.save(cliente);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id ){
        clienteRepository.findById(id).map(cliente -> {
            clienteRepository.delete(cliente);
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));
    }

    //Atualizando todo cliente
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  update(@PathVariable Integer id , @RequestBody  Cliente cliente ){
     clienteRepository.findById(id)
            .map( clienteExistente -> {
                cliente.setId(clienteExistente.getId());
                clienteRepository.save(cliente);
                return cliente;

            }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));

    }

    @GetMapping
    public List<Cliente> find (Cliente filtro){
        /**
          Example -> org.springframework.data.domain
         **/

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example =  Example.of(filtro,matcher);

      return clienteRepository.findAll(example);

    }


}
