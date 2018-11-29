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
public class RegistroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idRegistro")
    private int idRegistro;
    @Basic(optional = false)
    @Column(name = "Equipamento_idEquipamento")
    private int equipamentoidEquipamento;
    @Basic(optional = false)
    @Column(name = "Funcionario_cpf")
    private int funcionariocpf;
    @Basic(optional = false)
    @Column(name = "Pacote_idPacote")
    private int pacoteidPacote;

    public RegistroPK() {
    }

    public RegistroPK(int idRegistro, int equipamentoidEquipamento, int funcionariocpf, int pacoteidPacote) {
        this.idRegistro = idRegistro;
        this.equipamentoidEquipamento = equipamentoidEquipamento;
        this.funcionariocpf = funcionariocpf;
        this.pacoteidPacote = pacoteidPacote;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getEquipamentoidEquipamento() {
        return equipamentoidEquipamento;
    }

    public void setEquipamentoidEquipamento(int equipamentoidEquipamento) {
        this.equipamentoidEquipamento = equipamentoidEquipamento;
    }

    public int getFuncionariocpf() {
        return funcionariocpf;
    }

    public void setFuncionariocpf(int funcionariocpf) {
        this.funcionariocpf = funcionariocpf;
    }

    public int getPacoteidPacote() {
        return pacoteidPacote;
    }

    public void setPacoteidPacote(int pacoteidPacote) {
        this.pacoteidPacote = pacoteidPacote;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRegistro;
        hash += (int) equipamentoidEquipamento;
        hash += (int) funcionariocpf;
        hash += (int) pacoteidPacote;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroPK)) {
            return false;
        }
        RegistroPK other = (RegistroPK) object;
        if (this.idRegistro != other.idRegistro) {
            return false;
        }
        if (this.equipamentoidEquipamento != other.equipamentoidEquipamento) {
            return false;
        }
        if (this.funcionariocpf != other.funcionariocpf) {
            return false;
        }
        if (this.pacoteidPacote != other.pacoteidPacote) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroPK[ idRegistro=" + idRegistro + ", equipamentoidEquipamento=" + equipamentoidEquipamento + ", funcionariocpf=" + funcionariocpf + ", pacoteidPacote=" + pacoteidPacote + " ]";
    }
    
}
