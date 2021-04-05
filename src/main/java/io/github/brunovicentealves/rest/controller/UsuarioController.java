package io.github.brunovicentealves.rest.controller;

import io.github.brunovicentealves.exception.SenhaInvalidaException;
import io.github.brunovicentealves.jwt.JwtService;
import io.github.brunovicentealves.model.domain.entity.Usuario;
import io.github.brunovicentealves.rest.dto.CredenciaisDto;
import io.github.brunovicentealves.rest.dto.TokenDTO;
import io.github.brunovicentealves.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {


    private  final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid  Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

         return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticarUsuario ( @RequestBody  CredenciaisDto credenciaisDto){
    try {

       Usuario usuario = Usuario.builder().
                login(credenciaisDto.getLogin())
                .senha(credenciaisDto.getSenha())
                .build();

       UserDetails userDetails =  usuarioService.autenticarUsuario(usuario);

      String token= jwtService.gerartoken(usuario);

       return  new TokenDTO(usuario.getLogin(),token);

        }catch (UsernameNotFoundException | SenhaInvalidaException e ){
        throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());

          }
    }


}
