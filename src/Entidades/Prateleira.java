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
@Table(name = "prateleira")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prateleira.findAll", query = "SELECT p FROM Prateleira p")
    , @NamedQuery(name = "Prateleira.findByIdPrateleira", query = "SELECT p FROM Prateleira p WHERE p.prateleiraPK.idPrateleira = :idPrateleira")
    , @NamedQuery(name = "Prateleira.findByCapacidade", query = "SELECT p FROM Prateleira p WHERE p.capacidade = :capacidade")
    , @NamedQuery(name = "Prateleira.findByDescricao", query = "SELECT p FROM Prateleira p WHERE p.descricao = :descricao")
    , @NamedQuery(name = "Prateleira.findByColunas", query = "SELECT p FROM Prateleira p WHERE p.colunas = :colunas")
    , @NamedQuery(name = "Prateleira.findByNiveis", query = "SELECT p FROM Prateleira p WHERE p.niveis = :niveis")
    , @NamedQuery(name = "Prateleira.findByRuaidRua", query = "SELECT p FROM Prateleira p WHERE p.prateleiraPK.ruaidRua = :ruaidRua")
    , @NamedQuery(name = "Prateleira.findByBlocoidBloco", query = "SELECT p FROM Prateleira p WHERE p.prateleiraPK.blocoidBloco = :blocoidBloco")})
public class Prateleira implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrateleiraPK prateleiraPK;
    @Basic(optional = false)
    @Column(name = "capacidade")
    private float capacidade;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "colunas")
    private int colunas;
    @Basic(optional = false)
    @Column(name = "niveis")
    private int niveis;
    @JoinColumn(name = "Bloco_idBloco", referencedColumnName = "idBloco", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bloco bloco;
    @JoinColumn(name = "Rua_idRua", referencedColumnName = "idRua", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rua rua;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prateleira")
    private List<Lugarpacote> lugarpacoteList;

    public Prateleira() {
    }

    public Prateleira(PrateleiraPK prateleiraPK) {
        this.prateleiraPK = prateleiraPK;
    }

    public Prateleira(PrateleiraPK prateleiraPK, float capacidade, String descricao, int colunas, int niveis) {
        this.prateleiraPK = prateleiraPK;
        this.capacidade = capacidade;
        this.descricao = descricao;
        this.colunas = colunas;
        this.niveis = niveis;
    }

    public Prateleira(int idPrateleira, int ruaidRua, int blocoidBloco) {
        this.prateleiraPK = new PrateleiraPK(idPrateleira, ruaidRua, blocoidBloco);
    }

    public PrateleiraPK getPrateleiraPK() {
        return prateleiraPK;
    }

    public void setPrateleiraPK(PrateleiraPK prateleiraPK) {
        this.prateleiraPK = prateleiraPK;
    }

    public float getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(float capacidade) {
        this.capacidade = capacidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getColunas() {
        return colunas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

    public int getNiveis() {
        return niveis;
    }

    public void setNiveis(int niveis) {
        this.niveis = niveis;
    }

    public Bloco getBloco() {
        return bloco;
    }

    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }

    public Rua getRua() {
        return rua;
    }

    public void setRua(Rua rua) {
        this.rua = rua;
    }

    @XmlTransient
    public List<Lugarpacote> getLugarpacoteList() {
        return lugarpacoteList;
    }

    public void setLugarpacoteList(List<Lugarpacote> lugarpacoteList) {
        this.lugarpacoteList = lugarpacoteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prateleiraPK != null ? prateleiraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prateleira)) {
            return false;
        }
        Prateleira other = (Prateleira) object;
        if ((this.prateleiraPK == null && other.prateleiraPK != null) || (this.prateleiraPK != null && !this.prateleiraPK.equals(other.prateleiraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Prateleira[ prateleiraPK=" + prateleiraPK + " ]";
    }
    
}
