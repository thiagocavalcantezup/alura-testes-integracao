package br.com.alura.leilao.util.builder;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

public class LeilaoBuilder {

    private String nome;
    private BigDecimal valorInicial;
    private LocalDate dataAbertura;
    private Usuario usuario;

    public LeilaoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public LeilaoBuilder valorInicial(String valorInicialStr) {
        this.valorInicial = new BigDecimal(valorInicialStr);
        return this;
    }

    public LeilaoBuilder dataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
        return this;
    }

    public LeilaoBuilder usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Leilao criar() {
        return new Leilao(nome, valorInicial, dataAbertura, usuario);
    }

}
