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
import Entidades.Pacote;
import Entidades.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabricio
 */
public class ProdutoJpaController implements Serializable {

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produto produto) {
        if (produto.getPacoteList() == null) {
            produto.setPacoteList(new ArrayList<Pacote>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pacote> attachedPacoteList = new ArrayList<Pacote>();
            for (Pacote pacoteListPacoteToAttach : produto.getPacoteList()) {
                pacoteListPacoteToAttach = em.getReference(pacoteListPacoteToAttach.getClass(), pacoteListPacoteToAttach.getPacotePK());
                attachedPacoteList.add(pacoteListPacoteToAttach);
            }
            produto.setPacoteList(attachedPacoteList);
            em.persist(produto);
            for (Pacote pacoteListPacote : produto.getPacoteList()) {
                Produto oldProdutoOfPacoteListPacote = pacoteListPacote.getProduto();
                pacoteListPacote.setProduto(produto);
                pacoteListPacote = em.merge(pacoteListPacote);
                if (oldProdutoOfPacoteListPacote != null) {
                    oldProdutoOfPacoteListPacote.getPacoteList().remove(pacoteListPacote);
                    oldProdutoOfPacoteListPacote = em.merge(oldProdutoOfPacoteListPacote);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProduto = em.find(Produto.class, produto.getIdProduto());
            List<Pacote> pacoteListOld = persistentProduto.getPacoteList();
            List<Pacote> pacoteListNew = produto.getPacoteList();
            List<String> illegalOrphanMessages = null;
            for (Pacote pacoteListOldPacote : pacoteListOld) {
                if (!pacoteListNew.contains(pacoteListOldPacote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pacote " + pacoteListOldPacote + " since its produto field is not nullable.");
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
            produto.setPacoteList(pacoteListNew);
            produto = em.merge(produto);
            for (Pacote pacoteListNewPacote : pacoteListNew) {
                if (!pacoteListOld.contains(pacoteListNewPacote)) {
                    Produto oldProdutoOfPacoteListNewPacote = pacoteListNewPacote.getProduto();
                    pacoteListNewPacote.setProduto(produto);
                    pacoteListNewPacote = em.merge(pacoteListNewPacote);
                    if (oldProdutoOfPacoteListNewPacote != null && !oldProdutoOfPacoteListNewPacote.equals(produto)) {
                        oldProdutoOfPacoteListNewPacote.getPacoteList().remove(pacoteListNewPacote);
                        oldProdutoOfPacoteListNewPacote = em.merge(oldProdutoOfPacoteListNewPacote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produto.getIdProduto();
                if (findProduto(id) == null) {
                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
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
            Produto produto;
            try {
                produto = em.getReference(Produto.class, id);
                produto.getIdProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pacote> pacoteListOrphanCheck = produto.getPacoteList();
            for (Pacote pacoteListOrphanCheckPacote : pacoteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the Pacote " + pacoteListOrphanCheckPacote + " in its pacoteList field has a non-nullable produto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutoEntities() {
        return findProdutoEntities(true, -1, -1);
    }

    public List<Produto> findProdutoEntities(int maxResults, int firstResult) {
        return findProdutoEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
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

    public Produto findProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
