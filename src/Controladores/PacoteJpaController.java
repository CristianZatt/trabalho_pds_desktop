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
import Entidades.Armazem;
import Entidades.Fornecedores;
import Entidades.Lugarpacote;
import Entidades.Pacote;
import Entidades.PacotePK;
import Entidades.Produto;
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
public class PacoteJpaController implements Serializable {

    public PacoteJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pacote pacote) throws PreexistingEntityException, Exception {
        if (pacote.getPacotePK() == null) {
            pacote.setPacotePK(new PacotePK());
        }
        if (pacote.getRegistroList() == null) {
            pacote.setRegistroList(new ArrayList<Registro>());
        }
        pacote.getPacotePK().setProdutoidProduto(pacote.getProduto().getIdProduto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Armazem armazenidArmazen = pacote.getArmazenidArmazen();
            if (armazenidArmazen != null) {
                armazenidArmazen = em.getReference(armazenidArmazen.getClass(), armazenidArmazen.getIdArmazen());
                pacote.setArmazenidArmazen(armazenidArmazen);
            }
            Fornecedores fornecedorescnpj = pacote.getFornecedorescnpj();
            if (fornecedorescnpj != null) {
                fornecedorescnpj = em.getReference(fornecedorescnpj.getClass(), fornecedorescnpj.getCnpj());
                pacote.setFornecedorescnpj(fornecedorescnpj);
            }
            Lugarpacote lugarPacoteidLugarPacote = pacote.getLugarPacoteidLugarPacote();
            if (lugarPacoteidLugarPacote != null) {
                lugarPacoteidLugarPacote = em.getReference(lugarPacoteidLugarPacote.getClass(), lugarPacoteidLugarPacote.getLugarpacotePK());
                pacote.setLugarPacoteidLugarPacote(lugarPacoteidLugarPacote);
            }
            Produto produto = pacote.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdProduto());
                pacote.setProduto(produto);
            }
            List<Registro> attachedRegistroList = new ArrayList<Registro>();
            for (Registro registroListRegistroToAttach : pacote.getRegistroList()) {
                registroListRegistroToAttach = em.getReference(registroListRegistroToAttach.getClass(), registroListRegistroToAttach.getRegistroPK());
                attachedRegistroList.add(registroListRegistroToAttach);
            }
            pacote.setRegistroList(attachedRegistroList);
            em.persist(pacote);
            if (armazenidArmazen != null) {
                armazenidArmazen.getPacoteList().add(pacote);
                armazenidArmazen = em.merge(armazenidArmazen);
            }
            if (fornecedorescnpj != null) {
                fornecedorescnpj.getPacoteList().add(pacote);
                fornecedorescnpj = em.merge(fornecedorescnpj);
            }
            if (lugarPacoteidLugarPacote != null) {
                lugarPacoteidLugarPacote.getPacoteList().add(pacote);
                lugarPacoteidLugarPacote = em.merge(lugarPacoteidLugarPacote);
            }
            if (produto != null) {
                produto.getPacoteList().add(pacote);
                produto = em.merge(produto);
            }
            for (Registro registroListRegistro : pacote.getRegistroList()) {
                Pacote oldPacoteOfRegistroListRegistro = registroListRegistro.getPacote();
                registroListRegistro.setPacote(pacote);
                registroListRegistro = em.merge(registroListRegistro);
                if (oldPacoteOfRegistroListRegistro != null) {
                    oldPacoteOfRegistroListRegistro.getRegistroList().remove(registroListRegistro);
                    oldPacoteOfRegistroListRegistro = em.merge(oldPacoteOfRegistroListRegistro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPacote(pacote.getPacotePK()) != null) {
                throw new PreexistingEntityException("Pacote " + pacote + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pacote pacote) throws IllegalOrphanException, NonexistentEntityException, Exception {
        pacote.getPacotePK().setProdutoidProduto(pacote.getProduto().getIdProduto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pacote persistentPacote = em.find(Pacote.class, pacote.getPacotePK());
            Armazem armazenidArmazenOld = persistentPacote.getArmazenidArmazen();
            Armazem armazenidArmazenNew = pacote.getArmazenidArmazen();
            Fornecedores fornecedorescnpjOld = persistentPacote.getFornecedorescnpj();
            Fornecedores fornecedorescnpjNew = pacote.getFornecedorescnpj();
            Lugarpacote lugarPacoteidLugarPacoteOld = persistentPacote.getLugarPacoteidLugarPacote();
            Lugarpacote lugarPacoteidLugarPacoteNew = pacote.getLugarPacoteidLugarPacote();
            Produto produtoOld = persistentPacote.getProduto();
            Produto produtoNew = pacote.getProduto();
            List<Registro> registroListOld = persistentPacote.getRegistroList();
            List<Registro> registroListNew = pacote.getRegistroList();
            List<String> illegalOrphanMessages = null;
            for (Registro registroListOldRegistro : registroListOld) {
                if (!registroListNew.contains(registroListOldRegistro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registro " + registroListOldRegistro + " since its pacote field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (armazenidArmazenNew != null) {
                armazenidArmazenNew = em.getReference(armazenidArmazenNew.getClass(), armazenidArmazenNew.getIdArmazen());
                pacote.setArmazenidArmazen(armazenidArmazenNew);
            }
            if (fornecedorescnpjNew != null) {
                fornecedorescnpjNew = em.getReference(fornecedorescnpjNew.getClass(), fornecedorescnpjNew.getCnpj());
                pacote.setFornecedorescnpj(fornecedorescnpjNew);
            }
            if (lugarPacoteidLugarPacoteNew != null) {
                lugarPacoteidLugarPacoteNew = em.getReference(lugarPacoteidLugarPacoteNew.getClass(), lugarPacoteidLugarPacoteNew.getLugarpacotePK());
                pacote.setLugarPacoteidLugarPacote(lugarPacoteidLugarPacoteNew);
            }
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdProduto());
                pacote.setProduto(produtoNew);
            }
            List<Registro> attachedRegistroListNew = new ArrayList<Registro>();
            for (Registro registroListNewRegistroToAttach : registroListNew) {
                registroListNewRegistroToAttach = em.getReference(registroListNewRegistroToAttach.getClass(), registroListNewRegistroToAttach.getRegistroPK());
                attachedRegistroListNew.add(registroListNewRegistroToAttach);
            }
            registroListNew = attachedRegistroListNew;
            pacote.setRegistroList(registroListNew);
            pacote = em.merge(pacote);
            if (armazenidArmazenOld != null && !armazenidArmazenOld.equals(armazenidArmazenNew)) {
                armazenidArmazenOld.getPacoteList().remove(pacote);
                armazenidArmazenOld = em.merge(armazenidArmazenOld);
            }
            if (armazenidArmazenNew != null && !armazenidArmazenNew.equals(armazenidArmazenOld)) {
                armazenidArmazenNew.getPacoteList().add(pacote);
                armazenidArmazenNew = em.merge(armazenidArmazenNew);
            }
            if (fornecedorescnpjOld != null && !fornecedorescnpjOld.equals(fornecedorescnpjNew)) {
                fornecedorescnpjOld.getPacoteList().remove(pacote);
                fornecedorescnpjOld = em.merge(fornecedorescnpjOld);
            }
            if (fornecedorescnpjNew != null && !fornecedorescnpjNew.equals(fornecedorescnpjOld)) {
                fornecedorescnpjNew.getPacoteList().add(pacote);
                fornecedorescnpjNew = em.merge(fornecedorescnpjNew);
            }
            if (lugarPacoteidLugarPacoteOld != null && !lugarPacoteidLugarPacoteOld.equals(lugarPacoteidLugarPacoteNew)) {
                lugarPacoteidLugarPacoteOld.getPacoteList().remove(pacote);
                lugarPacoteidLugarPacoteOld = em.merge(lugarPacoteidLugarPacoteOld);
            }
            if (lugarPacoteidLugarPacoteNew != null && !lugarPacoteidLugarPacoteNew.equals(lugarPacoteidLugarPacoteOld)) {
                lugarPacoteidLugarPacoteNew.getPacoteList().add(pacote);
                lugarPacoteidLugarPacoteNew = em.merge(lugarPacoteidLugarPacoteNew);
            }
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getPacoteList().remove(pacote);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getPacoteList().add(pacote);
                produtoNew = em.merge(produtoNew);
            }
            for (Registro registroListNewRegistro : registroListNew) {
                if (!registroListOld.contains(registroListNewRegistro)) {
                    Pacote oldPacoteOfRegistroListNewRegistro = registroListNewRegistro.getPacote();
                    registroListNewRegistro.setPacote(pacote);
                    registroListNewRegistro = em.merge(registroListNewRegistro);
                    if (oldPacoteOfRegistroListNewRegistro != null && !oldPacoteOfRegistroListNewRegistro.equals(pacote)) {
                        oldPacoteOfRegistroListNewRegistro.getRegistroList().remove(registroListNewRegistro);
                        oldPacoteOfRegistroListNewRegistro = em.merge(oldPacoteOfRegistroListNewRegistro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PacotePK id = pacote.getPacotePK();
                if (findPacote(id) == null) {
                    throw new NonexistentEntityException("The pacote with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PacotePK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pacote pacote;
            try {
                pacote = em.getReference(Pacote.class, id);
                pacote.getPacotePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pacote with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Registro> registroListOrphanCheck = pacote.getRegistroList();
            for (Registro registroListOrphanCheckRegistro : registroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pacote (" + pacote + ") cannot be destroyed since the Registro " + registroListOrphanCheckRegistro + " in its registroList field has a non-nullable pacote field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Armazem armazenidArmazen = pacote.getArmazenidArmazen();
            if (armazenidArmazen != null) {
                armazenidArmazen.getPacoteList().remove(pacote);
                armazenidArmazen = em.merge(armazenidArmazen);
            }
            Fornecedores fornecedorescnpj = pacote.getFornecedorescnpj();
            if (fornecedorescnpj != null) {
                fornecedorescnpj.getPacoteList().remove(pacote);
                fornecedorescnpj = em.merge(fornecedorescnpj);
            }
            Lugarpacote lugarPacoteidLugarPacote = pacote.getLugarPacoteidLugarPacote();
            if (lugarPacoteidLugarPacote != null) {
                lugarPacoteidLugarPacote.getPacoteList().remove(pacote);
                lugarPacoteidLugarPacote = em.merge(lugarPacoteidLugarPacote);
            }
            Produto produto = pacote.getProduto();
            if (produto != null) {
                produto.getPacoteList().remove(pacote);
                produto = em.merge(produto);
            }
            em.remove(pacote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pacote> findPacoteEntities() {
        return findPacoteEntities(true, -1, -1);
    }

    public List<Pacote> findPacoteEntities(int maxResults, int firstResult) {
        return findPacoteEntities(false, maxResults, firstResult);
    }

    private List<Pacote> findPacoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pacote.class));
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

    public Pacote findPacote(PacotePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pacote.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pacote> rt = cq.from(Pacote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
