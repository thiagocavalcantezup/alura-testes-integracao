package br.com.alura.leilao.util.builder;

import java.math.BigDecimal;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Usuario;

public class LanceBuilder {

    private Usuario usuario;
    private BigDecimal valor;

    public LanceBuilder usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public LanceBuilder valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Lance criar() {
        return new Lance(usuario, valor);
    }

}
