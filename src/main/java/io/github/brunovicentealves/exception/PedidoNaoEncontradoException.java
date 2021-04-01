package io.github.brunovicentealves.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException() {
        super("Pedido não Encontrado !");
    }
}
