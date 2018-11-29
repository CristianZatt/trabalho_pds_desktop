/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Armazem;
import Entidades.Setorcarregamento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabricio
 */
public class SetorcarregamentoJpaController implements Serializable {

    public SetorcarregamentoJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Setorcarregamento setorcarregamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Armazem armazenidArmazen = setorcarregamento.getArmazenidArmazen();
            if (armazenidArmazen != null) {
                armazenidArmazen = em.getReference(armazenidArmazen.getClass(), armazenidArmazen.getIdArmazen());
                setorcarregamento.setArmazenidArmazen(armazenidArmazen);
            }
            em.persist(setorcarregamento);
            if (armazenidArmazen != null) {
                armazenidArmazen.getSetorcarregamentoList().add(setorcarregamento);
                armazenidArmazen = em.merge(armazenidArmazen);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Setorcarregamento setorcarregamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Setorcarregamento persistentSetorcarregamento = em.find(Setorcarregamento.class, setorcarregamento.getIdSetorCarregamento());
            Armazem armazenidArmazenOld = persistentSetorcarregamento.getArmazenidArmazen();
            Armazem armazenidArmazenNew = setorcarregamento.getArmazenidArmazen();
            if (armazenidArmazenNew != null) {
                armazenidArmazenNew = em.getReference(armazenidArmazenNew.getClass(), armazenidArmazenNew.getIdArmazen());
                setorcarregamento.setArmazenidArmazen(armazenidArmazenNew);
            }
            setorcarregamento = em.merge(setorcarregamento);
            if (armazenidArmazenOld != null && !armazenidArmazenOld.equals(armazenidArmazenNew)) {
                armazenidArmazenOld.getSetorcarregamentoList().remove(setorcarregamento);
                armazenidArmazenOld = em.merge(armazenidArmazenOld);
            }
            if (armazenidArmazenNew != null && !armazenidArmazenNew.equals(armazenidArmazenOld)) {
                armazenidArmazenNew.getSetorcarregamentoList().add(setorcarregamento);
                armazenidArmazenNew = em.merge(armazenidArmazenNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = setorcarregamento.getIdSetorCarregamento();
                if (findSetorcarregamento(id) == null) {
                    throw new NonexistentEntityException("The setorcarregamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Setorcarregamento setorcarregamento;
            try {
                setorcarregamento = em.getReference(Setorcarregamento.class, id);
                setorcarregamento.getIdSetorCarregamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The setorcarregamento with id " + id + " no longer exists.", enfe);
            }
            Armazem armazenidArmazen = setorcarregamento.getArmazenidArmazen();
            if (armazenidArmazen != null) {
                armazenidArmazen.getSetorcarregamentoList().remove(setorcarregamento);
                armazenidArmazen = em.merge(armazenidArmazen);
            }
            em.remove(setorcarregamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Setorcarregamento> findSetorcarregamentoEntities() {
        return findSetorcarregamentoEntities(true, -1, -1);
    }

    public List<Setorcarregamento> findSetorcarregamentoEntities(int maxResults, int firstResult) {
        return findSetorcarregamentoEntities(false, maxResults, firstResult);
    }

    private List<Setorcarregamento> findSetorcarregamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Setorcarregamento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Setorcarregamento findSetorcarregamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Setorcarregamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSetorcarregamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Setorcarregamento> rt = cq.from(Setorcarregamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
