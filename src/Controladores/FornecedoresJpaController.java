/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Fornecedores;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class FornecedoresJpaController implements Serializable {

    public FornecedoresJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fornecedores fornecedores) throws PreexistingEntityException, Exception {
        if (fornecedores.getPacoteList() == null) {
            fornecedores.setPacoteList(new ArrayList<Pacote>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pacote> attachedPacoteList = new ArrayList<Pacote>();
            for (Pacote pacoteListPacoteToAttach : fornecedores.getPacoteList()) {
                pacoteListPacoteToAttach = em.getReference(pacoteListPacoteToAttach.getClass(), pacoteListPacoteToAttach.getPacotePK());
                attachedPacoteList.add(pacoteListPacoteToAttach);
            }
            fornecedores.setPacoteList(attachedPacoteList);
            em.persist(fornecedores);
            for (Pacote pacoteListPacote : fornecedores.getPacoteList()) {
                Fornecedores oldFornecedorescnpjOfPacoteListPacote = pacoteListPacote.getFornecedorescnpj();
                pacoteListPacote.setFornecedorescnpj(fornecedores);
                pacoteListPacote = em.merge(pacoteListPacote);
                if (oldFornecedorescnpjOfPacoteListPacote != null) {
                    oldFornecedorescnpjOfPacoteListPacote.getPacoteList().remove(pacoteListPacote);
                    oldFornecedorescnpjOfPacoteListPacote = em.merge(oldFornecedorescnpjOfPacoteListPacote);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFornecedores(fornecedores.getCnpj()) != null) {
                throw new PreexistingEntityException("Fornecedores " + fornecedores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fornecedores fornecedores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedores persistentFornecedores = em.find(Fornecedores.class, fornecedores.getCnpj());
            List<Pacote> pacoteListOld = persistentFornecedores.getPacoteList();
            List<Pacote> pacoteListNew = fornecedores.getPacoteList();
            List<String> illegalOrphanMessages = null;
            for (Pacote pacoteListOldPacote : pacoteListOld) {
                if (!pacoteListNew.contains(pacoteListOldPacote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pacote " + pacoteListOldPacote + " since its fornecedorescnpj field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pacote> attachedPacoteListNew = new ArrayList<Pacote>();
            for (Pacote pacoteListNewPacoteToAttach : pacoteListNew) {
                pacoteListNewPacoteToAttach = em.getReference(pacoteListNewPacoteToAttach.getClass(), pacoteListNewPacoteToAttach.getPacotePK());
                attachedPacoteListNew.add(pacoteListNewPacoteToAttach);
            }
            pacoteListNew = attachedPacoteListNew;
            fornecedores.setPacoteList(pacoteListNew);
            fornecedores = em.merge(fornecedores);
            for (Pacote pacoteListNewPacote : pacoteListNew) {
                if (!pacoteListOld.contains(pacoteListNewPacote)) {
                    Fornecedores oldFornecedorescnpjOfPacoteListNewPacote = pacoteListNewPacote.getFornecedorescnpj();
                    pacoteListNewPacote.setFornecedorescnpj(fornecedores);
                    pacoteListNewPacote = em.merge(pacoteListNewPacote);
                    if (oldFornecedorescnpjOfPacoteListNewPacote != null && !oldFornecedorescnpjOfPacoteListNewPacote.equals(fornecedores)) {
                        oldFornecedorescnpjOfPacoteListNewPacote.getPacoteList().remove(pacoteListNewPacote);
                        oldFornecedorescnpjOfPacoteListNewPacote = em.merge(oldFornecedorescnpjOfPacoteListNewPacote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fornecedores.getCnpj();
                if (findFornecedores(id) == null) {
                    throw new NonexistentEntityException("The fornecedores with id " + id + " no longer exists.");
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
            Fornecedores fornecedores;
            try {
                fornecedores = em.getReference(Fornecedores.class, id);
                fornecedores.getCnpj();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fornecedores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pacote> pacoteListOrphanCheck = fornecedores.getPacoteList();
            for (Pacote pacoteListOrphanCheckPacote : pacoteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fornecedores (" + fornecedores + ") cannot be destroyed since the Pacote " + pacoteListOrphanCheckPacote + " in its pacoteList field has a non-nullable fornecedorescnpj field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fornecedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fornecedores> findFornecedoresEntities() {
        return findFornecedoresEntities(true, -1, -1);
    }

    public List<Fornecedores> findFornecedoresEntities(int maxResults, int firstResult) {
        return findFornecedoresEntities(false, maxResults, firstResult);
    }

    private List<Fornecedores> findFornecedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fornecedores.class));
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

    public Fornecedores findFornecedores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fornecedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getFornecedoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fornecedores> rt = cq.from(Fornecedores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
