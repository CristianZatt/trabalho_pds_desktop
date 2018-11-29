/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Lugarpacote;
import Entidades.LugarpacotePK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Prateleira;
import Entidades.Pacote;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabricio
 */
public class LugarpacoteJpaController implements Serializable {

    public LugarpacoteJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lugarpacote lugarpacote) throws PreexistingEntityException, Exception {
        if (lugarpacote.getLugarpacotePK() == null) {
            lugarpacote.setLugarpacotePK(new LugarpacotePK());
        }
        if (lugarpacote.getPacoteList() == null) {
            lugarpacote.setPacoteList(new ArrayList<Pacote>());
        }
        lugarpacote.getLugarpacotePK().setPrateleiraidPrateleira(lugarpacote.getPrateleira().getPrateleiraPK().getIdPrateleira());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prateleira prateleira = lugarpacote.getPrateleira();
            if (prateleira != null) {
                prateleira = em.getReference(prateleira.getClass(), prateleira.getPrateleiraPK());
                lugarpacote.setPrateleira(prateleira);
            }
            List<Pacote> attachedPacoteList = new ArrayList<Pacote>();
            for (Pacote pacoteListPacoteToAttach : lugarpacote.getPacoteList()) {
                pacoteListPacoteToAttach = em.getReference(pacoteListPacoteToAttach.getClass(), pacoteListPacoteToAttach.getPacotePK());
                attachedPacoteList.add(pacoteListPacoteToAttach);
            }
            lugarpacote.setPacoteList(attachedPacoteList);
            em.persist(lugarpacote);
            if (prateleira != null) {
                prateleira.getLugarpacoteList().add(lugarpacote);
                prateleira = em.merge(prateleira);
            }
            for (Pacote pacoteListPacote : lugarpacote.getPacoteList()) {
                Lugarpacote oldLugarPacoteidLugarPacoteOfPacoteListPacote = pacoteListPacote.getLugarPacoteidLugarPacote();
                pacoteListPacote.setLugarPacoteidLugarPacote(lugarpacote);
                pacoteListPacote = em.merge(pacoteListPacote);
                if (oldLugarPacoteidLugarPacoteOfPacoteListPacote != null) {
                    oldLugarPacoteidLugarPacoteOfPacoteListPacote.getPacoteList().remove(pacoteListPacote);
                    oldLugarPacoteidLugarPacoteOfPacoteListPacote = em.merge(oldLugarPacoteidLugarPacoteOfPacoteListPacote);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLugarpacote(lugarpacote.getLugarpacotePK()) != null) {
                throw new PreexistingEntityException("Lugarpacote " + lugarpacote + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lugarpacote lugarpacote) throws IllegalOrphanException, NonexistentEntityException, Exception {
        lugarpacote.getLugarpacotePK().setPrateleiraidPrateleira(lugarpacote.getPrateleira().getPrateleiraPK().getIdPrateleira());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lugarpacote persistentLugarpacote = em.find(Lugarpacote.class, lugarpacote.getLugarpacotePK());
            Prateleira prateleiraOld = persistentLugarpacote.getPrateleira();
            Prateleira prateleiraNew = lugarpacote.getPrateleira();
            List<Pacote> pacoteListOld = persistentLugarpacote.getPacoteList();
            List<Pacote> pacoteListNew = lugarpacote.getPacoteList();
            List<String> illegalOrphanMessages = null;
            for (Pacote pacoteListOldPacote : pacoteListOld) {
                if (!pacoteListNew.contains(pacoteListOldPacote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pacote " + pacoteListOldPacote + " since its lugarPacoteidLugarPacote field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (prateleiraNew != null) {
                prateleiraNew = em.getReference(prateleiraNew.getClass(), prateleiraNew.getPrateleiraPK());
                lugarpacote.setPrateleira(prateleiraNew);
            }
            List<Pacote> attachedPacoteListNew = new ArrayList<Pacote>();
            for (Pacote pacoteListNewPacoteToAttach : pacoteListNew) {
                pacoteListNewPacoteToAttach = em.getReference(pacoteListNewPacoteToAttach.getClass(), pacoteListNewPacoteToAttach.getPacotePK());
                attachedPacoteListNew.add(pacoteListNewPacoteToAttach);
            }
            pacoteListNew = attachedPacoteListNew;
            lugarpacote.setPacoteList(pacoteListNew);
            lugarpacote = em.merge(lugarpacote);
            if (prateleiraOld != null && !prateleiraOld.equals(prateleiraNew)) {
                prateleiraOld.getLugarpacoteList().remove(lugarpacote);
                prateleiraOld = em.merge(prateleiraOld);
            }
            if (prateleiraNew != null && !prateleiraNew.equals(prateleiraOld)) {
                prateleiraNew.getLugarpacoteList().add(lugarpacote);
                prateleiraNew = em.merge(prateleiraNew);
            }
            for (Pacote pacoteListNewPacote : pacoteListNew) {
                if (!pacoteListOld.contains(pacoteListNewPacote)) {
                    Lugarpacote oldLugarPacoteidLugarPacoteOfPacoteListNewPacote = pacoteListNewPacote.getLugarPacoteidLugarPacote();
                    pacoteListNewPacote.setLugarPacoteidLugarPacote(lugarpacote);
                    pacoteListNewPacote = em.merge(pacoteListNewPacote);
                    if (oldLugarPacoteidLugarPacoteOfPacoteListNewPacote != null && !oldLugarPacoteidLugarPacoteOfPacoteListNewPacote.equals(lugarpacote)) {
                        oldLugarPacoteidLugarPacoteOfPacoteListNewPacote.getPacoteList().remove(pacoteListNewPacote);
                        oldLugarPacoteidLugarPacoteOfPacoteListNewPacote = em.merge(oldLugarPacoteidLugarPacoteOfPacoteListNewPacote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LugarpacotePK id = lugarpacote.getLugarpacotePK();
                if (findLugarpacote(id) == null) {
                    throw new NonexistentEntityException("The lugarpacote with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LugarpacotePK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lugarpacote lugarpacote;
            try {
                lugarpacote = em.getReference(Lugarpacote.class, id);
                lugarpacote.getLugarpacotePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lugarpacote with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pacote> pacoteListOrphanCheck = lugarpacote.getPacoteList();
            for (Pacote pacoteListOrphanCheckPacote : pacoteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Lugarpacote (" + lugarpacote + ") cannot be destroyed since the Pacote " + pacoteListOrphanCheckPacote + " in its pacoteList field has a non-nullable lugarPacoteidLugarPacote field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Prateleira prateleira = lugarpacote.getPrateleira();
            if (prateleira != null) {
                prateleira.getLugarpacoteList().remove(lugarpacote);
                prateleira = em.merge(prateleira);
            }
            em.remove(lugarpacote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lugarpacote> findLugarpacoteEntities() {
        return findLugarpacoteEntities(true, -1, -1);
    }

    public List<Lugarpacote> findLugarpacoteEntities(int maxResults, int firstResult) {
        return findLugarpacoteEntities(false, maxResults, firstResult);
    }

    private List<Lugarpacote> findLugarpacoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lugarpacote.class));
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

    public Lugarpacote findLugarpacote(LugarpacotePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lugarpacote.class, id);
        } finally {
            em.close();
        }
    }

    public int getLugarpacoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lugarpacote> rt = cq.from(Lugarpacote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
