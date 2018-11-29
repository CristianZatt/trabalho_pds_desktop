/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Equipamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Registro;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabricio
 */
public class EquipamentoJpaController implements Serializable {

    public EquipamentoJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipamento equipamento) {
        if (equipamento.getRegistroList() == null) {
            equipamento.setRegistroList(new ArrayList<Registro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Registro> attachedRegistroList = new ArrayList<Registro>();
            for (Registro registroListRegistroToAttach : equipamento.getRegistroList()) {
                registroListRegistroToAttach = em.getReference(registroListRegistroToAttach.getClass(), registroListRegistroToAttach.getRegistroPK());
                attachedRegistroList.add(registroListRegistroToAttach);
            }
            equipamento.setRegistroList(attachedRegistroList);
            em.persist(equipamento);
            for (Registro registroListRegistro : equipamento.getRegistroList()) {
                Equipamento oldEquipamentoOfRegistroListRegistro = registroListRegistro.getEquipamento();
                registroListRegistro.setEquipamento(equipamento);
                registroListRegistro = em.merge(registroListRegistro);
                if (oldEquipamentoOfRegistroListRegistro != null) {
                    oldEquipamentoOfRegistroListRegistro.getRegistroList().remove(registroListRegistro);
                    oldEquipamentoOfRegistroListRegistro = em.merge(oldEquipamentoOfRegistroListRegistro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipamento equipamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipamento persistentEquipamento = em.find(Equipamento.class, equipamento.getIdEquipamento());
            List<Registro> registroListOld = persistentEquipamento.getRegistroList();
            List<Registro> registroListNew = equipamento.getRegistroList();
            List<String> illegalOrphanMessages = null;
            for (Registro registroListOldRegistro : registroListOld) {
                if (!registroListNew.contains(registroListOldRegistro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registro " + registroListOldRegistro + " since its equipamento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Registro> attachedRegistroListNew = new ArrayList<Registro>();
            for (Registro registroListNewRegistroToAttach : registroListNew) {
                registroListNewRegistroToAttach = em.getReference(registroListNewRegistroToAttach.getClass(), registroListNewRegistroToAttach.getRegistroPK());
                attachedRegistroListNew.add(registroListNewRegistroToAttach);
            }
            registroListNew = attachedRegistroListNew;
            equipamento.setRegistroList(registroListNew);
            equipamento = em.merge(equipamento);
            for (Registro registroListNewRegistro : registroListNew) {
                if (!registroListOld.contains(registroListNewRegistro)) {
                    Equipamento oldEquipamentoOfRegistroListNewRegistro = registroListNewRegistro.getEquipamento();
                    registroListNewRegistro.setEquipamento(equipamento);
                    registroListNewRegistro = em.merge(registroListNewRegistro);
                    if (oldEquipamentoOfRegistroListNewRegistro != null && !oldEquipamentoOfRegistroListNewRegistro.equals(equipamento)) {
                        oldEquipamentoOfRegistroListNewRegistro.getRegistroList().remove(registroListNewRegistro);
                        oldEquipamentoOfRegistroListNewRegistro = em.merge(oldEquipamentoOfRegistroListNewRegistro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipamento.getIdEquipamento();
                if (findEquipamento(id) == null) {
                    throw new NonexistentEntityException("The equipamento with id " + id + " no longer exists.");
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
            Equipamento equipamento;
            try {
                equipamento = em.getReference(Equipamento.class, id);
                equipamento.getIdEquipamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Registro> registroListOrphanCheck = equipamento.getRegistroList();
            for (Registro registroListOrphanCheckRegistro : registroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipamento (" + equipamento + ") cannot be destroyed since the Registro " + registroListOrphanCheckRegistro + " in its registroList field has a non-nullable equipamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(equipamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipamento> findEquipamentoEntities() {
        return findEquipamentoEntities(true, -1, -1);
    }

    public List<Equipamento> findEquipamentoEntities(int maxResults, int firstResult) {
        return findEquipamentoEntities(false, maxResults, firstResult);
    }

    private List<Equipamento> findEquipamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipamento.class));
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

    public Equipamento findEquipamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipamento> rt = cq.from(Equipamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
