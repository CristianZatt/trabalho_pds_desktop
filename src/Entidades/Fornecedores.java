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
@Table(name = "fornecedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fornecedores.findAll", query = "SELECT f FROM Fornecedores f")
    , @NamedQuery(name = "Fornecedores.findByCnpj", query = "SELECT f FROM Fornecedores f WHERE f.cnpj = :cnpj")
    , @NamedQuery(name = "Fornecedores.findByEndereco", query = "SELECT f FROM Fornecedores f WHERE f.endereco = :endereco")
    , @NamedQuery(name = "Fornecedores.findByNome", query = "SELECT f FROM Fornecedores f WHERE f.nome = :nome")
    , @NamedQuery(name = "Fornecedores.findByTelefone", query = "SELECT f FROM Fornecedores f WHERE f.telefone = :telefone")})
public class Fornecedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cnpj")
    private Integer cnpj;
    @Basic(optional = false)
    @Column(name = "endereco")
    private String endereco;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "telefone")
    private String telefone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fornecedorescnpj")
    private List<Pacote> pacoteList;

    public Fornecedores() {
    }

    public Fornecedores(Integer cnpj) {
        this.cnpj = cnpj;
    }

    public Fornecedores(Integer cnpj, String endereco, String nome, String telefone) {
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Integer getCnpj() {
        return cnpj;
    }

    public void setCnpj(Integer cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
        hash += (cnpj != null ? cnpj.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fornecedores)) {
            return false;
        }
        Fornecedores other = (Fornecedores) object;
        if ((this.cnpj == null && other.cnpj != null) || (this.cnpj != null && !this.cnpj.equals(other.cnpj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Fornecedores[ cnpj=" + cnpj + " ]";
    }
    
}
