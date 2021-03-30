package io.github.brunovicentealves.repository;
import io.github.brunovicentealves.model.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {


    List<io.github.brunovicentealves.model.domain.entity.Cliente> findByNomeLike(String nome);

    boolean existsByNome(String nome);


    @Query("select c from Cliente c left join fetch c.pedidos  where c.id = :id")
    ClienteRepository findClienteFetchPedidos(@Param("id") Long id);


}
