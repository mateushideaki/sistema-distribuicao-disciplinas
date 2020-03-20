/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.bean;

import br.com.prototipo.dao.CursoDao;
import br.com.prototipo.entity.Curso;
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
public class CursoBean implements Serializable {

    private Curso curso = new Curso();
    private CursoDao cursoDao = new CursoDao();
    private List<Curso> listaCursos;
    private String busca;
    private boolean modificado = true;

    /**
     * Creates a new instance of CursoBean
     */
    public CursoBean() {
    }

    public void limpaCurso() {
        this.curso.setId(0);
        this.curso.setCodigo(null);
        this.curso.setNome(null);
    }

    public List<String> listarCursoParaTurmas() {
        return cursoDao.getListParaTurmas();
    }

    public String addCurso() {
        cursoDao.addCurso(this.curso);
        this.curso.setId(0);
        this.curso.setCodigo(null);
        this.curso.setNome(null);
        modificado = true;
        return "gerenciarCurso";
    }

    public String removerCurso(Curso c) {
        this.curso = c;
        cursoDao.removeCurso(this.curso);
        this.curso.setCodigo(null);
        this.curso.setId(0);
        this.curso.setNome(null);
        modificado = true;
        return "gerenciarCurso";
    }

    public List listarCursos() {
        if (modificado) {
            cursoDao = new CursoDao();
            modificado = false;
        }

        this.listaCursos = cursoDao.getList(this.busca);
        return this.listaCursos;
    }

    public String carregarCurso(Curso c) {
        this.curso = c;
        return "editarCurso";
    }

    public String atualizarCurso() {
        cursoDao.updateCurso(this.curso);
        this.curso.setId(0);
        this.curso.setNome(null);
        return "gerenciarCurso";
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public List<Curso> getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.curso);
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
        final CursoBean other = (CursoBean) obj;
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        return true;
    }

}
