/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Mateus
 */
@Entity
@Table(name="parametros_do_sistema")
public class ParametroConfig implements Serializable{
    @Id
    @GeneratedValue
    private int id;
    private String departamento;
    private int ano;
    @Column(name="carga_min_colaborador")
    private int cargaMinPTemporario;
    @Column(name="carga_max_colaborador")
    private int cargaMaxPTemporario;
    @Column(name="carga_min_fixo")
    private int cargaMinPFixo;
    @Column(name="carga_max_fixo")
    private int cargaMaxPFixo;
    @Column(name="carga_min_adm")
    private int cargaMinAdm;
    @Column(name="carga_max_adm")
    private int cargaMaxAdm;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
    
    
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getCargaMinPTemporario() {
        return cargaMinPTemporario;
    }

    public void setCargaMinPTemporario(int cargaMinPTemporario) {
        this.cargaMinPTemporario = cargaMinPTemporario;
    }

    public int getCargaMaxPTemporario() {
        return cargaMaxPTemporario;
    }

    public void setCargaMaxPTemporario(int cargaMaxPTemporario) {
        this.cargaMaxPTemporario = cargaMaxPTemporario;
    }

    public int getCargaMinPFixo() {
        return cargaMinPFixo;
    }

    public void setCargaMinPFixo(int cargaMinPFixo) {
        this.cargaMinPFixo = cargaMinPFixo;
    }

    public int getCargaMaxPFixo() {
        return cargaMaxPFixo;
    }

    public void setCargaMaxPFixo(int cargaMaxPFixo) {
        this.cargaMaxPFixo = cargaMaxPFixo;
    }

    public int getCargaMinAdm() {
        return cargaMinAdm;
    }

    public void setCargaMinAdm(int cargaMinAdm) {
        this.cargaMinAdm = cargaMinAdm;
    }

    public int getCargaMaxAdm() {
        return cargaMaxAdm;
    }

    public void setCargaMaxAdm(int cargaMaxAdm) {
        this.cargaMaxAdm = cargaMaxAdm;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParametroConfig other = (ParametroConfig) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
