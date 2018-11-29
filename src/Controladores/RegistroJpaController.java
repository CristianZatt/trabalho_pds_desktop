/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Equipamento;
import Entidades.Funcionario;
import Entidades.Pacote;
import Entidades.Registro;
import Entidades.RegistroPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fabricio
 */
public class RegistroJpaController implements Serializable {

    public RegistroJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("PDS_ArmazemPU"); //nome da unidade de persistencia (persistence.xml)
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Registro registro) throws PreexistingEntityException, Exception {
        if (registro.getRegistroPK() == null) {
            registro.setRegistroPK(new RegistroPK());
        }
        registro.getRegistroPK().setPacoteidPacote(registro.getPacote().getPacotePK().getIdPacote());
        registro.getRegistroPK().setEquipamentoidEquipamento(registro.getEquipamento().getIdEquipamento());
        registro.getRegistroPK().setFuncionariocpf(registro.getFuncionario().getCpf());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipamento equipamento = registro.getEquipamento();
            if (equipamento != null) {
                equipamento = em.getReference(equipamento.getClass(), equipamento.getIdEquipamento());
                registro.setEquipamento(equipamento);
            }
            Funcionario funcionario = registro.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getCpf());
                registro.setFuncionario(funcionario);
            }
            Pacote pacote = registro.getPacote();
            if (pacote != null) {
                pacote = em.getReference(pacote.getClass(), pacote.getPacotePK());
                registro.setPacote(pacote);
            }
            em.persist(registro);
            if (equipamento != null) {
                equipamento.getRegistroList().add(registro);
                equipamento = em.merge(equipamento);
            }
            if (funcionario != null) {
                funcionario.getRegistroList().add(registro);
                funcionario = em.merge(funcionario);
            }
            if (pacote != null) {
                pacote.getRegistroList().add(registro);
                pacote = em.merge(pacote);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegistro(registro.getRegistroPK()) != null) {
                throw new PreexistingEntityException("Registro " + registro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Registro registro) throws NonexistentEntityException, Exception {
        registro.getRegistroPK().setPacoteidPacote(registro.getPacote().getPacotePK().getIdPacote());
        registro.getRegistroPK().setEquipamentoidEquipamento(registro.getEquipamento().getIdEquipamento());
        registro.getRegistroPK().setFuncionariocpf(registro.getFuncionario().getCpf());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Registro persistentRegistro = em.find(Registro.class, registro.getRegistroPK());
            Equipamento equipamentoOld = persistentRegistro.getEquipamento();
            Equipamento equipamentoNew = registro.getEquipamento();
            Funcionario funcionarioOld = persistentRegistro.getFuncionario();
            Funcionario funcionarioNew = registro.getFuncionario();
            Pacote pacoteOld = persistentRegistro.getPacote();
            Pacote pacoteNew = registro.getPacote();
            if (equipamentoNew != null) {
                equipamentoNew = em.getReference(equipamentoNew.getClass(), equipamentoNew.getIdEquipamento());
                registro.setEquipamento(equipamentoNew);
            }
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getCpf());
                registro.setFuncionario(funcionarioNew);
            }
            if (pacoteNew != null) {
                pacoteNew = em.getReference(pacoteNew.getClass(), pacoteNew.getPacotePK());
                registro.setPacote(pacoteNew);
            }
            registro = em.merge(registro);
            if (equipamentoOld != null && !equipamentoOld.equals(equipamentoNew)) {
                equipamentoOld.getRegistroList().remove(registro);
                equipamentoOld = em.merge(equipamentoOld);
            }
            if (equipamentoNew != null && !equipamentoNew.equals(equipamentoOld)) {
                equipamentoNew.getRegistroList().add(registro);
                equipamentoNew = em.merge(equipamentoNew);
            }
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.getRegistroList().remove(registro);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.getRegistroList().add(registro);
                funcionarioNew = em.merge(funcionarioNew);
            }
            if (pacoteOld != null && !pacoteOld.equals(pacoteNew)) {
                pacoteOld.getRegistroList().remove(registro);
                pacoteOld = em.merge(pacoteOld);
            }
            if (pacoteNew != null && !pacoteNew.equals(pacoteOld)) {
                pacoteNew.getRegistroList().add(registro);
                pacoteNew = em.merge(pacoteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RegistroPK id = registro.getRegistroPK();
                if (findRegistro(id) == null) {
                    throw new NonexistentEntityException("The registro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RegistroPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Registro registro;
            try {
                registro = em.getReference(Registro.class, id);
                registro.getRegistroPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registro with id " + id + " no longer exists.", enfe);
            }
            Equipamento equipamento = registro.getEquipamento();
            if (equipamento != null) {
                equipamento.getRegistroList().remove(registro);
                equipamento = em.merge(equipamento);
            }
            Funcionario funcionario = registro.getFuncionario();
            if (funcionario != null) {
                funcionario.getRegistroList().remove(registro);
                funcionario = em.merge(funcionario);
            }
            Pacote pacote = registro.getPacote();
            if (pacote != null) {
                pacote.getRegistroList().remove(registro);
                pacote = em.merge(pacote);
            }
            em.remove(registro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Registro> findRegistroEntities() {
        return findRegistroEntities(true, -1, -1);
    }

    public List<Registro> findRegistroEntities(int maxResults, int firstResult) {
        return findRegistroEntities(false, maxResults, firstResult);
    }

    private List<Registro> findRegistroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Registro.class));
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

    public Registro findRegistro(RegistroPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Registro.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Registro> rt = cq.from(Registro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
