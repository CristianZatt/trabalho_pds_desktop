/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Funcionario;
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
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) throws PreexistingEntityException, Exception {
        if (funcionario.getRegistroList() == null) {
            funcionario.setRegistroList(new ArrayList<Registro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Registro> attachedRegistroList = new ArrayList<Registro>();
            for (Registro registroListRegistroToAttach : funcionario.getRegistroList()) {
                registroListRegistroToAttach = em.getReference(registroListRegistroToAttach.getClass(), registroListRegistroToAttach.getRegistroPK());
                attachedRegistroList.add(registroListRegistroToAttach);
            }
            funcionario.setRegistroList(attachedRegistroList);
            em.persist(funcionario);
            for (Registro registroListRegistro : funcionario.getRegistroList()) {
                Funcionario oldFuncionarioOfRegistroListRegistro = registroListRegistro.getFuncionario();
                registroListRegistro.setFuncionario(funcionario);
                registroListRegistro = em.merge(registroListRegistro);
                if (oldFuncionarioOfRegistroListRegistro != null) {
                    oldFuncionarioOfRegistroListRegistro.getRegistroList().remove(registroListRegistro);
                    oldFuncionarioOfRegistroListRegistro = em.merge(oldFuncionarioOfRegistroListRegistro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncionario(funcionario.getCpf()) != null) {
                throw new PreexistingEntityException("Funcionario " + funcionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getCpf());
            List<Registro> registroListOld = persistentFuncionario.getRegistroList();
            List<Registro> registroListNew = funcionario.getRegistroList();
            List<String> illegalOrphanMessages = null;
            for (Registro registroListOldRegistro : registroListOld) {
                if (!registroListNew.contains(registroListOldRegistro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registro " + registroListOldRegistro + " since its funcionario field is not nullable.");
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
            funcionario.setRegistroList(registroListNew);
            funcionario = em.merge(funcionario);
            for (Registro registroListNewRegistro : registroListNew) {
                if (!registroListOld.contains(registroListNewRegistro)) {
                    Funcionario oldFuncionarioOfRegistroListNewRegistro = registroListNewRegistro.getFuncionario();
                    registroListNewRegistro.setFuncionario(funcionario);
                    registroListNewRegistro = em.merge(registroListNewRegistro);
                    if (oldFuncionarioOfRegistroListNewRegistro != null && !oldFuncionarioOfRegistroListNewRegistro.equals(funcionario)) {
                        oldFuncionarioOfRegistroListNewRegistro.getRegistroList().remove(registroListNewRegistro);
                        oldFuncionarioOfRegistroListNewRegistro = em.merge(oldFuncionarioOfRegistroListNewRegistro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionario.getCpf();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getCpf();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Registro> registroListOrphanCheck = funcionario.getRegistroList();
            for (Registro registroListOrphanCheckRegistro : registroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Registro " + registroListOrphanCheckRegistro + " in its registroList field has a non-nullable funcionario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(funcionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
