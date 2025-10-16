package repository.dao;

import config.JPAUtil;
import entities.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class LibroDao implements ILibro {

    @Override
    public Libro guardar(Libro l) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(l);
            tx.commit();
            return l;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Libro> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Libro.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Libro> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Libro l ORDER BY l.titulo", Libro.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Libro actualizar(Libro l) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Libro merged = em.merge(l);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Libro l = em.find(Libro.class, id);
            if (l != null) {
                em.remove(l);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Libro> listarConAutorYCategorias() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT l FROM Libro l " +
                                    "JOIN FETCH l.autor " +
                                    "LEFT JOIN FETCH l.categorias " +
                                    "ORDER BY l.titulo", Libro.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
