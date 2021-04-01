package controller;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.repository.ClienteRepository;
import io.github.brunovicentealves.rest.controller.ClienteController;
import io.github.brunovicentealves.service.ClienteService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = ClienteController.class)
@AutoConfigureMockMvc
public class ClienteTesteControllers  {

    @Autowired
    private ClienteController clienteController ;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void Setup(){
    RestAssuredMockMvc.standaloneSetup(this.clienteController);


    }

   @Test
   public void deveRetornarSucesso_QuandoBuscarCliente(){

       when(this.clienteRepository.findById(1l))
               .thenReturn(java.util.Optional.of(new Cliente("bruno", "9999999999")));

       given().accept(ContentType.JSON)
               .when()
               .get("/api/clientes/{id}",1l)
               .then()
               .statusCode(HttpStatus.OK.value());
   }


    @Test
    public void deveRetornarNãoEncontrado_QuandoBuscarCliente(){

        when(this.clienteRepository.findById(1l))
                .thenReturn(java.util.Optional.of(new Cliente("bruno", "9999999999")));

        given().accept(ContentType.JSON)
                .when()
                .get("/api/clientes/{id}",2l)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Test
    public void deveRetronarSucesso_QuandoSalvarCliente(){


       Cliente cliente =  new Cliente("bruno","99999999999");
        when(this.clienteRepository.save(cliente)).thenReturn(cliente);


        given().accept(ContentType.JSON)
                .when()
                .post("/api/clientes")
                .then()
                .statusCode(HttpStatus.OK.value());


    }


}
