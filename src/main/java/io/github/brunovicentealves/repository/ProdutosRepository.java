package io.github.brunovicentealves.repository;

import io.github.brunovicentealves.model.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosRepository extends JpaRepository<Produto,Integer> {
}
