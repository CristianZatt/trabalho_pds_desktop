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
@Table(name = "bloco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bloco.findAll", query = "SELECT b FROM Bloco b")
    , @NamedQuery(name = "Bloco.findByIdBloco", query = "SELECT b FROM Bloco b WHERE b.idBloco = :idBloco")
    , @NamedQuery(name = "Bloco.findByNumero", query = "SELECT b FROM Bloco b WHERE b.numero = :numero")
    , @NamedQuery(name = "Bloco.findByLado", query = "SELECT b FROM Bloco b WHERE b.lado = :lado")
    , @NamedQuery(name = "Bloco.findByMaxColuna", query = "SELECT b FROM Bloco b WHERE b.maxColuna = :maxColuna")
    , @NamedQuery(name = "Bloco.findByMaxNivel", query = "SELECT b FROM Bloco b WHERE b.maxNivel = :maxNivel")})
public class Bloco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBloco")
    private Integer idBloco;
    @Basic(optional = false)
    @Column(name = "numero")
    private int numero;
    @Basic(optional = false)
    @Column(name = "lado")
    private String lado;
    @Basic(optional = false)
    @Column(name = "maxColuna")
    private int maxColuna;
    @Basic(optional = false)
    @Column(name = "maxNivel")
    private int maxNivel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloco")
    private List<Prateleira> prateleiraList;

    public Bloco() {
    }

    public Bloco(Integer idBloco) {
        this.idBloco = idBloco;
    }

    public Bloco(Integer idBloco, int numero, String lado, int maxColuna, int maxNivel) {
        this.idBloco = idBloco;
        this.numero = numero;
        this.lado = lado;
        this.maxColuna = maxColuna;
        this.maxNivel = maxNivel;
    }

    public Integer getIdBloco() {
        return idBloco;
    }

    public void setIdBloco(Integer idBloco) {
        this.idBloco = idBloco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLado() {
        return lado;
    }

    public void setLado(String lado) {
        this.lado = lado;
    }

    public int getMaxColuna() {
        return maxColuna;
    }

    public void setMaxColuna(int maxColuna) {
        this.maxColuna = maxColuna;
    }

    public int getMaxNivel() {
        return maxNivel;
    }

    public void setMaxNivel(int maxNivel) {
        this.maxNivel = maxNivel;
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
        hash += (idBloco != null ? idBloco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloco)) {
            return false;
        }
        Bloco other = (Bloco) object;
        if ((this.idBloco == null && other.idBloco != null) || (this.idBloco != null && !this.idBloco.equals(other.idBloco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Bloco[ idBloco=" + idBloco + " ]";
    }
    
}
