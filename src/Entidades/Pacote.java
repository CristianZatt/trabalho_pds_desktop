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
@Table(name = "pacote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pacote.findAll", query = "SELECT p FROM Pacote p")
    , @NamedQuery(name = "Pacote.findByIdPacote", query = "SELECT p FROM Pacote p WHERE p.pacotePK.idPacote = :idPacote")
    , @NamedQuery(name = "Pacote.findByQuantidade", query = "SELECT p FROM Pacote p WHERE p.quantidade = :quantidade")
    , @NamedQuery(name = "Pacote.findByLote", query = "SELECT p FROM Pacote p WHERE p.lote = :lote")
    , @NamedQuery(name = "Pacote.findByPreco", query = "SELECT p FROM Pacote p WHERE p.preco = :preco")
    , @NamedQuery(name = "Pacote.findByTamanho", query = "SELECT p FROM Pacote p WHERE p.tamanho = :tamanho")
    , @NamedQuery(name = "Pacote.findByAprovacao", query = "SELECT p FROM Pacote p WHERE p.aprovacao = :aprovacao")
    , @NamedQuery(name = "Pacote.findByProdutoidProduto", query = "SELECT p FROM Pacote p WHERE p.pacotePK.produtoidProduto = :produtoidProduto")})
public class Pacote implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PacotePK pacotePK;
    @Basic(optional = false)
    @Column(name = "quantidade")
    private int quantidade;
    @Basic(optional = false)
    @Column(name = "lote")
    private String lote;
    @Basic(optional = false)
    @Column(name = "preco")
    private float preco;
    @Basic(optional = false)
    @Column(name = "tamanho")
    private float tamanho;
    @Basic(optional = false)
    @Column(name = "aprovacao")
    private boolean aprovacao;
    @JoinColumn(name = "Armazen_idArmazen", referencedColumnName = "idArmazen")
    @ManyToOne(optional = false)
    private Armazem armazenidArmazen;
    @JoinColumn(name = "Fornecedores_cnpj", referencedColumnName = "cnpj")
    @ManyToOne(optional = false)
    private Fornecedores fornecedorescnpj;
    @JoinColumn(name = "LugarPacote_idLugarPacote", referencedColumnName = "idLugarPacote")
    @ManyToOne(optional = false)
    private Lugarpacote lugarPacoteidLugarPacote;
    @JoinColumn(name = "Produto_idProduto", referencedColumnName = "idProduto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produto produto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacote")
    private List<Registro> registroList;

    public Pacote() {
    }

    public Pacote(PacotePK pacotePK) {
        this.pacotePK = pacotePK;
    }

    public Pacote(PacotePK pacotePK, int quantidade, String lote, float preco, float tamanho, boolean aprovacao) {
        this.pacotePK = pacotePK;
        this.quantidade = quantidade;
        this.lote = lote;
        this.preco = preco;
        this.tamanho = tamanho;
        this.aprovacao = aprovacao;
    }

    public Pacote(int idPacote, int produtoidProduto) {
        this.pacotePK = new PacotePK(idPacote, produtoidProduto);
    }

    public PacotePK getPacotePK() {
        return pacotePK;
    }

    public void setPacotePK(PacotePK pacotePK) {
        this.pacotePK = pacotePK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    public boolean getAprovacao() {
        return aprovacao;
    }

    public void setAprovacao(boolean aprovacao) {
        this.aprovacao = aprovacao;
    }

    public Armazem getArmazenidArmazen() {
        return armazenidArmazen;
    }

    public void setArmazenidArmazen(Armazem armazenidArmazen) {
        this.armazenidArmazen = armazenidArmazen;
    }

    public Fornecedores getFornecedorescnpj() {
        return fornecedorescnpj;
    }

    public void setFornecedorescnpj(Fornecedores fornecedorescnpj) {
        this.fornecedorescnpj = fornecedorescnpj;
    }

    public Lugarpacote getLugarPacoteidLugarPacote() {
        return lugarPacoteidLugarPacote;
    }

    public void setLugarPacoteidLugarPacote(Lugarpacote lugarPacoteidLugarPacote) {
        this.lugarPacoteidLugarPacote = lugarPacoteidLugarPacote;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @XmlTransient
    public List<Registro> getRegistroList() {
        return registroList;
    }

    public void setRegistroList(List<Registro> registroList) {
        this.registroList = registroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pacotePK != null ? pacotePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pacote)) {
            return false;
        }
        Pacote other = (Pacote) object;
        if ((this.pacotePK == null && other.pacotePK != null) || (this.pacotePK != null && !this.pacotePK.equals(other.pacotePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pacote[ pacotePK=" + pacotePK + " ]";
    }
    
}
