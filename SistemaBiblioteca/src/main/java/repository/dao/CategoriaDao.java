package repository.dao;

import config.JPAUtil;
import entities.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import repository.ICategoria;

import java.util.List;
import java.util.Optional;

public class CategoriaDao implements ICategoria {

    @Override
    public Categoria guardar(Categoria c) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(c);
            tx.commit();
            return c;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Categoria.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Categoria> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre", Categoria.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Categoria actualizar(Categoria c) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Categoria merged = em.merge(c);
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
            Categoria c = em.find(Categoria.class, id);
            if (c != null) {
                em.remove(c);
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
