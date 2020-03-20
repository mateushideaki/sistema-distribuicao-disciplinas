/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.bean;

import br.com.prototipo.dao.DisciplinaDao;
import br.com.prototipo.dao.PreferenciaDao;
import br.com.prototipo.dao.ProfessorDao;
import br.com.prototipo.dao.TurmaDao;
import br.com.prototipo.entity.Disciplina;
import br.com.prototipo.entity.Preferencia;
import br.com.prototipo.entity.Professor;
import br.com.prototipo.entity.Turma;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mateus
 */
@ManagedBean
@SessionScoped
public class PreferenciaBean implements Serializable {

    private Preferencia preferencia = new Preferencia();
    private PreferenciaDao preferenciaDao = new PreferenciaDao();
    private ProfessorDao professorDao = new ProfessorDao();
    private TurmaDao turmaDao = new TurmaDao();
    private DisciplinaDao discDao = new DisciplinaDao();
    private List<Preferencia> listaPreferencias;
    private String busca;
    private String pref1;
    private String pref2;
    private String pref3;
    private String pref4;
    private String pref5;
    private String desc1;
    private String desc2;
    private String desc3;
    private String desc4;
    private String desc5;
    /**
     * Creates a new instance of PreferenciaBean
     */
    public PreferenciaBean() {
    }

    public void limpaPreferencia() {
        this.preferencia.setTurma1(null);
        this.preferencia.setTurma2(null);
        this.preferencia.setTurma3(null);
//        this.preferencia.setDisciplina4(null);
//        this.preferencia.setDisciplina5(null);
        this.preferencia.setDescarte1(null);
        this.preferencia.setDescarte2(null);
        this.preferencia.setDescarte3(null);
//        this.preferencia.setDescarte4(null);
//        this.preferencia.setDescarte5(null);
        this.preferencia.setProfessor(null);
    }


    public String addPreferencia() {
        StringTokenizer st;
        st = new StringTokenizer(pref1);
        pref1 = st.nextToken();
        Turma t1 = turmaDao.getTurma(pref1);

        st = new StringTokenizer(pref2);
        pref2 = st.nextToken();
        Turma t2 = turmaDao.getTurma(pref2);

        st = new StringTokenizer(pref3);
        pref3 = st.nextToken();
        Turma t3 = turmaDao.getTurma(pref3);
        
//        st = new StringTokenizer(pref4);
//        pref4 = st.nextToken();
//        Disciplina d4 = discDao.getDisciplina(pref4);
//        
//        st = new StringTokenizer(pref5);
//        pref5 = st.nextToken();
//        Disciplina d5 = discDao.getDisciplina(pref5);
        
        st = new StringTokenizer(desc1);
        desc1 = st.nextToken();
        Disciplina de1 = discDao.getDisciplina(desc1);

        st = new StringTokenizer(desc2);
        desc2 = st.nextToken();
        Disciplina de2 = discDao.getDisciplina(desc2);

        st = new StringTokenizer(desc3);
        desc3 = st.nextToken();
        Disciplina de3 = discDao.getDisciplina(desc3);
        
//        st = new StringTokenizer(desc4);
//        desc4 = st.nextToken();
//        Disciplina de4 = discDao.getDisciplina(desc4);
//        
//        st = new StringTokenizer(desc5);
//        desc5 = st.nextToken();
//        Disciplina de5 = discDao.getDisciplina(desc5);

        Professor p = professorDao.getProfessor(busca);
        this.preferencia = new Preferencia();
        preferencia.setTurma1(t1);
        preferencia.setTurma2(t2);
        preferencia.setTurma3(t3);
//        preferencia.setDisciplina4(d4);
//        preferencia.setDisciplina5(d5);
        preferencia.setDescarte1(de1);
        preferencia.setDescarte2(de2);
        preferencia.setDescarte3(de3);
//        preferencia.setDescarte4(de4);
//        preferencia.setDescarte5(de5);
        preferencia.setProfessor(p);

        preferenciaDao.addPreferencia(this.preferencia);
        return "gerenciarPreferencias";
    }

    public String removerPreferencia(Preferencia p) {
        this.preferencia = p;
        preferenciaDao.removePreferencia(this.preferencia);
        this.limpaPreferencia();
        return "gerenciarPreferencia";
    }

    public Preferencia getPref() {
        this.preferencia = preferenciaDao.getPreferencia(this.busca);
        return this.preferencia;
    }

