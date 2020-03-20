/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.bean;

import br.com.prototipo.dao.DisciplinaDao;
import br.com.prototipo.entity.Disciplina;
import java.io.Serializable;
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
public class DisciplinaBean implements Serializable {

    /**
     * Creates a new instance of DisciplinaBean
     */
    private Disciplina disciplina = new Disciplina();
    private DisciplinaDao disciplinaDao = new DisciplinaDao();
    private List<Disciplina> listaDisciplinas;
    private List<String> disciplinasString;
    private List<String> disciplinasString2;
    private Disciplina busca = new Disciplina();
    private boolean modificado = true;


    public DisciplinaBean() {
    }

    public void limpaDisc() {
        disciplina.setCodigo(null);
        disciplina.setNome(null);
    }

    public void gerarListString() {
        this.disciplinasString = disciplinaDao.getListParaTurmas();
    }

    public void gerarListString2() {
        this.disciplinasString2 = disciplinaDao.getListDisciplinasString();
    }

    public String adicionarDisciplina() {
        disciplinaDao.addDisciplina(disciplina);
        modificado = true;
        return "gerenciarDisciplina";
    }

    public String removerDisciplina(Disciplina d) {
        disciplinaDao = new DisciplinaDao();
        this.disciplina = disciplinaDao.getDisciplina(d.getCodigo());
        disciplinaDao.removeDisciplina(this.disciplina);
        disciplina.setCodigo(null);
        disciplina.setNome(null);
        modificado = true;
        return "gerenciarDisciplina";
    }

    public List listarDisciplinas() {
        if (modificado) {
            disciplinaDao = new DisciplinaDao();
            modificado = false;
        }

        this.listaDisciplinas = disciplinaDao.getList(this.busca);
        return this.listaDisciplinas;
    }

    public String carregarDisciplina(Disciplina d) {
        disciplina = d;
        return "editarDisciplina";
    }

    public String atualizarDisciplina() {
        disciplinaDao.updateDisciplina(this.disciplina);
        disciplina.setCodigo(null);
        disciplina.setNome(null);
        return "gerenciarDisciplina";
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<String> getDisciplinasString() {
        return disciplinasString;
    }

    public void setDisciplinasString(List<String> disciplinasString) {
        this.disciplinasString = disciplinasString;
    }

    public List<String> getDisciplinasString2() {
        return disciplinasString2;
    }

    public void setDisciplinasString2(List<String> disciplinasString2) {
        this.disciplinasString2 = disciplinasString2;
    }

    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }

   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.disciplina);
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
        final DisciplinaBean other = (DisciplinaBean) obj;
        if (!Objects.equals(this.disciplina, other.disciplina)) {
            return false;
        }
        return true;
    }

    public Disciplina getBusca() {
        return busca;
    }

    public void setBusca(Disciplina busca) {
        this.busca = busca;
    }

}
