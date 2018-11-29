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
@Table(name = "produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p")
    , @NamedQuery(name = "Produto.findByIdProduto", query = "SELECT p FROM Produto p WHERE p.idProduto = :idProduto")
    , @NamedQuery(name = "Produto.findByNome", query = "SELECT p FROM Produto p WHERE p.nome = :nome")
    , @NamedQuery(name = "Produto.findByPeso", query = "SELECT p FROM Produto p WHERE p.peso = :peso")
    , @NamedQuery(name = "Produto.findByOrigem", query = "SELECT p FROM Produto p WHERE p.origem = :origem")
    , @NamedQuery(name = "Produto.findByTipo", query = "SELECT p FROM Produto p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Produto.findByValidade", query = "SELECT p FROM Produto p WHERE p.validade = :validade")
    , @NamedQuery(name = "Produto.findByRefrigerado", query = "SELECT p FROM Produto p WHERE p.refrigerado = :refrigerado")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProduto")
    private Integer idProduto;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "peso")
    private float peso;
    @Basic(optional = false)
    @Column(name = "origem")
    private String origem;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "validade")
    private boolean validade;
    @Basic(optional = false)
    @Column(name = "refrigerado")
    private boolean refrigerado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<Pacote> pacoteList;

    public Produto() {
    }

    public Produto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Produto(Integer idProduto, String nome, float peso, String origem, String tipo, boolean validade, boolean refrigerado) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.peso = peso;
        this.origem = origem;
        this.tipo = tipo;
        this.validade = validade;
        this.refrigerado = refrigerado;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean getValidade() {
        return validade;
    }

    public void setValidade(boolean validade) {
        this.validade = validade;
    }

    public boolean getRefrigerado() {
        return refrigerado;
    }

    public void setRefrigerado(boolean refrigerado) {
        this.refrigerado = refrigerado;
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
        hash += (idProduto != null ? idProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.idProduto == null && other.idProduto != null) || (this.idProduto != null && !this.idProduto.equals(other.idProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Produto[ idProduto=" + idProduto + " ]";
    }
    
}
