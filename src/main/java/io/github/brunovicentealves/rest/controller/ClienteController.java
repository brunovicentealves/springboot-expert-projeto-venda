package io.github.brunovicentealves.rest.controller;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;


    @Autowired
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @GetMapping("/{id}")
    @ResponseBody
  public ResponseEntity<Cliente> getClienteById(@PathVariable("id")  Long id){
                   Optional<Cliente> cliente =clienteRepository.findById(id);
                   if(cliente.isPresent()){
                       return ResponseEntity.ok(cliente.get());
                   }
                   return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public  ResponseEntity save (@RequestBody Cliente cliente){
        Cliente cliente1 = clienteRepository.save(cliente);
        return ResponseEntity.ok(cliente1);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Long id ){
        Optional<Cliente> cliente =clienteRepository.findById(id);
        if(cliente.isPresent()){
           clienteRepository.delete(cliente.get());
           return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    //Atualizando todo cliente
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Long id , @RequestBody  Cliente cliente ){
    return clienteRepository.findById(id)
            .map( clienteExistente -> {
                cliente.setId(clienteExistente.getId());
                clienteRepository.save(cliente);

                return ResponseEntity.noContent().build();
            }).orElseGet(()-> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity find (Cliente filtro){
        /**
          Example -> org.springframework.data.domain


         **/

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example =  Example.of(filtro,matcher);

        List<Cliente> clientes = clienteRepository.findAll(example);

        return  ResponseEntity.ok(clientes);
    }


}
