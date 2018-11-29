/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Bloco;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Prateleira;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabricio
 */
public class BlocoJpaController implements Serializable {

    public BlocoJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bloco bloco) {
        if (bloco.getPrateleiraList() == null) {
            bloco.setPrateleiraList(new ArrayList<Prateleira>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Prateleira> attachedPrateleiraList = new ArrayList<Prateleira>();
            for (Prateleira prateleiraListPrateleiraToAttach : bloco.getPrateleiraList()) {
                prateleiraListPrateleiraToAttach = em.getReference(prateleiraListPrateleiraToAttach.getClass(), prateleiraListPrateleiraToAttach.getPrateleiraPK());
                attachedPrateleiraList.add(prateleiraListPrateleiraToAttach);
            }
            bloco.setPrateleiraList(attachedPrateleiraList);
            em.persist(bloco);
            for (Prateleira prateleiraListPrateleira : bloco.getPrateleiraList()) {
                Bloco oldBlocoOfPrateleiraListPrateleira = prateleiraListPrateleira.getBloco();
                prateleiraListPrateleira.setBloco(bloco);
                prateleiraListPrateleira = em.merge(prateleiraListPrateleira);
                if (oldBlocoOfPrateleiraListPrateleira != null) {
                    oldBlocoOfPrateleiraListPrateleira.getPrateleiraList().remove(prateleiraListPrateleira);
                    oldBlocoOfPrateleiraListPrateleira = em.merge(oldBlocoOfPrateleiraListPrateleira);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bloco bloco) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bloco persistentBloco = em.find(Bloco.class, bloco.getIdBloco());
            List<Prateleira> prateleiraListOld = persistentBloco.getPrateleiraList();
            List<Prateleira> prateleiraListNew = bloco.getPrateleiraList();
            List<String> illegalOrphanMessages = null;
            for (Prateleira prateleiraListOldPrateleira : prateleiraListOld) {
                if (!prateleiraListNew.contains(prateleiraListOldPrateleira)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prateleira " + prateleiraListOldPrateleira + " since its bloco field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Prateleira> attachedPrateleiraListNew = new ArrayList<Prateleira>();
            for (Prateleira prateleiraListNewPrateleiraToAttach : prateleiraListNew) {
                prateleiraListNewPrateleiraToAttach = em.getReference(prateleiraListNewPrateleiraToAttach.getClass(), prateleiraListNewPrateleiraToAttach.getPrateleiraPK());
                attachedPrateleiraListNew.add(prateleiraListNewPrateleiraToAttach);
            }
            prateleiraListNew = attachedPrateleiraListNew;
            bloco.setPrateleiraList(prateleiraListNew);
            bloco = em.merge(bloco);
            for (Prateleira prateleiraListNewPrateleira : prateleiraListNew) {
                if (!prateleiraListOld.contains(prateleiraListNewPrateleira)) {
                    Bloco oldBlocoOfPrateleiraListNewPrateleira = prateleiraListNewPrateleira.getBloco();
                    prateleiraListNewPrateleira.setBloco(bloco);
                    prateleiraListNewPrateleira = em.merge(prateleiraListNewPrateleira);
                    if (oldBlocoOfPrateleiraListNewPrateleira != null && !oldBlocoOfPrateleiraListNewPrateleira.equals(bloco)) {
                        oldBlocoOfPrateleiraListNewPrateleira.getPrateleiraList().remove(prateleiraListNewPrateleira);
                        oldBlocoOfPrateleiraListNewPrateleira = em.merge(oldBlocoOfPrateleiraListNewPrateleira);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bloco.getIdBloco();
                if (findBloco(id) == null) {
                    throw new NonexistentEntityException("The bloco with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bloco bloco;
            try {
                bloco = em.getReference(Bloco.class, id);
                bloco.getIdBloco();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bloco with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Prateleira> prateleiraListOrphanCheck = bloco.getPrateleiraList();
            for (Prateleira prateleiraListOrphanCheckPrateleira : prateleiraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bloco (" + bloco + ") cannot be destroyed since the Prateleira " + prateleiraListOrphanCheckPrateleira + " in its prateleiraList field has a non-nullable bloco field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(bloco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bloco> findBlocoEntities() {
        return findBlocoEntities(true, -1, -1);
    }

    public List<Bloco> findBlocoEntities(int maxResults, int firstResult) {
        return findBlocoEntities(false, maxResults, firstResult);
    }

    private List<Bloco> findBlocoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bloco.class));
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

    public Bloco findBloco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bloco.class, id);
        } finally {
            em.close();
        }
    }

    public int getBlocoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bloco> rt = cq.from(Bloco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
