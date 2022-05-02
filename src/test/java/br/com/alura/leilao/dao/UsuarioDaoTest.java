package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

public class UsuarioDaoTest {

    private UsuarioDao usuarioDao;

    @Test
    void deveEncontrarUsuarioCadastradoPeloNome() {
        EntityManager em = JPAUtil.getEntityManager();

        Usuario novoUsuario = new Usuario("fulano", "fulano@example.com", "12345678");
        em.getTransaction().begin();
        em.persist(novoUsuario);
        em.getTransaction().commit();

        usuarioDao = new UsuarioDao(em);
        Usuario usuarioEncontrado = usuarioDao.buscarPorUsername(novoUsuario.getNome());

        assertNotNull(usuarioEncontrado);
    }

    @Test
    void naoDeveEncontrarUsuarioNaoCadastradoPeloNome() {
        EntityManager em = JPAUtil.getEntityManager();

        Usuario novoUsuario = new Usuario("fulano", "fulano@example.com", "12345678");
        em.getTransaction().begin();
        em.persist(novoUsuario);
        em.getTransaction().commit();

        usuarioDao = new UsuarioDao(em);
        assertThrows(NoResultException.class, () -> usuarioDao.buscarPorUsername("beltrano"));

    }

}
