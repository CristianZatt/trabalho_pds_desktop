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
public class LugarpacotePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idLugarPacote")
    private int idLugarPacote;
    @Basic(optional = false)
    @Column(name = "Prateleira_idPrateleira")
    private int prateleiraidPrateleira;

    public LugarpacotePK() {
    }

    public LugarpacotePK(int idLugarPacote, int prateleiraidPrateleira) {
        this.idLugarPacote = idLugarPacote;
        this.prateleiraidPrateleira = prateleiraidPrateleira;
    }

    public int getIdLugarPacote() {
        return idLugarPacote;
    }

    public void setIdLugarPacote(int idLugarPacote) {
        this.idLugarPacote = idLugarPacote;
    }

    public int getPrateleiraidPrateleira() {
        return prateleiraidPrateleira;
    }

    public void setPrateleiraidPrateleira(int prateleiraidPrateleira) {
        this.prateleiraidPrateleira = prateleiraidPrateleira;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLugarPacote;
        hash += (int) prateleiraidPrateleira;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LugarpacotePK)) {
            return false;
        }
        LugarpacotePK other = (LugarpacotePK) object;
        if (this.idLugarPacote != other.idLugarPacote) {
            return false;
        }
        if (this.prateleiraidPrateleira != other.prateleiraidPrateleira) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.LugarpacotePK[ idLugarPacote=" + idLugarPacote + ", prateleiraidPrateleira=" + prateleiraidPrateleira + " ]";
    }
    
}
