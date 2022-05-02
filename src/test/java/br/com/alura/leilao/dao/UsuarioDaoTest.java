package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

public class UsuarioDaoTest {

    private UsuarioDao usuarioDao;
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        em = JPAUtil.getEntityManager();
        usuarioDao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveEncontrarUsuarioCadastradoPeloNome() {
        Usuario novoUsuario = criarUsuario();
        Usuario usuarioEncontrado = usuarioDao.buscarPorUsername(novoUsuario.getNome());
        assertNotNull(usuarioEncontrado);
    }

    @Test
    void naoDeveEncontrarUsuarioNaoCadastradoPeloNome() {
        criarUsuario();
        assertThrows(NoResultException.class, () -> usuarioDao.buscarPorUsername("beltrano"));

    }

    private Usuario criarUsuario() {
        Usuario novoUsuario = new Usuario("fulano", "fulano@example.com", "12345678");
        em.persist(novoUsuario);

        return novoUsuario;
    }

}
