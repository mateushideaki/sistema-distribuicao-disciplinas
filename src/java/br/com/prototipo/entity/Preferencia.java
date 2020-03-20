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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Mateus
 */
@Entity
@Table(name="preferencia")
public class Preferencia implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    private Professor professor;
    
    @Column(name="registroProfessor", unique=true)
    private String registroProfessor;
    
    @ManyToOne
    private Turma turma1;
    
    @ManyToOne
    private Turma turma2;
    
    @ManyToOne
    private Turma turma3;
    
    
    
//    @ManyToOne
//    private Disciplina disciplina1;
//    
//    @ManyToOne
//    private Disciplina disciplina2;
//    
//    @ManyToOne
//    private Disciplina disciplina3;
    
    
    @ManyToOne
    private Disciplina descarte1;
    
    @ManyToOne
    private Disciplina descarte2;
    
    @ManyToOne
    private Disciplina descarte3;
    

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
        this.registroProfessor = professor.getRegistro();
    }

    public Turma getTurma1() {
        return turma1;
    }

    public void setTurma1(Turma turma1) {
        this.turma1 = turma1;
    }

    public Turma getTurma2() {
        return turma2;
    }

    public void setTurma2(Turma turma2) {
        this.turma2 = turma2;
    }

    public Turma getTurma3() {
        return turma3;
    }

    public void setTurma3(Turma turma3) {
        this.turma3 = turma3;
    }

    public Disciplina getDescarte1() {
        return descarte1;
    }

    public void setDescarte1(Disciplina descarte1) {
        this.descarte1 = descarte1;
    }

    public Disciplina getDescarte2() {
        return descarte2;
    }

    public void setDescarte2(Disciplina descarte2) {
        this.descarte2 = descarte2;
    }

    public Disciplina getDescarte3() {
        return descarte3;
    }

    public void setDescarte3(Disciplina descarte3) {
        this.descarte3 = descarte3;
    }





    

    public String getRegistroProfessor() {
        return registroProfessor;
    }

    public void setRegistroProfessor(String registroProfessor) {
        this.registroProfessor = registroProfessor;
    }


    

  
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
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
        final Preferencia other = (Preferencia) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
