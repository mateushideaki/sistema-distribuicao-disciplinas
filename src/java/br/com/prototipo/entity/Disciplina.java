/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Mateus
 */
@Entity
@Table(name="disciplina")
public class Disciplina implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name="codigo", unique=true)
    private String codigo;
    private String nome;
//    private int semestre;
//    private boolean anual;
//    private int cargaAnual;
    
    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.REMOVE)
    private Collection<Turma> t;

    public Collection<Turma> getT() {
        return t;
    }

    public void setT(Collection<Turma> t) {
        this.t = t;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

//    public int getSemestre() {
//        return semestre;
//    }
//
//    public void setSemestre(int semestre) {
//        this.semestre = semestre;
//    }

//    public boolean isAnual() {
//        return anual;
//    }
//
//    public void setAnual(boolean anual) {
//        this.anual = anual;
//    }
//
//    public int getCargaAnual() {
//        return cargaAnual;
//    }
//
//    public void setCargaAnual(int cargaAnual) {
//        this.cargaAnual = cargaAnual;
//    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
   
   

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Disciplina other = (Disciplina) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
   
     
    
}
