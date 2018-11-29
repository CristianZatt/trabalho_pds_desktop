/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Fabricio
 */
@Embeddable
public class PacotePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idPacote")
    private int idPacote;
    @Basic(optional = false)
    @Column(name = "Produto_idProduto")
    private int produtoidProduto;

    public PacotePK() {
    }

    public PacotePK(int idPacote, int produtoidProduto) {
        this.idPacote = idPacote;
        this.produtoidProduto = produtoidProduto;
    }

    public int getIdPacote() {
        return idPacote;
    }

    public void setIdPacote(int idPacote) {
        this.idPacote = idPacote;
    }

    public int getProdutoidProduto() {
        return produtoidProduto;
    }

    public void setProdutoidProduto(int produtoidProduto) {
        this.produtoidProduto = produtoidProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPacote;
        hash += (int) produtoidProduto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PacotePK)) {
            return false;
        }
        PacotePK other = (PacotePK) object;
        if (this.idPacote != other.idPacote) {
            return false;
        }
        if (this.produtoidProduto != other.produtoidProduto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PacotePK[ idPacote=" + idPacote + ", produtoidProduto=" + produtoidProduto + " ]";
    }
    
}
