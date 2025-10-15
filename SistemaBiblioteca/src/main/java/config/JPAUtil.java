package config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JPAUtil {

    private static final String PU_NAME = "BibliotecaPU";
    private static final EntityManagerFactory emf = buildEMF();

    private JPAUtil() { }

    private static EntityManagerFactory buildEMF() {
        try {
            return Persistence.createEntityManagerFactory(PU_NAME);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Error inicializando EMF: " + ex.getMessage());
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

