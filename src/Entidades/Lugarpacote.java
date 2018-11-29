/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fabricio
 */
@Entity
@Table(name = "lugarpacote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lugarpacote.findAll", query = "SELECT l FROM Lugarpacote l")
    , @NamedQuery(name = "Lugarpacote.findByIdLugarPacote", query = "SELECT l FROM Lugarpacote l WHERE l.lugarpacotePK.idLugarPacote = :idLugarPacote")
    , @NamedQuery(name = "Lugarpacote.findByNumColuna", query = "SELECT l FROM Lugarpacote l WHERE l.numColuna = :numColuna")
    , @NamedQuery(name = "Lugarpacote.findByNumNivel", query = "SELECT l FROM Lugarpacote l WHERE l.numNivel = :numNivel")
    , @NamedQuery(name = "Lugarpacote.findByStatus", query = "SELECT l FROM Lugarpacote l WHERE l.status = :status")
    , @NamedQuery(name = "Lugarpacote.findByPrateleiraidPrateleira", query = "SELECT l FROM Lugarpacote l WHERE l.lugarpacotePK.prateleiraidPrateleira = :prateleiraidPrateleira")})
public class Lugarpacote implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LugarpacotePK lugarpacotePK;
    @Basic(optional = false)
    @Column(name = "numColuna")
    private int numColuna;
    @Basic(optional = false)
    @Column(name = "numNivel")
    private int numNivel;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lugarPacoteidLugarPacote")
    private List<Pacote> pacoteList;
    @JoinColumn(name = "Prateleira_idPrateleira", referencedColumnName = "idPrateleira", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Prateleira prateleira;

    public Lugarpacote() {
    }

    public Lugarpacote(LugarpacotePK lugarpacotePK) {
        this.lugarpacotePK = lugarpacotePK;
    }

    public Lugarpacote(LugarpacotePK lugarpacotePK, int numColuna, int numNivel, String status) {
        this.lugarpacotePK = lugarpacotePK;
        this.numColuna = numColuna;
        this.numNivel = numNivel;
        this.status = status;
    }

    public Lugarpacote(int idLugarPacote, int prateleiraidPrateleira) {
        this.lugarpacotePK = new LugarpacotePK(idLugarPacote, prateleiraidPrateleira);
    }

    public LugarpacotePK getLugarpacotePK() {
        return lugarpacotePK;
    }

    public void setLugarpacotePK(LugarpacotePK lugarpacotePK) {
        this.lugarpacotePK = lugarpacotePK;
    }

    public int getNumColuna() {
        return numColuna;
    }

    public void setNumColuna(int numColuna) {
        this.numColuna = numColuna;
    }

    public int getNumNivel() {
        return numNivel;
    }

    public void setNumNivel(int numNivel) {
        this.numNivel = numNivel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<Pacote> getPacoteList() {
        return pacoteList;
    }

    public void setPacoteList(List<Pacote> pacoteList) {
        this.pacoteList = pacoteList;
    }

    public Prateleira getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(Prateleira prateleira) {
        this.prateleira = prateleira;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lugarpacotePK != null ? lugarpacotePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lugarpacote)) {
            return false;
        }
        Lugarpacote other = (Lugarpacote) object;
        if ((this.lugarpacotePK == null && other.lugarpacotePK != null) || (this.lugarpacotePK != null && !this.lugarpacotePK.equals(other.lugarpacotePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Lugarpacote[ lugarpacotePK=" + lugarpacotePK + " ]";
    }
    
}
