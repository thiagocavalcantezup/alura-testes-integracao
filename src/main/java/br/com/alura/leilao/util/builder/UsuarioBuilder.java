package br.com.alura.leilao.util.builder;

import br.com.alura.leilao.model.Usuario;

public class UsuarioBuilder {

    private String nome;
    private String senha;
    private String email;

    public UsuarioBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public UsuarioBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UsuarioBuilder senha(String senha) {
        this.senha = senha;
        return this;
    }

    public Usuario criar() {
        return new Usuario(nome, email, senha);
    }

}
