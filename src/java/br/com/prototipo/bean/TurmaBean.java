/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.bean;

import br.com.prototipo.dao.CursoDao;
import br.com.prototipo.dao.DisciplinaDao;
import br.com.prototipo.dao.HorarioDao;
import br.com.prototipo.dao.TurmaDao;
import br.com.prototipo.entity.Curso;
import br.com.prototipo.entity.Disciplina;
import br.com.prototipo.entity.Horario;
import br.com.prototipo.entity.HorarioTurma;
import br.com.prototipo.entity.Turma;
import java.io.Serializable;
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
public class TurmaBean implements Serializable {

    /**
     * Creates a new instance of TurmaBean
     */
    private Turma turma = new Turma();
    private TurmaDao turmaDao = new TurmaDao();
    private List<Turma> listaTurmas;
    private String cursoSelecionado;
    private String disciplinaSelecionada;
    private String busca;
    private String busca2;
    private CursoDao cd = new CursoDao();
    private DisciplinaDao dd = new DisciplinaDao();
    private List<String> horariosSegunda;
    private List<String> horariosTerca;
    private List<String> horariosQuarta;
    private List<String> horariosQuinta;
    private List<String> horariosSexta;
    private List<String> horariosSabado;
    private HorarioTurma hTurma = new HorarioTurma();
    private HorarioDao hd = new HorarioDao();
    private boolean modificadoHT = true;
    private List<HorarioTurma> listaHT;
    private String cod;
    private List<String> turmasString;

    private String ano;
    private boolean modificadoT = true;

    public TurmaBean() {
    }

    public String verHorarios(Turma t) {
        this.turma = t;
        return "gerenciarHorariosTurma";
    }

    public void modificou() {
        this.modificadoT = true;
        this.modificadoHT = true;
    }

    public void limpaHT() {
        this.horariosSegunda = null;
        this.horariosTerca = null;
        this.horariosQuarta = null;
        this.horariosQuinta = null;
        this.horariosSexta = null;
        this.horariosSabado = null;
    }

    public String removerHorarioTurma(HorarioTurma ht) {
        this.hTurma = ht;
        this.turmaDao.removeHorarioTurma(this.hTurma);
        modificadoHT = true;
        return "gerenciarHorariosTurma";
    }

    public List listarHorariosTurma() {
        if (modificadoHT) {
            turmaDao = new TurmaDao();
            modificadoHT = false;
        }
        this.listaHT = turmaDao.getList(this.turma.getCodigo(), this.busca2);
        return this.listaHT;
    }

    public String adicionarHorariosTurma() {
        if (!horariosSegunda.isEmpty()) {
            Iterator it = horariosSegunda.listIterator();
            while (it.hasNext()) {
                Horario hora = hd.getHorarioEscolhido("Segunda-feira", (String) it.next());
                hTurma = new HorarioTurma();
                hTurma.setTurma(this.turma);
                hTurma.setHorario(hora);
                turmaDao.addHorarioTurma(hTurma);
            }
        }

        if (!horariosTerca.isEmpty()) {
            Iterator it = horariosTerca.listIterator();
            while (it.hasNext()) {
                Horario hora = hd.getHorarioEscolhido("Terca-feira", (String) it.next());
                hTurma = new HorarioTurma();
                hTurma.setTurma(this.turma);
                hTurma.setHorario(hora);
                turmaDao.addHorarioTurma(hTurma);
            }
        }

        if (!horariosQuarta.isEmpty()) {
            Iterator it = horariosQuarta.listIterator();
            while (it.hasNext()) {
                Horario hora = hd.getHorarioEscolhido("Quarta-feira", (String) it.next());
                hTurma = new HorarioTurma();
                hTurma.setTurma(this.turma);
                hTurma.setHorario(hora);
                turmaDao.addHorarioTurma(hTurma);
            }
        }

        if (!horariosQuinta.isEmpty()) {
            Iterator it = horariosQuinta.listIterator();
            while (it.hasNext()) {
                Horario hora = hd.getHorarioEscolhido("Quinta-feira", (String) it.next());
                hTurma = new HorarioTurma();
                hTurma.setTurma(this.turma);
                hTurma.setHorario(hora);
                turmaDao.addHorarioTurma(hTurma);
            }
        }

        if (!horariosSexta.isEmpty()) {
            Iterator it = horariosSexta.listIterator();
            while (it.hasNext()) {
                Horario hora = hd.getHorarioEscolhido("Sexta-feira", (String) it.next());
                hTurma = new HorarioTurma();
                hTurma.setTurma(this.turma);
                hTurma.setHorario(hora);
                turmaDao.addHorarioTurma(hTurma);
            }
        }

        if (!horariosSabado.isEmpty()) {
            Iterator it = horariosSabado.listIterator();
            while (it.hasNext()) {
                Horario hora = hd.getHorarioEscolhido("Sabado", (String) it.next());
                hTurma = new HorarioTurma();
                hTurma.setTurma(this.turma);
                hTurma.setHorario(hora);
                turmaDao.addHorarioTurma(hTurma);
            }
        }
        modificadoT = true;
        modificadoHT = true;
        return "gerenciarHorariosTurma";
    }

    public void limparTurma() {
        this.turma = new Turma();
    }

