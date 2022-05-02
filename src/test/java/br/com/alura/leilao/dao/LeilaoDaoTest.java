package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

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
        Usuario novoUsuario = new UsuarioBuilder().comNome("fulano")
                                                  .comEmail("fulano@example.com")
                                                  .comSenha("12345678")
                                                  .criar();
        em.persist(novoUsuario);

        Leilao novoLeilao = new LeilaoBuilder().comNome("Mochila")
                                               .comValorInicial("70.00")
                                               .comDataAbertura(LocalDate.now())
                                               .comUsuario(novoUsuario)
                                               .criar();

        Leilao leilaoSalvo = leilaoDao.salvar(novoLeilao);
        Leilao leilaoEncontrado = leilaoDao.buscarPorId(leilaoSalvo.getId());

        assertNotNull(leilaoEncontrado);
    }

    @Test
    void deveAtualizarUmLeilao() {
        Usuario novoUsuario = new UsuarioBuilder().comNome("fulano")
                                                  .comEmail("fulano@example.com")
                                                  .comSenha("12345678")
                                                  .criar();
        em.persist(novoUsuario);

        Leilao novoLeilao = new LeilaoBuilder().comNome("Mochila")
                                               .comValorInicial("70.00")
                                               .comDataAbertura(LocalDate.now())
                                               .comUsuario(novoUsuario)
                                               .criar();

        Leilao leilaoSalvo = leilaoDao.salvar(novoLeilao);
        leilaoSalvo.setNome("Celular");
        leilaoSalvo.setValorInicial(new BigDecimal("400.00"));
        leilaoSalvo = leilaoDao.salvar(leilaoSalvo);

        Leilao leilaoEncontrado = leilaoDao.buscarPorId(leilaoSalvo.getId());

        assertEquals("Celular", leilaoEncontrado.getNome());
        assertEquals(new BigDecimal("400.00"), leilaoEncontrado.getValorInicial());
    }

}
