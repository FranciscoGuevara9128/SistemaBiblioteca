package repository.dao;

import config.JPAUtil;
import entities.Autor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import repository.IAutor;

import java.util.List;
import java.util.Optional;

public class AutorDao implements IAutor {

    @Override
    public Autor guardar(Autor a) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(a);
            tx.commit();
            return a;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Autor> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Autor.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Autor> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Autor a ORDER BY a.id", Autor.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Autor actualizar(Autor a) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Autor merged = em.merge(a);
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
            Autor a = em.find(Autor.class, id);
            if (a != null) {
                em.remove(a);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
