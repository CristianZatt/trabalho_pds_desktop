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
public class PrateleiraPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idPrateleira")
    private int idPrateleira;
    @Basic(optional = false)
    @Column(name = "Rua_idRua")
    private int ruaidRua;
    @Basic(optional = false)
    @Column(name = "Bloco_idBloco")
    private int blocoidBloco;

    public PrateleiraPK() {
    }

    public PrateleiraPK(int idPrateleira, int ruaidRua, int blocoidBloco) {
        this.idPrateleira = idPrateleira;
        this.ruaidRua = ruaidRua;
        this.blocoidBloco = blocoidBloco;
    }

    public int getIdPrateleira() {
        return idPrateleira;
    }

    public void setIdPrateleira(int idPrateleira) {
        this.idPrateleira = idPrateleira;
    }

    public int getRuaidRua() {
        return ruaidRua;
    }

    public void setRuaidRua(int ruaidRua) {
        this.ruaidRua = ruaidRua;
    }

    public int getBlocoidBloco() {
        return blocoidBloco;
    }

    public void setBlocoidBloco(int blocoidBloco) {
        this.blocoidBloco = blocoidBloco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPrateleira;
        hash += (int) ruaidRua;
        hash += (int) blocoidBloco;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrateleiraPK)) {
            return false;
        }
        PrateleiraPK other = (PrateleiraPK) object;
        if (this.idPrateleira != other.idPrateleira) {
            return false;
        }
        if (this.ruaidRua != other.ruaidRua) {
            return false;
        }
        if (this.blocoidBloco != other.blocoidBloco) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PrateleiraPK[ idPrateleira=" + idPrateleira + ", ruaidRua=" + ruaidRua + ", blocoidBloco=" + blocoidBloco + " ]";
    }
    
}
