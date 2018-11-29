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
@Table(name = "armazen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Armazen.findAll", query = "SELECT a FROM Armazen a")
    , @NamedQuery(name = "Armazen.findByIdArmazen", query = "SELECT a FROM Armazen a WHERE a.idArmazen = :idArmazen")
    , @NamedQuery(name = "Armazen.findByEndereco", query = "SELECT a FROM Armazen a WHERE a.endereco = :endereco")
    , @NamedQuery(name = "Armazen.findByEmpresa", query = "SELECT a FROM Armazen a WHERE a.empresa = :empresa")
    , @NamedQuery(name = "Armazen.findByContato", query = "SELECT a FROM Armazen a WHERE a.contato = :contato")
    , @NamedQuery(name = "Armazen.findByTipo", query = "SELECT a FROM Armazen a WHERE a.tipo = :tipo")
    , @NamedQuery(name = "Armazen.findByTamanho", query = "SELECT a FROM Armazen a WHERE a.tamanho = :tamanho")})
public class Armazem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArmazen")
    private Integer idArmazen;
    @Basic(optional = false)
    @Column(name = "endereco")
    private String endereco;
    @Basic(optional = false)
    @Column(name = "empresa")
    private String empresa;
    @Basic(optional = false)
    @Column(name = "contato")
    private String contato;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "tamanho")
    private float tamanho;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "armazenidArmazen")
    private List<Setorcarregamento> setorcarregamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "armazenidArmazen")
    private List<Pacote> pacoteList;

    public Armazem() {
    }

    public Armazem(Integer idArmazen) {
        this.idArmazen = idArmazen;
    }

    public Armazem(Integer idArmazen, String endereco, String empresa, String contato, String tipo, float tamanho) {
        this.idArmazen = idArmazen;
        this.endereco = endereco;
        this.empresa = empresa;
        this.contato = contato;
        this.tipo = tipo;
        this.tamanho = tamanho;
    }

    public Integer getIdArmazen() {
        return idArmazen;
    }

    public void setIdArmazen(Integer idArmazen) {
        this.idArmazen = idArmazen;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    @XmlTransient
    public List<Setorcarregamento> getSetorcarregamentoList() {
        return setorcarregamentoList;
    }

    public void setSetorcarregamentoList(List<Setorcarregamento> setorcarregamentoList) {
        this.setorcarregamentoList = setorcarregamentoList;
    }

    @XmlTransient
    public List<Pacote> getPacoteList() {
        return pacoteList;
    }

    public void setPacoteList(List<Pacote> pacoteList) {
        this.pacoteList = pacoteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArmazen != null ? idArmazen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Armazem)) {
            return false;
        }
        Armazem other = (Armazem) object;
        if ((this.idArmazen == null && other.idArmazen != null) || (this.idArmazen != null && !this.idArmazen.equals(other.idArmazen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Armazen[ idArmazen=" + idArmazen + " ]";
    }
    
}