    public String addTurma() {
        StringTokenizer st;
        st = new StringTokenizer(cursoSelecionado);
        cursoSelecionado = st.nextToken();
        Curso curso = cd.getCursoEscolhido(cursoSelecionado);
        this.turma.setCurso(curso);

        st = new StringTokenizer(disciplinaSelecionada);
        disciplinaSelecionada = st.nextToken();
        Disciplina disc = dd.getDisciplina(disciplinaSelecionada);
        this.turma.setDisciplina(disc);
        this.turma.setCodigo(disc.getCodigo() + "-" + this.turma.getCodigo());
        if (this.turma.isAnual()) {
            this.turma.setCargaSemanal(this.turma.getCargaTotal()/32);
        } else {
            this.turma.setCargaSemanal(this.turma.getCargaTotal()/16);
        }
        String retorno = turmaDao.addTurma(this.turma);
        if (retorno == "sucesso") {
            modificadoT = true;
            return "cadastrarHorariosTurma";
        } else {
            return "gerenciarTurmas";
        }
    }

    public String removerTurma(Turma t) {
        turmaDao.removerTurma(t);
        modificadoT = true;
        return "gerenciarTurmas";
    }

    public List listarTurmas() {
        if (modificadoT) {
            turmaDao = new TurmaDao();
            modificadoT = false;
        }
        this.listaTurmas = turmaDao.getList(this.busca);
        return this.listaTurmas;
    }

    public String carregarTurma(Turma t) {
        this.turma = t;
        this.cod = this.turma.getCodigo();
        this.disciplinaSelecionada = this.turma.getDisciplina().getCodigo() + " - " + this.turma.getDisciplina().getNome();
        this.cursoSelecionado = this.turma.getCurso().getCodigo() + " - " + this.turma.getCurso().getNome();
        return "editarTurma";
    }

    public String atualizarTurma() {
        StringTokenizer st;
        st = new StringTokenizer(cursoSelecionado);
        cursoSelecionado = st.nextToken();
        Curso curso = cd.getCursoEscolhido(cursoSelecionado);
        this.turma.setCurso(curso);

        st = new StringTokenizer(disciplinaSelecionada);
        disciplinaSelecionada = st.nextToken();
        Disciplina disc = dd.getDisciplina(disciplinaSelecionada);
        this.turma.setDisciplina(disc);

        List<HorarioTurma> listAux = turmaDao.getList2(this.turma.getCodigo());

        String aux = cod;
        st = new StringTokenizer(cod);
        try {
            aux = st.nextToken("-");
            aux = st.nextToken();
        } catch (Exception e) {
            aux = this.cod;
        }
        this.turma.setCodigo(disc.getCodigo() + "-" + aux);
        if (this.turma.isAnual()) {
            this.turma.setCargaSemanal(this.turma.getCargaTotal()/32);
        } else {
            this.turma.setCargaSemanal(this.turma.getCargaTotal()/16);
        }
        for (int i = 0; i < listAux.size(); i++) {
            HorarioTurma ht1 = listAux.get(i);
            ht1.setCodigoTurma(this.turma.getCodigo());
            turmaDao.updateHorarioTurma(ht1);
        }
        
        turmaDao.updateTurma(this.turma);
        return "gerenciarTurmas";
    }

    public void listarTurmasString() {
        this.turmasString = turmaDao.getListTurmasString();
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getCursoSelecionado() {
        return cursoSelecionado;
    }

    public void setCursoSelecionado(String cursoSelecionado) {
        this.cursoSelecionado = cursoSelecionado;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public String getBusca2() {
        return busca2;
    }

    public void setBusca2(String busca2) {
        this.busca2 = busca2;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public List<Turma> getListaTurmas() {
        return listaTurmas;
    }

    public void setListaTurmas(List<Turma> listaTurmas) {
        this.listaTurmas = listaTurmas;
    }

    public List<String> getHorariosSegunda() {
        return horariosSegunda;
    }

    public void setHorariosSegunda(List<String> horariosSegunda) {
        this.horariosSegunda = horariosSegunda;
    }

    public List<String> getHorariosTerca() {
        return horariosTerca;
    }

    public void setHorariosTerca(List<String> horariosTerca) {
        this.horariosTerca = horariosTerca;
    }

    public List<String> getHorariosQuarta() {
        return horariosQuarta;
    }

    public void setHorariosQuarta(List<String> horariosQuarta) {
        this.horariosQuarta = horariosQuarta;
    }

    public List<String> getHorariosQuinta() {
        return horariosQuinta;
    }

    public void setHorariosQuinta(List<String> horariosQuinta) {
        this.horariosQuinta = horariosQuinta;
    }

    public List<String> getHorariosSexta() {
        return horariosSexta;
    }

    public void setHorariosSexta(List<String> horariosSexta) {
        this.horariosSexta = horariosSexta;
    }

    public List<String> getHorariosSabado() {
        return horariosSabado;
    }

    public void setHorariosSabado(List<String> horariosSabado) {
        this.horariosSabado = horariosSabado;
    }

    public List<HorarioTurma> getListaHT() {
        return listaHT;
    }

    public void setListaHT(List<HorarioTurma> listaHT) {
        this.listaHT = listaHT;
    }

    public String getDisciplinaSelecionada() {
        return disciplinaSelecionada;
    }

    public void setDisciplinaSelecionada(String disciplinaSelecionada) {
        this.disciplinaSelecionada = disciplinaSelecionada;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public List<String> getTurmasString() {
        return turmasString;
    }

    public void setTurmasString(List<String> turmasString) {
        this.turmasString = turmasString;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.turma);
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
        final TurmaBean other = (TurmaBean) obj;
        if (!Objects.equals(this.turma, other.turma)) {
            return false;
        }
        return true;
    }

}
