package br.com.prototipo.bean;

import br.com.prototipo.dao.AlocacaoDao;
import br.com.prototipo.dao.DisciplinaDao;
import br.com.prototipo.dao.PreferenciaDao;
import br.com.prototipo.dao.ProfessorDao;
import br.com.prototipo.dao.TurmaDao;
import br.com.prototipo.entity.Alocacao;
import br.com.prototipo.util.Escritor;
import br.com.prototipo.entity.HorarioTurma;
import br.com.prototipo.entity.Preferencia;
import br.com.prototipo.entity.Professor;
import br.com.prototipo.entity.Turma;
import br.com.prototipo.util.Leitor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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
public class AlocacaoBean implements Serializable {

    private List<String> profString;
    private List<String> turmaString;
    private Escritor escritor = new Escritor();
    private Leitor leitor = new Leitor();
    private AlocacaoDao alocacaoDao = new AlocacaoDao();

    private String regProf, codTurma;
    private List<String> discString;

    private List<Professor> listaProfessores = new ArrayList<>();
    private List<Turma> listaTurmas = new ArrayList<>();
    private List<Preferencia> listaPreferencias = new ArrayList<>();
    private List<HorarioTurma> listaHorarios = new ArrayList<>();
    private List<Alocacao> listaAlocacao = new ArrayList<>();  //os que falta inserir
    private List<Alocacao> listaAlocacaoFixos = new ArrayList<>(); // os fixos
    private String ano;
    private String anoBusca;
    private List<Alocacao> listaAlocacao2 = new ArrayList<>(); //listar os ja inseridos
    private boolean modificado = true;
    
    private boolean equitativo;
    private boolean anual;
    private boolean hext;

    public AlocacaoBean() {
    }

    public void escreverModelo() throws IOException {
        this.removeFixosDasListas();
        this.escritor.escreve(listaProfessores, listaPreferencias, listaTurmas, listaHorarios, anual, equitativo, hext);
    }

    public void lerModelo() throws FileNotFoundException {
        ProfessorDao profDao = new ProfessorDao();
        this.listaAlocacao = this.leitor.ler();
        for (int i = 0; i < listaAlocacao.size(); i++) {
            Alocacao aloc = listaAlocacao.get(i);
            Professor prof = profDao.getProfessor(aloc.getProfessor().getRegistro());
            Turma t = aloc.getTurma();
            for (int j = 0; j < listaTurmas.size(); j++) {
                Turma turma = listaTurmas.get(j);
                if (turma.getId() == t.getId()) {
                    t = turma;
                    break;
                }
            }
            aloc.setAno(ano);
            aloc.setProfessor(prof);
            aloc.setTurma(t);
            String cod = t.getCodigo() + ano;
            aloc.setCodigo(cod);
        }
        this.listaAlocacao.addAll(this.listaAlocacaoFixos);
        this.modificado = true;
    }

    public void removeFixosDasListas() {
        for (int i = 0; i < this.listaAlocacaoFixos.size(); i++) {
            Turma t = this.listaAlocacaoFixos.get(i).getTurma();
            Professor p = this.listaAlocacaoFixos.get(i).getProfessor();
            this.removeProfessorLista(p);
            this.removeTurmaLista(t);
        }
    }

    public String fixar() {
        ProfessorDao professorDao = new ProfessorDao();
        StringTokenizer st;
        st = new StringTokenizer(regProf);
        String codigo = st.nextToken();
        Professor p = professorDao.getProfessor(codigo);

        TurmaDao tDao = new TurmaDao();
        StringTokenizer st2;
        st2 = new StringTokenizer(codTurma);
        codTurma = st2.nextToken();
        Turma t = tDao.getTurma(codTurma);

        Alocacao aloc = new Alocacao();
        aloc.setAno(ano);
        aloc.setCodigo(t.getCodigo() + ano);
        aloc.setProfessor(p);
        aloc.setTurma(t);
        this.listaAlocacaoFixos.add(aloc);  
        return "Alocacao4";
    }

