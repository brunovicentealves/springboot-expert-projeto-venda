package io.github.brunovicentealves.repository;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente) {

        entityManager.persist(cliente);

        return cliente;
    }

    @Transactional
    public List<Cliente> obterTodos() {
        return entityManager.createQuery("from Cliente",Cliente.class).getResultList();
    }



    @Transactional
    public List<Cliente> buscarPorNome(String nome ){
        String jpql = "SELECT c FROM Cliente c WHERE c.nome like :nome ";
        TypedQuery<Cliente> query =entityManager.createQuery(jpql,Cliente.class);
        query.setParameter("nome","%" + nome +"%");
        return  query.getResultList();
    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar (Cliente cliente){
        if(! entityManager.contains(cliente)){
            cliente = entityManager.merge(cliente);
        }
       entityManager.remove(cliente);

    }
}
