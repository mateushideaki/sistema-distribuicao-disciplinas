    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.bean;

import br.com.prototipo.dao.ProfessorDao;
import br.com.prototipo.entity.Professor;
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
public class ProfessorBean implements Serializable {

    /**
     * Creates a new instance of ProfessorBean
     */
    private Professor professor = new Professor();
    private String busca;
    private ProfessorDao professorDao = new ProfessorDao();
    private List<Professor> listaProfessores;
    private List<String> professoresString;
    private boolean modificado = true;

    public ProfessorBean() {
        modificado = true;
    }

    public String adicionarProfessor() {
        this.professor.setCargaMaxS(this.professor.getCargaMaxA()/32);
        this.professor.setCargaMinS(this.professor.getCargaMinA()/32);
        professorDao.addProfessor(professor);
        professor.setNome(null);
        professor.setCargaMaxA(0);
        professor.setCargaMinA(0);
        professor.setRegistro(null);
        professor.setCargoAdm(false);
        professor.setDescricaoCargo(null);
        modificado = true;
        return "gerenciarProfessor";
    }

    public void limpaProf() {
        professor.setNome(null);
        professor.setCargaMaxA(0);
        professor.setCargaMinA(0);
        professor.setRegistro(null);
        professor.setCargoAdm(false);
        professor.setDescricaoCargo(null);
    }

    public String removerProfessor(Professor p) {
        this.professor = p;
        professorDao.removeProfessor(this.professor);
        professor.setNome(null);
        professor.setCargaMaxA(0);
        professor.setCargaMinA(0);
        professor.setRegistro(null);
        professor.setCargoAdm(false);
        professor.setDescricaoCargo(null);
        modificado = true;
        return "gerenciarProfessor";
    }

    public List listarProfessores() {
        if (modificado) {
            professorDao = new ProfessorDao();
            modificado = false;
        }
        this.listaProfessores = professorDao.getList(this.busca);
        return this.listaProfessores;
    }

    public String carregarProfessor(Professor p) {
        professor = p;
        return "editarProfessor";
    }

    public String atualizarProfessor() {
        this.professor.setCargaMaxS(this.professor.getCargaMaxA()/32);
        this.professor.setCargaMinS(this.professor.getCargaMinA()/32);
        professorDao.updateProfessor(this.professor);
        professor.setNome(null);
        professor.setCargaMaxA(0);
        professor.setCargaMinA(0);
        professor.setRegistro(null);
        professor.setCargoAdm(false);
        professor.setDescricaoCargo(null);
        return "gerenciarProfessor";
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public List<String> getProfessoresString() {
        return professoresString;
    }

    public void setProfessoresString(List<String> professoresString) {
        this.professoresString = professoresString;
    }

    public void geraListaString() {
        this.professoresString = professorDao.getListProfessoresString();
    }

    public List<Professor> getListaProfessores() {
        return listaProfessores;
    }

    public void setListaProfessores(List<Professor> listaProfessores) {
        this.listaProfessores = listaProfessores;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.professor);
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
        final ProfessorBean other = (ProfessorBean) obj;
        if (!Objects.equals(this.professor, other.professor)) {
            return false;
        }
        return true;
    }

}