    public String carregarPreferencia(Preferencia p) {
        this.pref1 = this.preferencia.getTurma1().getCodigo() + " - " + this.preferencia.getTurma1().getDisciplina().getNome();
        this.pref2 = this.preferencia.getTurma2().getCodigo() + " - " + this.preferencia.getTurma2().getDisciplina().getNome();
        this.pref3 = this.preferencia.getTurma3().getCodigo() + " - " + this.preferencia.getTurma3().getDisciplina().getNome();
//        this.pref4 = this.preferencia.getDisciplina4().getCodigo() + "-" + this.preferencia.getDisciplina4().getNome();
//        this.pref5 = this.preferencia.getDisciplina5().getCodigo() + "-" + this.preferencia.getDisciplina5().getNome();
        this.desc1 = this.preferencia.getDescarte1().getCodigo() + " - " + this.preferencia.getDescarte1().getNome();
        this.desc2 = this.preferencia.getDescarte2().getCodigo() + " - " + this.preferencia.getDescarte2().getNome();
        this.desc3 = this.preferencia.getDescarte3().getCodigo() + " - " + this.preferencia.getDescarte3().getNome();
//        this.desc4 = this.preferencia.getDescarte4().getCodigo() + "-" + this.preferencia.getDescarte4().getNome();
//        this.desc5 = this.preferencia.getDescarte5().getCodigo() + "-" + this.preferencia.getDescarte5().getNome();
        this.preferencia = p;
        return "editarPreferencia";
    }

    public String atualizarPreferencia() {
        StringTokenizer st;
        st = new StringTokenizer(pref1);
        pref1 = st.nextToken();
        Turma t1 = turmaDao.getTurma(pref1);

        st = new StringTokenizer(pref2);
        pref2 = st.nextToken();
        Turma t2 = turmaDao.getTurma(pref2);

        st = new StringTokenizer(pref3);
        pref3 = st.nextToken();
        Turma t3 = turmaDao.getTurma(pref3);
       
        
        st = new StringTokenizer(desc1);
        desc1 = st.nextToken();
        Disciplina de1 = discDao.getDisciplina(desc1);

        st = new StringTokenizer(desc2);
        desc2 = st.nextToken();
        Disciplina de2 = discDao.getDisciplina(desc2);

        st = new StringTokenizer(desc3);
        desc3 = st.nextToken();
        Disciplina de3 = discDao.getDisciplina(desc3);
        
//        st = new StringTokenizer(desc4);
//        desc4 = st.nextToken();
//        Disciplina de4 = discDao.getDisciplina(desc4);
//        
//        st = new StringTokenizer(desc5);
//        desc5 = st.nextToken();
//        Disciplina de5 = discDao.getDisciplina(desc5);
        
        preferencia.setTurma1(t1);
        preferencia.setTurma2(t2);
        preferencia.setTurma3(t3);
//        preferencia.setDisciplina4(d4);
//        preferencia.setDisciplina5(d5);
        preferencia.setDescarte1(de1);
        preferencia.setDescarte2(de2);
        preferencia.setDescarte3(de3);
//        preferencia.setDescarte4(de4);
//        preferencia.setDescarte5(de5);
        preferenciaDao.updatePreferencia(this.preferencia);
        return "gerenciarPreferencias";
    }

    public Preferencia getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(Preferencia preferencia) {
        this.preferencia = preferencia;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public String getPref1() {
        return pref1;
    }

    public void setPref1(String pref1) {
        this.pref1 = pref1;
    }

    public String getPref2() {
        return pref2;
    }

    public void setPref2(String pref2) {
        this.pref2 = pref2;
    }

    public String getPref3() {
        return pref3;
    }

    public void setPref3(String pref3) {
        this.pref3 = pref3;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public String getPref4() {
        return pref4;
    }

    public void setPref4(String pref4) {
        this.pref4 = pref4;
    }

    public String getPref5() {
        return pref5;
    }

    public void setPref5(String pref5) {
        this.pref5 = pref5;
    }

    public String getDesc4() {
        return desc4;
    }

    public void setDesc4(String desc4) {
        this.desc4 = desc4;
    }

    public String getDesc5() {
        return desc5;
    }

    public void setDesc5(String desc5) {
        this.desc5 = desc5;
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.preferencia);
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
        final PreferenciaBean other = (PreferenciaBean) obj;
        if (!Objects.equals(this.preferencia, other.preferencia)) {
            return false;
        }
        return true;
    }

}
