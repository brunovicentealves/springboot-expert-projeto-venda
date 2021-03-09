package io.github.brunovicentealves;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.repository.ClienteRepository;
import io.github.brunovicentealves.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init (@Autowired ClienteRepository clienteRepository){
        return args->{
            System.out.println("SALVANDO CLIENTES");
          clienteRepository.salvar(new Cliente("Douglas"));
            clienteRepository.salvar(new Cliente("pedro"));

            System.out.println("BUSCANDO CLIENTES");
          List<Cliente> todosCliente =   clienteRepository.obterTodos();
          todosCliente.forEach(System.out::println);


        System.out.println("ATUALIZANDO CLIENTES");
            todosCliente.forEach(c-> {
                c.setNome(c.getNome()+"Atualizado");
                clienteRepository.atualizar(c);

            });

            List<Cliente> todosCliente1 =   clienteRepository.obterTodos();
            todosCliente.forEach(System.out::println);

            System.out.println("OBTER PELO NOME ");
            clienteRepository.buscarPorNome("dro").forEach(System.out::println);

            System.out.println("DELETANDO CLIENTE ");
            clienteRepository.obterTodos().forEach(c->{
                clienteRepository.deletar(c);
            });

            todosCliente = clienteRepository.obterTodos();
            if (todosCliente.isEmpty()){

                System.out.println("Nenhum cliente emcontrado");
            }else{
                todosCliente.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class,args);
    }
}
