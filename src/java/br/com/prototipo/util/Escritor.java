package br.com.prototipo.util;

import br.com.prototipo.entity.HorarioTurma;
import br.com.prototipo.entity.Preferencia;
import br.com.prototipo.entity.Professor;
import br.com.prototipo.entity.Turma;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateus
 */
public class Escritor {

    public void escreve(List<Professor> listaProf, List<Preferencia> listaPref, List<Turma> listaT, List<HorarioTurma> listaHorarios, boolean anual, boolean eq, boolean hext) throws IOException {

        File diretorio = new File("C:\\ModeloCplexDistribuicao");
        diretorio.mkdir();
        File arq1 = new File(diretorio, "ModeloAlocacao.lp");
        try {
            arq1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter arq = new FileWriter(arq1);
        PrintWriter gravarArq = new PrintWriter(arq);

        //FUNCAO OBJETIVO       
        gravarArq.printf("Maximize%n");
        gravarArq.printf("obj: ");
        int cont = 0;
        for (int indiceP = 0; indiceP < listaProf.size(); indiceP++) {      //PARA TODOS OS PROFESSORES
            Professor professor = listaProf.get(indiceP);
            Preferencia preferencia = null;
            for (int indicePref = 0; indicePref < listaPref.size(); indicePref++) {     //PROCURA NA LISTA DE PREFERENCIAS
                if (listaPref.get(indicePref).getRegistroProfessor().equalsIgnoreCase(professor.getRegistro())) {
                    preferencia = listaPref.get(indicePref);            //SE ENCONTRAR, SALVA NA VARIAVEL PREFERENCIA
                }
            }
            if (preferencia != null) {   //SE HOUVER ENCONTRADO A PREFERENCIA, ESCREVE O MODELO COM OS PESOS      
                for (int td = 0; td < listaT.size(); td++) {
                    Turma turma = listaT.get(td);
                    if (preferencia.getTurma1() != null) {
                        if (turma.getCodigo().equalsIgnoreCase(preferencia.getTurma1().getCodigo())) {
                            if (cont > 0) {
                                gravarArq.printf(" + ");
                            }
                            gravarArq.printf(" 15 P%sTD%d ", professor.getRegistro(), turma.getId());
                            cont++;
                        }
                    }

                    if (preferencia.getTurma2() != null) {
                        if (turma.getCodigo().equalsIgnoreCase(preferencia.getTurma2().getCodigo())) {
                            if (cont > 0) {
                                gravarArq.printf(" + ");
                            }
                            gravarArq.printf(" 10 P%sTD%d ", professor.getRegistro(), turma.getId());
                            cont++;
                        }
                    }

                    if (preferencia.getTurma3() != null) {
                        if (turma.getCodigo().equalsIgnoreCase(preferencia.getTurma3().getCodigo())) {
                            if (cont > 0) {
                                gravarArq.printf(" + ");
                            }
                            gravarArq.printf(" 5 P%sTD%d", professor.getRegistro(), turma.getId());
                            cont++;
                        }
                    }

                    if (preferencia.getDescarte1() != null) {
                        if (turma.getDisciplina().getCodigo().equalsIgnoreCase(preferencia.getDescarte1().getCodigo())) {
                            if (cont > 0) {
                                gravarArq.printf(" - ");
                            }
                            gravarArq.printf(" 20 P%sTD%d", professor.getRegistro(), turma.getId());
                            cont++;
                        }
                    }

                    if (preferencia.getDescarte2() != null) {
                        if (turma.getDisciplina().getCodigo().equalsIgnoreCase(preferencia.getDescarte2().getCodigo())) {
                            if (cont > 0) {
                                gravarArq.printf(" - ");
                            }
                            gravarArq.printf(" 20 P%sTD%d", professor.getRegistro(), turma.getId());
                            cont++;
                        }
                    }

                    if (preferencia.getDescarte3() != null) {
                        if (turma.getDisciplina().getCodigo().equalsIgnoreCase(preferencia.getDescarte3().getCodigo())) {
                            if (cont > 0) {
                                gravarArq.printf(" - ");
                            }
                            gravarArq.printf(" 20 P%sTD%d", professor.getRegistro(), turma.getId());
                            cont++;
                        }
                    }
                }
            }
        }
        if (eq) {
            gravarArq.printf(" - L"); //VARIAVEL GARGALO USADA PARA O MODELO EQUITATIVO
        }
        //RESTRICOES
        gravarArq.printf("%nSubject To%n");
        //RESTRICAO DE CONFLITO DE HORARIO
        //VERIFICANDO CONFLITOS DE HORARIOS IGUAIS
        List<Conflito> listaConflitos = new ArrayList<>();
        for (int i = 0; i < (listaHorarios.size() - 1); i++) {
            for (int j = (i + 1); j < listaHorarios.size(); j++) {
                HorarioTurma h1 = listaHorarios.get(i);
                HorarioTurma h2 = listaHorarios.get(j);
                if (h1.getHorario().getId() == h2.getHorario().getId() && (h1.getTurma().getSemestre() == h2.getTurma().getSemestre() || h1.getTurma().isAnual() || h2.getTurma().isAnual())) {
                    Conflito conf = new Conflito();
                    boolean jaTem = false;
                    conf.setDisc1(h1.getCodigoTurma());
                    conf.setDisc2(h2.getCodigoTurma());
                    for (int k = 0; k < listaConflitos.size(); k++) {
                        if (conf.getDisc1().equalsIgnoreCase(listaConflitos.get(k).getDisc1()) && conf.getDisc2().equalsIgnoreCase(listaConflitos.get(k).getDisc2())) {
                            jaTem = true;
                        }
                        if (conf.getDisc1().equalsIgnoreCase(listaConflitos.get(k).getDisc2()) && conf.getDisc2().equalsIgnoreCase(listaConflitos.get(k).getDisc1())) {
                            jaTem = true;
                        }
                    }
                    if (!jaTem) {
                        listaConflitos.add(conf);
                    }
                }
            }
        }

        //VERIFICANDO CONFLITOS DE HORARIOS EXTREMOS
        if (hext) {
            for (int i = 0; i < (listaHorarios.size() - 1); i++) {
                HorarioTurma horario1 = listaHorarios.get(i);
                for (int j = 0; j < listaHorarios.size(); j++) {
                    HorarioTurma horario2 = listaHorarios.get(j);
                    if (!horario1.getTurma().getCodigo().equalsIgnoreCase(horario2.getTurma().getCodigo())) {
                        if (horario1.getTurma().getSemestre() == horario2.getTurma().getSemestre() || horario1.getTurma().isAnual() || horario2.getTurma().isAnual()) {
                            if (horario1.getHorario().getAula().equalsIgnoreCase("14") && horario2.getHorario().getAula().equalsIgnoreCase("1")) {
                                if (horario1.getHorario().getDia() == (horario2.getHorario().getDia() - 1)) {
                                    Conflito conflito = new Conflito();
                                    conflito.setDisc1(horario1.getCodigoTurma());
                                    conflito.setDisc2(horario2.getCodigoTurma());
                                    boolean jaTem = false;
                                    for (int k = 0; k < listaConflitos.size(); k++) {
                                        if (conflito.getDisc1().equalsIgnoreCase(listaConflitos.get(k).getDisc1()) && conflito.getDisc2().equalsIgnoreCase(listaConflitos.get(k).getDisc2())) {
                                            jaTem = true;
                                        }
                                        if (conflito.getDisc1().equalsIgnoreCase(listaConflitos.get(k).getDisc2()) && conflito.getDisc2().equalsIgnoreCase(listaConflitos.get(k).getDisc1())) {
                                            jaTem = true;
                                        }
                                    }
                                    if (!jaTem) {
                                        listaConflitos.add(conflito);
                                    }
                                }
                            }
                            if (horario1.getHorario().getAula().equalsIgnoreCase("1") && horario2.getHorario().getAula().equalsIgnoreCase("14")) {
                                if (horario1.getHorario().getDia() == (horario2.getHorario().getDia() + 1)) {
                                    Conflito conflito = new Conflito();
                                    conflito.setDisc1(horario1.getCodigoTurma());
                                    conflito.setDisc2(horario2.getCodigoTurma());
                                    boolean jaTem = false;
                                    for (int k = 0; k < listaConflitos.size(); k++) {
                                        if (conflito.getDisc1().equalsIgnoreCase(listaConflitos.get(k).getDisc1()) && conflito.getDisc2().equalsIgnoreCase(listaConflitos.get(k).getDisc2())) {
                                            jaTem = true;
                                        }
                                        if (conflito.getDisc1().equalsIgnoreCase(listaConflitos.get(k).getDisc2()) && conflito.getDisc2().equalsIgnoreCase(listaConflitos.get(k).getDisc1())) {
                                            jaTem = true;
                                        }
                                    }
                                    if (!jaTem) {
                                        listaConflitos.add(conflito);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //ESCREVENDO AS RESTRIÇÕES DE CONFLITO
        for (int indConf = 0; indConf < listaConflitos.size(); indConf++) {
            Conflito conflito = listaConflitos.get(indConf);
            for (int indTurma = 0; indTurma < (listaT.size() - 1); indTurma++) {
                Turma turma1 = listaT.get(indTurma);
                if (conflito.temConflito(turma1.getCodigo())) {
                    for (int indTurmaFrente = (indTurma + 1); indTurmaFrente < listaT.size(); indTurmaFrente++) {
                        Turma turma2 = listaT.get(indTurmaFrente);
                        if (conflito.temConflito(turma2.getCodigo())) {
                            for (int indiceProfessor = 0; indiceProfessor < listaProf.size(); indiceProfessor++) {
                                Professor professor = listaProf.get(indiceProfessor);
                                gravarArq.printf("P%sTD%d + P%sTD%d <= 1%n", professor.getRegistro(), turma1.getId(), professor.getRegistro(), turma2.getId());
                            }
                        }
                    }
                }
            }
        }

        //TODAS AS TURMAS DEVEM TER PROFESSORES
        for (int i = 0; i < listaT.size(); i++) {
            Turma turma = listaT.get(i);
            for (int j = 0; j < listaProf.size(); j++) {
                Professor professor = listaProf.get(j);
                if (j > 0) {
                    gravarArq.printf(" + ");
                }
                gravarArq.printf("P%sTD%d", professor.getRegistro(), turma.getId());
            }
            gravarArq.printf(" = 1%n");
        }

        if (anual) {
            //RESTRICAO DE CARGA MAXIMA ANUAL
            for (int i = 0; i < listaProf.size(); i++) {
                Professor professor = listaProf.get(i);
                for (int j = 0; j < listaT.size(); j++) {
                    Turma turma = listaT.get(j);
                    if (j > 0) {
                        gravarArq.printf(" + ");
                    }
                    gravarArq.printf("%d P%sTD%d", turma.getCargaTotal(), professor.getRegistro(), turma.getId());
                }
                gravarArq.printf(" <= %d%n", professor.getCargaMaxA());
            }

            //RESTRICAO DE CARGA MINIMA ANUAL
            for (int i = 0; i < listaProf.size(); i++) {
                Professor professor = listaProf.get(i);
                for (int j = 0; j < listaT.size(); j++) {
                    Turma turma = listaT.get(j);
                    if (j > 0) {
                        gravarArq.printf(" + ");
                    }
                    gravarArq.printf("%d P%sTD%d", turma.getCargaTotal(), professor.getRegistro(), turma.getId());
                }
                gravarArq.printf(" >= %d%n", professor.getCargaMinA());
            }
        } else {
            //RESTRICAO DE CARGA MAXIMA SEMANAL
            for (int i = 0; i < listaProf.size(); i++) {
                Professor professor = listaProf.get(i);
                //SEMESTRE 1
                for (int j = 0; j < listaT.size(); j++) {
                    Turma turma = listaT.get(j);
                    if (turma.isAnual() || turma.getSemestre() == 1) {
                        if (j > 0) {
                            gravarArq.printf(" + ");
                        }
                        gravarArq.printf("%d P%sTD%d", turma.getCargaSemanal(), professor.getRegistro(), turma.getId());
                    }
                }
                gravarArq.printf(" <= %d%n", professor.getCargaMaxS());
                //SEMESTRE2
                for (int j = 0; j < listaT.size(); j++) {
                    Turma turma = listaT.get(j);
                    if (turma.isAnual() || turma.getSemestre() == 2) {
                        if (j > 0) {
                            gravarArq.printf(" + ");
                        }
                        gravarArq.printf("%d P%sTD%d", turma.getCargaSemanal(), professor.getRegistro(), turma.getId());
                    }
                }
                gravarArq.printf(" <= %d%n", professor.getCargaMaxS());
            }

            //RESTRICAO DE CARGA MINIMA SEMANAL
            for (int i = 0; i < listaProf.size(); i++) {
                Professor professor = listaProf.get(i);
                //SEMESTRE1
                for (int j = 0; j < listaT.size(); j++) {
                    Turma turma = listaT.get(j);
                    if (turma.isAnual() || turma.getSemestre() == 1) {
                        if (j > 0) {
                            gravarArq.printf(" + ");
                        }
                        gravarArq.printf("%d P%sTD%d", turma.getCargaSemanal(), professor.getRegistro(), turma.getId());
                    }
                }
                gravarArq.printf(" >= %d%n", professor.getCargaMinS());
                //SEMESTRE2
                for (int j = 0; j < listaT.size(); j++) {
                    Turma turma = listaT.get(j);
                    if (turma.isAnual() || turma.getSemestre() == 2) {
                        if (j > 0) {
                            gravarArq.printf(" + ");
                        }
                        gravarArq.printf("%d P%sTD%d", turma.getCargaSemanal(), professor.getRegistro(), turma.getId());
                    }
                }
                gravarArq.printf(" >= %d%n", professor.getCargaMinS());
            }
        }

        //RESTRICAO DE EQUITATIVIDADE
        if (eq) {
            for (int i = 0; i < listaProf.size(); i++) {
                Professor professor = listaProf.get(i);
                for (int j = 0; j < listaT.size(); j++) {
                    Turma turma = listaT.get(j);
                    if (j > 0) {
                        gravarArq.printf(" + ");
                    }
                    gravarArq.printf("%d P%sTD%d", turma.getCargaTotal(), professor.getRegistro(), turma.getId());
                }
                gravarArq.printf(" - L <= 0%n");
            }
        }

        //DECLARACAO DAS VARIAVEIS BINARIAS
        gravarArq.printf("%nBinary%n");
        for (int i = 0; i < listaProf.size(); i++) {
            Professor professor = listaProf.get(i);
            for (int j = 0; j < listaT.size(); j++) {
                Turma td = listaT.get(j);
                gravarArq.printf("P%sTD%d%n", professor.getRegistro(), td.getId());
            }
        }
        gravarArq.printf("%nEnd");

        arq.close();
    }
}
