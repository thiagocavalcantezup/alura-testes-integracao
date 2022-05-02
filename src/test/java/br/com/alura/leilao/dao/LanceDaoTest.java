package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LanceBuilder;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

public class LanceDaoTest {

    private LanceDao lanceDao;
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        em = JPAUtil.getEntityManager();
        lanceDao = new LanceDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaCadastrarUmLance() {
        Usuario novoUsuario = new UsuarioBuilder().nome("fulano")
                                                  .email("fulano@example.com")
                                                  .senha("12345678")
                                                  .criar();
        em.persist(novoUsuario);

        Leilao novoLeilao = new LeilaoBuilder().nome("Mochila")
                                               .valorInicial("70.00")
                                               .dataAbertura(LocalDate.now())
                                               .usuario(novoUsuario)
                                               .criar();
        em.persist(novoLeilao);

        Lance novoLance = new LanceBuilder().usuario(novoUsuario)
                                            .valor(new BigDecimal("100"))
                                            .criar();

        novoLeilao.propoe(novoLance);

        lanceDao.salvar(novoLance);

        assertNotNull(em.find(Lance.class, novoLance.getId()));
    }

    @Test
    void deveriaBuscarMaiorLanceDoLeilao() {
        Usuario novoUsuario1 = new UsuarioBuilder().nome("fulano")
                                                   .email("fulano@example.com")
                                                   .senha("12345678")
                                                   .criar();
        em.persist(novoUsuario1);

        Usuario novoUsuario2 = new UsuarioBuilder().nome("beltrano")
                                                   .email("beltrano@example.com")
                                                   .senha("12345678")
                                                   .criar();
        em.persist(novoUsuario2);

        Usuario novoUsuario3 = new UsuarioBuilder().nome("ciclano")
                                                   .email("ciclano@example.com")
                                                   .senha("12345678")
                                                   .criar();
        em.persist(novoUsuario3);

        Usuario novoUsuario4 = new UsuarioBuilder().nome("thiago")
                                                   .email("thiago@example.com")
                                                   .senha("12345678")
                                                   .criar();
        em.persist(novoUsuario4);

        Leilao novoLeilao = new LeilaoBuilder().nome("Mochila")
                                               .valorInicial("70.00")
                                               .dataAbertura(LocalDate.now())
                                               .usuario(novoUsuario4)
                                               .criar();
        em.persist(novoLeilao);

        Lance novoLance1 = new LanceBuilder().usuario(novoUsuario1)
                                             .valor(new BigDecimal("100"))
                                             .criar();
        if (novoLeilao.propoe(novoLance1)) {
            lanceDao.salvar(novoLance1);
        }

        Lance novoLance2 = new LanceBuilder().usuario(novoUsuario2)
                                             .valor(new BigDecimal("200"))
                                             .criar();
        if (novoLeilao.propoe(novoLance2)) {
            lanceDao.salvar(novoLance2);
        }

        Lance novoLance3 = new LanceBuilder().usuario(novoUsuario3)
                                             .valor(new BigDecimal("500"))
                                             .criar();
        if (novoLeilao.propoe(novoLance3)) {
            lanceDao.salvar(novoLance3);
        }

        Lance lanceEncontrado = lanceDao.buscarMaiorLanceDoLeilao(novoLeilao);

        assertEquals(novoLance3, lanceEncontrado);
    }

}
