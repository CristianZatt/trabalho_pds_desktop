/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Prateleira;
import Entidades.Rua;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabricio
 */
public class RuaJpaController implements Serializable {

    public RuaJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rua rua) {
        if (rua.getPrateleiraList() == null) {
            rua.setPrateleiraList(new ArrayList<Prateleira>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Prateleira> attachedPrateleiraList = new ArrayList<Prateleira>();
            for (Prateleira prateleiraListPrateleiraToAttach : rua.getPrateleiraList()) {
                prateleiraListPrateleiraToAttach = em.getReference(prateleiraListPrateleiraToAttach.getClass(), prateleiraListPrateleiraToAttach.getPrateleiraPK());
                attachedPrateleiraList.add(prateleiraListPrateleiraToAttach);
            }
            rua.setPrateleiraList(attachedPrateleiraList);
            em.persist(rua);
            for (Prateleira prateleiraListPrateleira : rua.getPrateleiraList()) {
                Rua oldRuaOfPrateleiraListPrateleira = prateleiraListPrateleira.getRua();
                prateleiraListPrateleira.setRua(rua);
                prateleiraListPrateleira = em.merge(prateleiraListPrateleira);
                if (oldRuaOfPrateleiraListPrateleira != null) {
                    oldRuaOfPrateleiraListPrateleira.getPrateleiraList().remove(prateleiraListPrateleira);
                    oldRuaOfPrateleiraListPrateleira = em.merge(oldRuaOfPrateleiraListPrateleira);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rua rua) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rua persistentRua = em.find(Rua.class, rua.getIdRua());
            List<Prateleira> prateleiraListOld = persistentRua.getPrateleiraList();
            List<Prateleira> prateleiraListNew = rua.getPrateleiraList();
            List<String> illegalOrphanMessages = null;
            for (Prateleira prateleiraListOldPrateleira : prateleiraListOld) {
                if (!prateleiraListNew.contains(prateleiraListOldPrateleira)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prateleira " + prateleiraListOldPrateleira + " since its rua field is not nullable.");
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
            rua.setPrateleiraList(prateleiraListNew);
            rua = em.merge(rua);
            for (Prateleira prateleiraListNewPrateleira : prateleiraListNew) {
                if (!prateleiraListOld.contains(prateleiraListNewPrateleira)) {
                    Rua oldRuaOfPrateleiraListNewPrateleira = prateleiraListNewPrateleira.getRua();
                    prateleiraListNewPrateleira.setRua(rua);
                    prateleiraListNewPrateleira = em.merge(prateleiraListNewPrateleira);
                    if (oldRuaOfPrateleiraListNewPrateleira != null && !oldRuaOfPrateleiraListNewPrateleira.equals(rua)) {
                        oldRuaOfPrateleiraListNewPrateleira.getPrateleiraList().remove(prateleiraListNewPrateleira);
                        oldRuaOfPrateleiraListNewPrateleira = em.merge(oldRuaOfPrateleiraListNewPrateleira);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rua.getIdRua();
                if (findRua(id) == null) {
                    throw new NonexistentEntityException("The rua with id " + id + " no longer exists.");
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
            Rua rua;
            try {
                rua = em.getReference(Rua.class, id);
                rua.getIdRua();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rua with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Prateleira> prateleiraListOrphanCheck = rua.getPrateleiraList();
            for (Prateleira prateleiraListOrphanCheckPrateleira : prateleiraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rua (" + rua + ") cannot be destroyed since the Prateleira " + prateleiraListOrphanCheckPrateleira + " in its prateleiraList field has a non-nullable rua field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rua);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rua> findRuaEntities() {
        return findRuaEntities(true, -1, -1);
    }

    public List<Rua> findRuaEntities(int maxResults, int firstResult) {
        return findRuaEntities(false, maxResults, firstResult);
    }

    private List<Rua> findRuaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rua.class));
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

    public Rua findRua(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rua.class, id);
        } finally {
            em.close();
        }
    }

    public int getRuaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rua> rt = cq.from(Rua.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
