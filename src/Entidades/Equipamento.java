/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fabricio
 */
@Entity
@Table(name = "equipamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipamento.findAll", query = "SELECT e FROM Equipamento e")
    , @NamedQuery(name = "Equipamento.findByIdEquipamento", query = "SELECT e FROM Equipamento e WHERE e.idEquipamento = :idEquipamento")
    , @NamedQuery(name = "Equipamento.findByDataUltimaManutencao", query = "SELECT e FROM Equipamento e WHERE e.dataUltimaManutencao = :dataUltimaManutencao")
    , @NamedQuery(name = "Equipamento.findByDataProximaManutencao", query = "SELECT e FROM Equipamento e WHERE e.dataProximaManutencao = :dataProximaManutencao")
    , @NamedQuery(name = "Equipamento.findBySetor", query = "SELECT e FROM Equipamento e WHERE e.setor = :setor")})
public class Equipamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEquipamento")
    private Integer idEquipamento;
    @Basic(optional = false)
    @Column(name = "dataUltimaManutencao")
    @Temporal(TemporalType.DATE)
    private Date dataUltimaManutencao;
    @Basic(optional = false)
    @Column(name = "dataProximaManutencao")
    @Temporal(TemporalType.DATE)
    private Date dataProximaManutencao;
    @Basic(optional = false)
    @Column(name = "setor")
    private String setor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipamento")
    private List<Registro> registroList;

    public Equipamento() {
    }

    public Equipamento(Integer idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Equipamento(Integer idEquipamento, Date dataUltimaManutencao, Date dataProximaManutencao, String setor) {
        this.idEquipamento = idEquipamento;
        this.dataUltimaManutencao = dataUltimaManutencao;
        this.dataProximaManutencao = dataProximaManutencao;
        this.setor = setor;
    }

    public Integer getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Integer idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Date getDataUltimaManutencao() {
        return dataUltimaManutencao;
    }

    public void setDataUltimaManutencao(Date dataUltimaManutencao) {
        this.dataUltimaManutencao = dataUltimaManutencao;
    }

    public Date getDataProximaManutencao() {
        return dataProximaManutencao;
    }

    public void setDataProximaManutencao(Date dataProximaManutencao) {
        this.dataProximaManutencao = dataProximaManutencao;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
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
        hash += (idEquipamento != null ? idEquipamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipamento)) {
            return false;
        }
        Equipamento other = (Equipamento) object;
        if ((this.idEquipamento == null && other.idEquipamento != null) || (this.idEquipamento != null && !this.idEquipamento.equals(other.idEquipamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Equipamento[ idEquipamento=" + idEquipamento + " ]";
    }
    
}