    public String salvarAlocacoes() {
        this.alocacaoDao = new AlocacaoDao();
        for (int i = 0; i < this.listaAlocacao.size(); i++) {
            alocacaoDao.addAlocacao(this.listaAlocacao.get(i));
        }
        this.listaAlocacao.clear();
        this.listaAlocacao2.clear();
        this.listaAlocacaoFixos.clear();
        this.listaProfessores.clear();
        this.listaTurmas.clear();
        return "AlocacaoFinal";
    }

    public String rejeitarResultado() {
        this.listaAlocacao.clear();
        this.listaAlocacao2.clear();
        this.listaAlocacaoFixos.clear();
        this.listaProfessores.clear();
        this.listaTurmas.clear();
        return "AlocacaoFinal";
    }

    public List listarAlocacoes() {
        //this.listaAlocacao = alocacaoDao.getList(anoBusca);
        Collections.sort(this.listaAlocacao, new Comparator() {
            public int compare(Object o1, Object o2) {
                Alocacao p1 = (Alocacao) o1;
                Alocacao p2 = (Alocacao) o2;
                return p1.getProfessor().getNome().compareToIgnoreCase(p2.getProfessor().getNome());
            }
        });
        return this.listaAlocacao;
    }

    public List listarAlocacoes2() { //ALOCACAO2 = OS JA CADASTRADOS
        if (modificado) {
            alocacaoDao = new AlocacaoDao();
            modificado = false;
        }
        this.listaAlocacao2 = alocacaoDao.getList(anoBusca);
        Collections.sort(this.listaAlocacao2, new Comparator() {
            public int compare(Object o1, Object o2) {
                Alocacao p1 = (Alocacao) o1;
                Alocacao p2 = (Alocacao) o2;
                return p1.getProfessor().getNome().compareToIgnoreCase(p2.getProfessor().getNome());
            }
        });
        return this.listaAlocacao2;
    }

    public List listarFixos() {
        return this.listaAlocacaoFixos;
    }

    public void listarHorarios() {
        this.listaHorarios = new TurmaDao().getListAlocacao();
    }

    public void listarPreferencias() {
        this.listaPreferencias = new PreferenciaDao().listaParaAlocacao();
    }

    public String adicionarTurmasLista() {

        TurmaDao tDao = new TurmaDao();
        Iterator it = turmaString.iterator();
        while (it.hasNext()) {
            String codigoTurmaAux = (String) it.next();
            StringTokenizer st;
            st = new StringTokenizer(codigoTurmaAux);
            codigoTurmaAux = st.nextToken();

            Turma t = tDao.getTurma(codigoTurmaAux);
            if (!this.listaTurmas.contains(t)) {
                this.listaTurmas.add(t);
            }
        }
        return "Alocacao2";
    }

    public String adicionarProfessoresLista() {

        ProfessorDao professorDao = new ProfessorDao();
        Iterator it = profString.iterator();
        while (it.hasNext()) {
            StringTokenizer st;
            st = new StringTokenizer((String) it.next());
            String codigo = st.nextToken();
            Professor p = professorDao.getProfessor(codigo);
            if (!this.listaProfessores.contains(p)) {
                this.listaProfessores.add(p);
            }
        }
        Collections.sort(this.listaProfessores, new Comparator() {
            public int compare(Object o1, Object o2) {
                Professor p1 = (Professor) o1;
                Professor p2 = (Professor) o2;
                return p1.getNome().compareToIgnoreCase(p2.getNome());
            }
        });
        return "Alocacao";
    }

