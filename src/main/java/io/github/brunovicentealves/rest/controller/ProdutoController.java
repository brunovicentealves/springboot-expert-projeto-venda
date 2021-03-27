package io.github.brunovicentealves.rest.controller;

import io.github.brunovicentealves.model.domain.entity.Produto;
import io.github.brunovicentealves.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private ProdutosRepository  produtosRepository ;

    @Autowired
    public ProdutoController(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody Produto produto){
       return produtosRepository.save(produto);
    }

    @GetMapping("{id}")
    public Produto getProdutoById(@PathVariable  Integer id ){

        return produtosRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "Produto Não Encontrado !"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduto(@PathVariable Integer id){
        produtosRepository.findById(id)
                .map(produto -> {
                    produtosRepository.delete(produto);
                    return Void.TYPE;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"produto não encontrado"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@PathVariable Integer id , @RequestBody Produto produto){
        produtosRepository.findById(id).map(produto1 ->{
            produto.setId(produto1.getId());
            produtosRepository.save(produto);
            return produto;
        } ).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @GetMapping
    public List<Produto>   find (Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro,matcher);

        return  produtosRepository.findAll(example);

    }
}
