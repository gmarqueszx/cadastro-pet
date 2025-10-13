package br.com.gmarqueszx.cadastropet.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.util.Properties;

public class JpaUtil {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            Properties props = new Properties();
            props.load(JpaUtil.class.getClassLoader().getResourceAsStream("db.properties"));

            entityManagerFactory = Persistence.createEntityManagerFactory("PETS_PU", props);

        } catch (IOException e) {
            throw new RuntimeException("Não foi possível carregar as propriedades do banco de dados.", e);
        }
    }


    public static EntityManager entityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
