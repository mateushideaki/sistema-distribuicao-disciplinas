/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.util;

import br.com.prototipo.entity.Disciplina;
import br.com.prototipo.entity.Professor;

/**
 *
 * @author Mateus
 */
public class Fixo {
    private Professor professor;
    private Disciplina disciplina;

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    
    
}
