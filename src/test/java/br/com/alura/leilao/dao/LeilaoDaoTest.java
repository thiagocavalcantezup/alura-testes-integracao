package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

public class LeilaoDaoTest {

    private LeilaoDao leilaoDao;
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        em = JPAUtil.getEntityManager();
        leilaoDao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveCadastrarUmLeilao() {
        Usuario novoUsuario = criarUsuario();
        Leilao novoLeilao = new Leilao(
            "Mochila", new BigDecimal("70.00"), LocalDate.now(), novoUsuario
        );
        Leilao leilaoSalvo = leilaoDao.salvar(novoLeilao);
        Leilao leilaoEncontrado = leilaoDao.buscarPorId(leilaoSalvo.getId());

        assertNotNull(leilaoEncontrado);
    }

    private Usuario criarUsuario() {
        Usuario novoUsuario = new Usuario("fulano", "fulano@example.com", "12345678");
        em.persist(novoUsuario);

        return novoUsuario;
    }

}
