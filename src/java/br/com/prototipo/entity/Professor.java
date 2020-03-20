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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 *
 * @author Mateus
 */

@Entity
@Table(name="professor")

public class Professor implements Serializable{
    
   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   private int id;
   private String nome;
   @Column(name="registro", unique=true)
   private String registro;
   @Column(name="carga_max_semanal")
   private int cargaMaxS;
   @Column(name="carga_min_semanal")
   private int cargaMinS;
   @Column(name="carga_min_anual")
   private int cargaMinA;
   @Column(name="carga_max_anual")
   private int cargaMaxA;
   private boolean cargoAdm;
   private String descricaoCargo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public int getCargaMaxS() {
        return cargaMaxS;
    }

    public void setCargaMaxS(int cargaMaxS) {
        this.cargaMaxS = cargaMaxS;
    }

    public int getCargaMinS() {
        return cargaMinS;
    }

    public void setCargaMinS(int cargaMinS) {
        this.cargaMinS = cargaMinS;
    }

    public int getCargaMinA() {
        return cargaMinA;
    }

    public void setCargaMinA(int cargaMinA) {
        this.cargaMinA = cargaMinA;
        this.cargaMinS = cargaMinA/32;
    }

    public int getCargaMaxA() {
        return cargaMaxA;
    }

    public void setCargaMaxA(int cargaMaxA) {
        this.cargaMaxA = cargaMaxA;
        this.cargaMaxS = cargaMaxA/32;
    }

    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getCargoAdm() {
        return cargoAdm;
    }

    public void setCargoAdm(boolean cargoAdm) {
        this.cargoAdm = cargoAdm;
    }
    
    
    public String getDescricaoCargo() {
        return descricaoCargo;
    }

    public void setDescricaoCargo(String descricaoCargo) {
        this.descricaoCargo = descricaoCargo;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
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
        final Professor other = (Professor) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
   
   
   
}
