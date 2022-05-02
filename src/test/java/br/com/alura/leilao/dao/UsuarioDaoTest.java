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
import br.com.alura.leilao.util.builder.UsuarioBuilder;

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
    void deveriaEncontrarUsuarioCadastradoPeloNome() {
        Usuario novoUsuario = new UsuarioBuilder().nome("fulano")
                                                  .email("fulano@example.com")
                                                  .senha("12345678")
                                                  .criar();
        em.persist(novoUsuario);
        Usuario usuarioEncontrado = usuarioDao.buscarPorUsername(novoUsuario.getNome());
        assertNotNull(usuarioEncontrado);
    }

    @Test
    void naoDeveriaEncontrarUsuarioNaoCadastradoPeloNome() {
        Usuario novoUsuario = new UsuarioBuilder().nome("fulano")
                                                  .email("fulano@example.com")
                                                  .senha("12345678")
                                                  .criar();
        em.persist(novoUsuario);
        assertThrows(NoResultException.class, () -> usuarioDao.buscarPorUsername("beltrano"));

    }

    @Test
    void deveriaRemoverUmUsuario() {
        Usuario novoUsuario = new UsuarioBuilder().nome("fulano")
                                                  .email("fulano@example.com")
                                                  .senha("12345678")
                                                  .criar();
        em.persist(novoUsuario);
        usuarioDao.deletar(novoUsuario);
        assertThrows(
            NoResultException.class, () -> usuarioDao.buscarPorUsername(novoUsuario.getNome())
        );
    }

}
