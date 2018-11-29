/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Bloco;
import Entidades.Rua;
import Entidades.Lugarpacote;
import Entidades.Prateleira;
import Entidades.PrateleiraPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabricio
 */
public class PrateleiraJpaController implements Serializable {

    public PrateleiraJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Prateleira prateleira) throws PreexistingEntityException, Exception {
        if (prateleira.getPrateleiraPK() == null) {
            prateleira.setPrateleiraPK(new PrateleiraPK());
        }
        if (prateleira.getLugarpacoteList() == null) {
            prateleira.setLugarpacoteList(new ArrayList<Lugarpacote>());
        }
        prateleira.getPrateleiraPK().setBlocoidBloco(prateleira.getBloco().getIdBloco());
        prateleira.getPrateleiraPK().setRuaidRua(prateleira.getRua().getIdRua());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bloco bloco = prateleira.getBloco();
            if (bloco != null) {
                bloco = em.getReference(bloco.getClass(), bloco.getIdBloco());
                prateleira.setBloco(bloco);
            }
            Rua rua = prateleira.getRua();
            if (rua != null) {
                rua = em.getReference(rua.getClass(), rua.getIdRua());
                prateleira.setRua(rua);
            }
            List<Lugarpacote> attachedLugarpacoteList = new ArrayList<Lugarpacote>();
            for (Lugarpacote lugarpacoteListLugarpacoteToAttach : prateleira.getLugarpacoteList()) {
                lugarpacoteListLugarpacoteToAttach = em.getReference(lugarpacoteListLugarpacoteToAttach.getClass(), lugarpacoteListLugarpacoteToAttach.getLugarpacotePK());
                attachedLugarpacoteList.add(lugarpacoteListLugarpacoteToAttach);
            }
            prateleira.setLugarpacoteList(attachedLugarpacoteList);
            em.persist(prateleira);
            if (bloco != null) {
                bloco.getPrateleiraList().add(prateleira);
                bloco = em.merge(bloco);
            }
            if (rua != null) {
                rua.getPrateleiraList().add(prateleira);
                rua = em.merge(rua);
            }
            for (Lugarpacote lugarpacoteListLugarpacote : prateleira.getLugarpacoteList()) {
                Prateleira oldPrateleiraOfLugarpacoteListLugarpacote = lugarpacoteListLugarpacote.getPrateleira();
                lugarpacoteListLugarpacote.setPrateleira(prateleira);
                lugarpacoteListLugarpacote = em.merge(lugarpacoteListLugarpacote);
                if (oldPrateleiraOfLugarpacoteListLugarpacote != null) {
                    oldPrateleiraOfLugarpacoteListLugarpacote.getLugarpacoteList().remove(lugarpacoteListLugarpacote);
                    oldPrateleiraOfLugarpacoteListLugarpacote = em.merge(oldPrateleiraOfLugarpacoteListLugarpacote);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrateleira(prateleira.getPrateleiraPK()) != null) {
                throw new PreexistingEntityException("Prateleira " + prateleira + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Prateleira prateleira) throws IllegalOrphanException, NonexistentEntityException, Exception {
        prateleira.getPrateleiraPK().setBlocoidBloco(prateleira.getBloco().getIdBloco());
        prateleira.getPrateleiraPK().setRuaidRua(prateleira.getRua().getIdRua());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prateleira persistentPrateleira = em.find(Prateleira.class, prateleira.getPrateleiraPK());
            Bloco blocoOld = persistentPrateleira.getBloco();
            Bloco blocoNew = prateleira.getBloco();
            Rua ruaOld = persistentPrateleira.getRua();
            Rua ruaNew = prateleira.getRua();
            List<Lugarpacote> lugarpacoteListOld = persistentPrateleira.getLugarpacoteList();
            List<Lugarpacote> lugarpacoteListNew = prateleira.getLugarpacoteList();
            List<String> illegalOrphanMessages = null;
            for (Lugarpacote lugarpacoteListOldLugarpacote : lugarpacoteListOld) {
                if (!lugarpacoteListNew.contains(lugarpacoteListOldLugarpacote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lugarpacote " + lugarpacoteListOldLugarpacote + " since its prateleira field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (blocoNew != null) {
                blocoNew = em.getReference(blocoNew.getClass(), blocoNew.getIdBloco());
                prateleira.setBloco(blocoNew);
            }
            if (ruaNew != null) {
                ruaNew = em.getReference(ruaNew.getClass(), ruaNew.getIdRua());
                prateleira.setRua(ruaNew);
            }
            List<Lugarpacote> attachedLugarpacoteListNew = new ArrayList<Lugarpacote>();
            for (Lugarpacote lugarpacoteListNewLugarpacoteToAttach : lugarpacoteListNew) {
                lugarpacoteListNewLugarpacoteToAttach = em.getReference(lugarpacoteListNewLugarpacoteToAttach.getClass(), lugarpacoteListNewLugarpacoteToAttach.getLugarpacotePK());
                attachedLugarpacoteListNew.add(lugarpacoteListNewLugarpacoteToAttach);
            }
            lugarpacoteListNew = attachedLugarpacoteListNew;
            prateleira.setLugarpacoteList(lugarpacoteListNew);
            prateleira = em.merge(prateleira);
            if (blocoOld != null && !blocoOld.equals(blocoNew)) {
                blocoOld.getPrateleiraList().remove(prateleira);
                blocoOld = em.merge(blocoOld);
            }
            if (blocoNew != null && !blocoNew.equals(blocoOld)) {
                blocoNew.getPrateleiraList().add(prateleira);
                blocoNew = em.merge(blocoNew);
            }
            if (ruaOld != null && !ruaOld.equals(ruaNew)) {
                ruaOld.getPrateleiraList().remove(prateleira);
                ruaOld = em.merge(ruaOld);
            }
            if (ruaNew != null && !ruaNew.equals(ruaOld)) {
                ruaNew.getPrateleiraList().add(prateleira);
                ruaNew = em.merge(ruaNew);
            }
            for (Lugarpacote lugarpacoteListNewLugarpacote : lugarpacoteListNew) {
                if (!lugarpacoteListOld.contains(lugarpacoteListNewLugarpacote)) {
                    Prateleira oldPrateleiraOfLugarpacoteListNewLugarpacote = lugarpacoteListNewLugarpacote.getPrateleira();
                    lugarpacoteListNewLugarpacote.setPrateleira(prateleira);
                    lugarpacoteListNewLugarpacote = em.merge(lugarpacoteListNewLugarpacote);
                    if (oldPrateleiraOfLugarpacoteListNewLugarpacote != null && !oldPrateleiraOfLugarpacoteListNewLugarpacote.equals(prateleira)) {
                        oldPrateleiraOfLugarpacoteListNewLugarpacote.getLugarpacoteList().remove(lugarpacoteListNewLugarpacote);
                        oldPrateleiraOfLugarpacoteListNewLugarpacote = em.merge(oldPrateleiraOfLugarpacoteListNewLugarpacote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PrateleiraPK id = prateleira.getPrateleiraPK();
                if (findPrateleira(id) == null) {
                    throw new NonexistentEntityException("The prateleira with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PrateleiraPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prateleira prateleira;
            try {
                prateleira = em.getReference(Prateleira.class, id);
                prateleira.getPrateleiraPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prateleira with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Lugarpacote> lugarpacoteListOrphanCheck = prateleira.getLugarpacoteList();
            for (Lugarpacote lugarpacoteListOrphanCheckLugarpacote : lugarpacoteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Prateleira (" + prateleira + ") cannot be destroyed since the Lugarpacote " + lugarpacoteListOrphanCheckLugarpacote + " in its lugarpacoteList field has a non-nullable prateleira field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Bloco bloco = prateleira.getBloco();
            if (bloco != null) {
                bloco.getPrateleiraList().remove(prateleira);
                bloco = em.merge(bloco);
            }
            Rua rua = prateleira.getRua();
            if (rua != null) {
                rua.getPrateleiraList().remove(prateleira);
                rua = em.merge(rua);
            }
            em.remove(prateleira);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prateleira> findPrateleiraEntities() {
        return findPrateleiraEntities(true, -1, -1);
    }

    public List<Prateleira> findPrateleiraEntities(int maxResults, int firstResult) {
        return findPrateleiraEntities(false, maxResults, firstResult);
    }

    private List<Prateleira> findPrateleiraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Prateleira.class));
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

    public Prateleira findPrateleira(PrateleiraPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Prateleira.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrateleiraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Prateleira> rt = cq.from(Prateleira.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
