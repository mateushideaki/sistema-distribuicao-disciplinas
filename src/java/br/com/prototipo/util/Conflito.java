/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.util;

/**
 *
 * @author Mateus
 */
public class Conflito {
    private String disc1;
    private String disc2;

    public boolean temConflito(String disciplina){
        return (disciplina.equalsIgnoreCase(this.disc1) || disciplina.equalsIgnoreCase(this.disc2));
    }
    
    public String getDisc1() {
        return disc1;
    }

    public void setDisc1(String disc1) {
        this.disc1 = disc1;
    }

    public String getDisc2() {
        return disc2;
    }

    public void setDisc2(String disc2) {
        this.disc2 = disc2;
    }
    
    
}
