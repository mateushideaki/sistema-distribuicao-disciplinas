/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mateus
 */
@ManagedBean
@SessionScoped
public class MenuBean implements Serializable{

    /**
     * Creates a new instance of MenuBean
     */
    public MenuBean() {
    }
    
    public String telaLogin(){
        return "index";
    }
    
    public String gerenciarTurma(){
        return "gerenciarTurmas";
    }
    
    public String cadastrarUsuarioProfessor(){
        return "cadastrarUsuarioProfessor";
    }
    
    public String cadastrarProfessor(){
        return "gerenciarProfessor";
    }
    
    public String cadastrarDisciplina(){
        return "gerenciarDisciplina";
    }
    
    public String menuPrincipal(){
        return "menuPrincipal";
    }
}