    public void reset() {
        this.listaProfessores.clear();
        ProfessorDao professorDao = new ProfessorDao();
        Iterator it = profString.iterator();
        while (it.hasNext()) {
            StringTokenizer st;
            st = new StringTokenizer((String) it.next());
            String codigo = st.nextToken();
            Professor p = professorDao.getProfessor(codigo);
            if (!this.listaProfessores.contains(p)) {
                this.listaProfessores.add(p);
            }
        }
        Collections.sort(this.listaProfessores, new Comparator() {
            public int compare(Object o1, Object o2) {
                Professor p1 = (Professor) o1;
                Professor p2 = (Professor) o2;
                return p1.getNome().compareToIgnoreCase(p2.getNome());
            }
        });
    }

    public String limparLista() {
        this.listaProfessores.clear();
        return "Alocacao";
    }

    public String limparListaFixos() {
        this.listaAlocacaoFixos.clear();
        return "Alocacao2";
    }

    public List<Professor> listarProf() {
        return this.listaProfessores;
    }

    public List<Turma> listarTurmas() {
        return this.listaTurmas;
    }

    public void listarDisciplinas() {
        DisciplinaDao dd = new DisciplinaDao();
        this.discString = dd.getListDisciplinasString();
    }

    public String passo1() {
        return "Alocacao";
    }

    public String passo2() {
        this.listarHorarios();
        this.listarPreferencias();
        this.listarDisciplinas();
        return "Alocacao2";
    }

    public String passo3() {
        return "Alocacao3";
    }

    public String passo4() {
        return "Alocacao4";
    }
    
    public String passo5(){
        return "Alocacao5";
    }

    public String passo6() throws IOException {
        this.escreverModelo();
        this.lerModelo();
        return "Alocacao6";
    }

    public String removeProfessorLista(Professor p) {
        if (this.listaProfessores.contains(p)) {
            this.listaProfessores.remove(p);
        }
        return "Alocacao";
    }

    public String removeTurmaLista(Turma t) {
        if (this.listaTurmas.contains(t)) {
            this.listaTurmas.remove(t);
        }
        return "Alocacao2";
    }

    public String limparTurmas() {
        this.listaTurmas.clear();
        return "Alocacao2";
    }

    public String removeFixo(Alocacao a) {
        if (this.listaAlocacaoFixos.contains(a)) {
            this.listaAlocacaoFixos.remove(a);
        }
        return "Alocacao4";
    }

    public List<String> getProfString() {
        return profString;
    }

    public void setProfString(List<String> profString) {
        this.profString = profString;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public List<Professor> getListaProfessores() {
        return listaProfessores;
    }

    public void setListaProfessores(List<Professor> listaProfessores) {
        this.listaProfessores = listaProfessores;
    }

    public String getAnoBusca() {
        return anoBusca;
    }

    public void setAnoBusca(String anoBusca) {
        this.anoBusca = anoBusca;
    }

    public String getRegProf() {
        return regProf;
    }

    public void setRegProf(String regProf) {
        this.regProf = regProf;
    }

    public List<String> getDiscString() {
        return discString;
    }

    public void setDiscString(List<String> discString) {
        this.discString = discString;
    }

    public String getCodTurma() {
        return codTurma;
    }

    public void setCodTurma(String codTurma) {
        this.codTurma = codTurma;
    }

    public List<String> getTurmaString() {
        return turmaString;
    }

    public void setTurmaString(List<String> turmaString) {
        this.turmaString = turmaString;
    }

    public boolean isEquitativo() {
        return equitativo;
    }

    public void setEquitativo(boolean equitativo) {
        this.equitativo = equitativo;
    }

    public boolean isAnual() {
        return anual;
    }

    public void setAnual(boolean anual) {
        this.anual = anual;
    }

    public boolean isHext() {
        return hext;
    }

    public void setHext(boolean hext) {
        this.hext = hext;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.listaProfessores);
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
        final AlocacaoBean other = (AlocacaoBean) obj;
        if (!Objects.equals(this.listaProfessores, other.listaProfessores)) {
            return false;
        }
        return true;
    }
    
    

}
