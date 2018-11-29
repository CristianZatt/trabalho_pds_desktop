/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fabricio
 */
@Entity
@Table(name = "setorcarregamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Setorcarregamento.findAll", query = "SELECT s FROM Setorcarregamento s")
    , @NamedQuery(name = "Setorcarregamento.findByIdSetorCarregamento", query = "SELECT s FROM Setorcarregamento s WHERE s.idSetorCarregamento = :idSetorCarregamento")
    , @NamedQuery(name = "Setorcarregamento.findByNumSetor", query = "SELECT s FROM Setorcarregamento s WHERE s.numSetor = :numSetor")
    , @NamedQuery(name = "Setorcarregamento.findByDescricao", query = "SELECT s FROM Setorcarregamento s WHERE s.descricao = :descricao")})
public class Setorcarregamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSetorCarregamento")
    private Integer idSetorCarregamento;
    @Basic(optional = false)
    @Column(name = "numSetor")
    private int numSetor;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "Armazen_idArmazen", referencedColumnName = "idArmazen")
    @ManyToOne(optional = false)
    private Armazem armazenidArmazen;

    public Setorcarregamento() {
    }

    public Setorcarregamento(Integer idSetorCarregamento) {
        this.idSetorCarregamento = idSetorCarregamento;
    }

    public Setorcarregamento(Integer idSetorCarregamento, int numSetor, String descricao) {
        this.idSetorCarregamento = idSetorCarregamento;
        this.numSetor = numSetor;
        this.descricao = descricao;
    }

    public Integer getIdSetorCarregamento() {
        return idSetorCarregamento;
    }

    public void setIdSetorCarregamento(Integer idSetorCarregamento) {
        this.idSetorCarregamento = idSetorCarregamento;
    }

    public int getNumSetor() {
        return numSetor;
    }

    public void setNumSetor(int numSetor) {
        this.numSetor = numSetor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Armazem getArmazenidArmazen() {
        return armazenidArmazen;
    }

    public void setArmazenidArmazen(Armazem armazenidArmazen) {
        this.armazenidArmazen = armazenidArmazen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSetorCarregamento != null ? idSetorCarregamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Setorcarregamento)) {
            return false;
        }
        Setorcarregamento other = (Setorcarregamento) object;
        if ((this.idSetorCarregamento == null && other.idSetorCarregamento != null) || (this.idSetorCarregamento != null && !this.idSetorCarregamento.equals(other.idSetorCarregamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Setorcarregamento[ idSetorCarregamento=" + idSetorCarregamento + " ]";
    }
    
}
