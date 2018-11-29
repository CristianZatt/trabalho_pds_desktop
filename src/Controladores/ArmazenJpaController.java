/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Armazem;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Setorcarregamento;
import java.util.ArrayList;
import java.util.List;
import Entidades.Pacote;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabricio
 */
public class ArmazenJpaController implements Serializable {

    public ArmazenJpaController(EntityManagerFactory emf) {
        //this.emf = emf;
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Armazem armazen) {
        if (armazen.getSetorcarregamentoList() == null) {
            armazen.setSetorcarregamentoList(new ArrayList<Setorcarregamento>());
        }
        if (armazen.getPacoteList() == null) {
            armazen.setPacoteList(new ArrayList<Pacote>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Setorcarregamento> attachedSetorcarregamentoList = new ArrayList<Setorcarregamento>();
            for (Setorcarregamento setorcarregamentoListSetorcarregamentoToAttach : armazen.getSetorcarregamentoList()) {
                setorcarregamentoListSetorcarregamentoToAttach = em.getReference(setorcarregamentoListSetorcarregamentoToAttach.getClass(), setorcarregamentoListSetorcarregamentoToAttach.getIdSetorCarregamento());
                attachedSetorcarregamentoList.add(setorcarregamentoListSetorcarregamentoToAttach);
            }
            armazen.setSetorcarregamentoList(attachedSetorcarregamentoList);
            List<Pacote> attachedPacoteList = new ArrayList<Pacote>();
            for (Pacote pacoteListPacoteToAttach : armazen.getPacoteList()) {
                pacoteListPacoteToAttach = em.getReference(pacoteListPacoteToAttach.getClass(), pacoteListPacoteToAttach.getPacotePK());
                attachedPacoteList.add(pacoteListPacoteToAttach);
            }
            armazen.setPacoteList(attachedPacoteList);
            em.persist(armazen);
            for (Setorcarregamento setorcarregamentoListSetorcarregamento : armazen.getSetorcarregamentoList()) {
                Armazem oldArmazenidArmazenOfSetorcarregamentoListSetorcarregamento = setorcarregamentoListSetorcarregamento.getArmazenidArmazen();
                setorcarregamentoListSetorcarregamento.setArmazenidArmazen(armazen);
                setorcarregamentoListSetorcarregamento = em.merge(setorcarregamentoListSetorcarregamento);
                if (oldArmazenidArmazenOfSetorcarregamentoListSetorcarregamento != null) {
                    oldArmazenidArmazenOfSetorcarregamentoListSetorcarregamento.getSetorcarregamentoList().remove(setorcarregamentoListSetorcarregamento);
                    oldArmazenidArmazenOfSetorcarregamentoListSetorcarregamento = em.merge(oldArmazenidArmazenOfSetorcarregamentoListSetorcarregamento);
                }
            }
            for (Pacote pacoteListPacote : armazen.getPacoteList()) {
                Armazem oldArmazenidArmazenOfPacoteListPacote = pacoteListPacote.getArmazenidArmazen();
                pacoteListPacote.setArmazenidArmazen(armazen);
                pacoteListPacote = em.merge(pacoteListPacote);
                if (oldArmazenidArmazenOfPacoteListPacote != null) {
                    oldArmazenidArmazenOfPacoteListPacote.getPacoteList().remove(pacoteListPacote);
                    oldArmazenidArmazenOfPacoteListPacote = em.merge(oldArmazenidArmazenOfPacoteListPacote);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Armazem armazen) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Armazem persistentArmazen = em.find(Armazem.class, armazen.getIdArmazen());
            List<Setorcarregamento> setorcarregamentoListOld = persistentArmazen.getSetorcarregamentoList();
            List<Setorcarregamento> setorcarregamentoListNew = armazen.getSetorcarregamentoList();
            List<Pacote> pacoteListOld = persistentArmazen.getPacoteList();
            List<Pacote> pacoteListNew = armazen.getPacoteList();
            List<String> illegalOrphanMessages = null;
            for (Setorcarregamento setorcarregamentoListOldSetorcarregamento : setorcarregamentoListOld) {
                if (!setorcarregamentoListNew.contains(setorcarregamentoListOldSetorcarregamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Setorcarregamento " + setorcarregamentoListOldSetorcarregamento + " since its armazenidArmazen field is not nullable.");
                }
            }
            for (Pacote pacoteListOldPacote : pacoteListOld) {
                if (!pacoteListNew.contains(pacoteListOldPacote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pacote " + pacoteListOldPacote + " since its armazenidArmazen field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Setorcarregamento> attachedSetorcarregamentoListNew = new ArrayList<Setorcarregamento>();
            for (Setorcarregamento setorcarregamentoListNewSetorcarregamentoToAttach : setorcarregamentoListNew) {
                setorcarregamentoListNewSetorcarregamentoToAttach = em.getReference(setorcarregamentoListNewSetorcarregamentoToAttach.getClass(), setorcarregamentoListNewSetorcarregamentoToAttach.getIdSetorCarregamento());
                attachedSetorcarregamentoListNew.add(setorcarregamentoListNewSetorcarregamentoToAttach);
            }
            setorcarregamentoListNew = attachedSetorcarregamentoListNew;
            armazen.setSetorcarregamentoList(setorcarregamentoListNew);
            List<Pacote> attachedPacoteListNew = new ArrayList<Pacote>();
            for (Pacote pacoteListNewPacoteToAttach : pacoteListNew) {
                pacoteListNewPacoteToAttach = em.getReference(pacoteListNewPacoteToAttach.getClass(), pacoteListNewPacoteToAttach.getPacotePK());
                attachedPacoteListNew.add(pacoteListNewPacoteToAttach);
            }
            pacoteListNew = attachedPacoteListNew;
            armazen.setPacoteList(pacoteListNew);
            armazen = em.merge(armazen);
            for (Setorcarregamento setorcarregamentoListNewSetorcarregamento : setorcarregamentoListNew) {
                if (!setorcarregamentoListOld.contains(setorcarregamentoListNewSetorcarregamento)) {
                    Armazem oldArmazenidArmazenOfSetorcarregamentoListNewSetorcarregamento = setorcarregamentoListNewSetorcarregamento.getArmazenidArmazen();
                    setorcarregamentoListNewSetorcarregamento.setArmazenidArmazen(armazen);
                    setorcarregamentoListNewSetorcarregamento = em.merge(setorcarregamentoListNewSetorcarregamento);
                    if (oldArmazenidArmazenOfSetorcarregamentoListNewSetorcarregamento != null && !oldArmazenidArmazenOfSetorcarregamentoListNewSetorcarregamento.equals(armazen)) {
                        oldArmazenidArmazenOfSetorcarregamentoListNewSetorcarregamento.getSetorcarregamentoList().remove(setorcarregamentoListNewSetorcarregamento);
                        oldArmazenidArmazenOfSetorcarregamentoListNewSetorcarregamento = em.merge(oldArmazenidArmazenOfSetorcarregamentoListNewSetorcarregamento);
                    }
                }
            }
            for (Pacote pacoteListNewPacote : pacoteListNew) {
                if (!pacoteListOld.contains(pacoteListNewPacote)) {
                    Armazem oldArmazenidArmazenOfPacoteListNewPacote = pacoteListNewPacote.getArmazenidArmazen();
                    pacoteListNewPacote.setArmazenidArmazen(armazen);
                    pacoteListNewPacote = em.merge(pacoteListNewPacote);
                    if (oldArmazenidArmazenOfPacoteListNewPacote != null && !oldArmazenidArmazenOfPacoteListNewPacote.equals(armazen)) {
                        oldArmazenidArmazenOfPacoteListNewPacote.getPacoteList().remove(pacoteListNewPacote);
                        oldArmazenidArmazenOfPacoteListNewPacote = em.merge(oldArmazenidArmazenOfPacoteListNewPacote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = armazen.getIdArmazen();
                if (findArmazen(id) == null) {
                    throw new NonexistentEntityException("The armazen with id " + id + " no longer exists.");
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
            Armazem armazen;
            try {
                armazen = em.getReference(Armazem.class, id);
                armazen.getIdArmazen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The armazen with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Setorcarregamento> setorcarregamentoListOrphanCheck = armazen.getSetorcarregamentoList();
            for (Setorcarregamento setorcarregamentoListOrphanCheckSetorcarregamento : setorcarregamentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Armazen (" + armazen + ") cannot be destroyed since the Setorcarregamento " + setorcarregamentoListOrphanCheckSetorcarregamento + " in its setorcarregamentoList field has a non-nullable armazenidArmazen field.");
            }
            List<Pacote> pacoteListOrphanCheck = armazen.getPacoteList();
            for (Pacote pacoteListOrphanCheckPacote : pacoteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Armazen (" + armazen + ") cannot be destroyed since the Pacote " + pacoteListOrphanCheckPacote + " in its pacoteList field has a non-nullable armazenidArmazen field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(armazen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Armazem> findArmazenEntities() {
        return findArmazenEntities(true, -1, -1);
    }

    public List<Armazem> findArmazenEntities(int maxResults, int firstResult) {
        return findArmazenEntities(false, maxResults, firstResult);
    }

    private List<Armazem> findArmazenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Armazem.class));
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

    public Armazem findArmazen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Armazem.class, id);
        } finally {
            em.close();
        }
    }

    public int getArmazenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Armazem> rt = cq.from(Armazem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
