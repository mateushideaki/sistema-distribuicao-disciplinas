/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.util;

import br.com.prototipo.entity.Alocacao;
import br.com.prototipo.entity.Professor;
import br.com.prototipo.entity.Turma;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Mateus
 */
public class Leitor {

    public List<Alocacao> ler() throws FileNotFoundException {
        File arq = new File("C:\\ModeloCplexDistribuicao\\RespModeloAlocacao.mst");
        Scanner scanner = new Scanner(new FileReader(arq));
        List<Alocacao> listaAlocacao = new ArrayList<>();
        
        int i = 1;
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            if (i > 10) {                                                        //IGNORA O COMECO DO ARQUIVO
                StringTokenizer tok = new StringTokenizer(linha);                //PEGA A LINHA E CRIA UM TOKENIZER
                if (tok.nextToken().equalsIgnoreCase("</variables>")) {          //QUANDO CHEGA AQUI ACABOU O ARQUIVO, ENTAO DA BREAK
                    break;  
                }
                String variavel = tok.nextToken();                          
                variavel = variavel.substring(6, variavel.length() - 1);        //PEGA A VARIAVEL PxTy
                
                StringTokenizer tok2 = new StringTokenizer(variavel);           
                String registroProf;
                registroProf = tok2.nextToken("TD");                            //PEGA O REGISTRO DO PROF
                registroProf = registroProf.substring(1);                       //IGNORA O P DO COMECO
                int indTurma = Integer.parseInt(tok2.nextToken());              //PEGA O INDICE DA TURMADISCIPLINA
                tok.nextToken();
                String verifica = tok.nextToken();
                char bin = verifica.charAt(7);                                  //PEGA O VALUE DA VARIAVEL (0 OU 1)
                if(bin == '1'){                                                 //SE FOR 1 FAZ A ALOCACAO
                    Professor prof = new Professor();
                    Turma t = new Turma();
                    prof.setRegistro(registroProf);
                    t.setId(indTurma);
                    Alocacao aloc = new Alocacao();
                    aloc.setTurma(t);
                    aloc.setProfessor(prof);
                    listaAlocacao.add(aloc);
                }
            }
            i++;
        }
        scanner.close();
        return listaAlocacao;
        
    }
    

}
