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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "rua")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rua.findAll", query = "SELECT r FROM Rua r")
    , @NamedQuery(name = "Rua.findByIdRua", query = "SELECT r FROM Rua r WHERE r.idRua = :idRua")
    , @NamedQuery(name = "Rua.findByNome", query = "SELECT r FROM Rua r WHERE r.nome = :nome")})
public class Rua implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRua")
    private Integer idRua;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rua")
    private List<Prateleira> prateleiraList;

    public Rua() {
    }

    public Rua(Integer idRua) {
        this.idRua = idRua;
    }

    public Rua(Integer idRua, String nome) {
        this.idRua = idRua;
        this.nome = nome;
    }

    public Integer getIdRua() {
        return idRua;
    }

    public void setIdRua(Integer idRua) {
        this.idRua = idRua;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<Prateleira> getPrateleiraList() {
        return prateleiraList;
    }

    public void setPrateleiraList(List<Prateleira> prateleiraList) {
        this.prateleiraList = prateleiraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRua != null ? idRua.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rua)) {
            return false;
        }
        Rua other = (Rua) object;
        if ((this.idRua == null && other.idRua != null) || (this.idRua != null && !this.idRua.equals(other.idRua))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Rua[ idRua=" + idRua + " ]";
    }
    
}
