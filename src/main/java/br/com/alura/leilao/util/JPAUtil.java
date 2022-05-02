package br.com.alura.leilao.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private final static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(
        "tests"
    );

    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

}
