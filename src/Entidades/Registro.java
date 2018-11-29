/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fabricio
 */
@Entity
@Table(name = "registro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r")
    , @NamedQuery(name = "Registro.findByIdRegistro", query = "SELECT r FROM Registro r WHERE r.registroPK.idRegistro = :idRegistro")
    , @NamedQuery(name = "Registro.findByData", query = "SELECT r FROM Registro r WHERE r.data = :data")
    , @NamedQuery(name = "Registro.findByHora", query = "SELECT r FROM Registro r WHERE r.hora = :hora")
    , @NamedQuery(name = "Registro.findByTipoReg", query = "SELECT r FROM Registro r WHERE r.tipoReg = :tipoReg")
    , @NamedQuery(name = "Registro.findByEquipamentoidEquipamento", query = "SELECT r FROM Registro r WHERE r.registroPK.equipamentoidEquipamento = :equipamentoidEquipamento")
    , @NamedQuery(name = "Registro.findByFuncionariocpf", query = "SELECT r FROM Registro r WHERE r.registroPK.funcionariocpf = :funcionariocpf")
    , @NamedQuery(name = "Registro.findByPacoteidPacote", query = "SELECT r FROM Registro r WHERE r.registroPK.pacoteidPacote = :pacoteidPacote")})
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RegistroPK registroPK;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Basic(optional = false)
    @Column(name = "tipo_reg")
    private String tipoReg;
    @JoinColumn(name = "Equipamento_idEquipamento", referencedColumnName = "idEquipamento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Equipamento equipamento;
    @JoinColumn(name = "Funcionario_cpf", referencedColumnName = "cpf", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Funcionario funcionario;
    @JoinColumn(name = "Pacote_idPacote", referencedColumnName = "idPacote", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pacote pacote;

    public Registro() {
    }

    public Registro(RegistroPK registroPK) {
        this.registroPK = registroPK;
    }

    public Registro(RegistroPK registroPK, Date data, Date hora, String tipoReg) {
        this.registroPK = registroPK;
        this.data = data;
        this.hora = hora;
        this.tipoReg = tipoReg;
    }

    public Registro(int idRegistro, int equipamentoidEquipamento, int funcionariocpf, int pacoteidPacote) {
        this.registroPK = new RegistroPK(idRegistro, equipamentoidEquipamento, funcionariocpf, pacoteidPacote);
    }

    public RegistroPK getRegistroPK() {
        return registroPK;
    }

    public void setRegistroPK(RegistroPK registroPK) {
        this.registroPK = registroPK;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getTipoReg() {
        return tipoReg;
    }

    public void setTipoReg(String tipoReg) {
        this.tipoReg = tipoReg;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registroPK != null ? registroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.registroPK == null && other.registroPK != null) || (this.registroPK != null && !this.registroPK.equals(other.registroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Registro[ registroPK=" + registroPK + " ]";
    }
    
}
