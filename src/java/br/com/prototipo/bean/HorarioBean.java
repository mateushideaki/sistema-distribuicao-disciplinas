/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.bean;

import br.com.prototipo.dao.HorarioDao;
import br.com.prototipo.entity.Horario;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mateus
 */
@ManagedBean
@SessionScoped
public class HorarioBean implements Serializable{

    private Horario horario = new Horario();
    private HorarioDao horarioDao = new HorarioDao();
    private List<Horario> listaHorarios;
    private String busca;
    private List<String> horariosString;
    private boolean modificado = true;
    
    /**
     * Creates a new instance of CursoBean
     */
    public HorarioBean() {
    }
    
    
    
    public void cadastroInicial(){
        int d,a;
        if(horarioDao.getList("").isEmpty()){
            for(d = 2; d <= 7; d++){
                for(a = 1; a <= 14; a++){
                    this.horario = new Horario();
                    this.horario.setDia(d);
                    this.horario.setAula(Integer.toString(a));
                    this.horario.setNomeDia();
                    this.addHorario();
                }
            }
        }
    }
    
    public void limpaHorario(){
        this.horario.setId(0);
        this.horario.setAula(null);
        this.horario.setDia(0);
        this.horario.setNomeDia();
    }
    
    public String addHorario(){
        this.horario.setNomeDia();
        horarioDao.addHorario(this.horario);
        this.horario.setId(0);
        this.horario.setAula(null);
        this.horario.setDia(0);
        this.horario.setNomeDia();
        modificado = true;
        return "gerenciarHorario";
    }
    
    public String removerHorario(Horario h){
        this.horario = h;
        horarioDao.removeHorario(this.horario);
        this.horario.setId(0);
        this.horario.setAula(null);
        this.horario.setDia(0);
        this.horario.setNomeDia();
        modificado = true;
        return "gerenciarHorario";
    }
    
    public List listarHorarios(){
        if(modificado){
            horarioDao = new HorarioDao();
            modificado = false;
        }
        
        this.listaHorarios = horarioDao.getList(this.busca);
        return this.listaHorarios;
    }
    
    public String carregarHorario(Horario h){
        this.horario = h;
        return "editarHorario";
    }
    
    public String atualizarHorario(){
        horarioDao.updateHorario(this.horario);
        this.horario.setId(0);
        this.horario.setAula(null);
        this.horario.setDia(0);
        this.horario.setNomeDia();
        return "gerenciarHorario";
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }
    
    
    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }
    
    public void gerarHorariosString(){
        horariosString = horarioDao.getListParaDisciplinas("Segunda-Feira");
    }

    public List<String> getHorariosString() {
        return horariosString;
    }
    
    
    
    public void setHorariosString(List<String> horariosString) {
        this.horariosString = horariosString;
    }

    public List<Horario> getListaHorarios() {
        return listaHorarios;
    }

    public void setListaHorarios(List<Horario> listaHorarios) {
        this.listaHorarios = listaHorarios;
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.horario);
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
        final HorarioBean other = (HorarioBean) obj;
        if (!Objects.equals(this.horario, other.horario)) {
            return false;
        }
        return true;
    }
    
    
    
}
