/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.dao;

import br.com.prototipo.entity.HorarioTurma;
import br.com.prototipo.entity.Turma;
import br.com.prototipo.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mateus
 */
public class TurmaDao {

    private Session sessao = HibernateUtil.getSessionFactory().openSession();
    private Transaction trans;
    private List<Turma> lista;
    private List<HorarioTurma> list2;

    public List<String> getListTurmasString() {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Turma.class);
        cri.addOrder(Order.asc("codigo"));
        
        Criteria cri2 = sessao.createCriteria(Turma.class);
        cri2.addOrder(Order.asc("codigo"));
        
        ProjectionList projection = Projections.projectionList();
        projection.add(Projections.property("codigo"));
        cri.setProjection(projection);
        
        ProjectionList projection2 = Projections.projectionList();
        projection2.add(Projections.property("nomeDisciplina"));
        cri2.setProjection(projection2);
        
        List<String> l1 = cri.list();
        List<String> l2 = cri2.list();
        List<String> l3 = new ArrayList<>();
        Iterator<String> it1 = l1.iterator();
        Iterator<String> it2 = l2.iterator();
        while(it1.hasNext()){
            String aux1 = it1.next();
            String aux2 = it2.next();
            String aux3 = aux1 + " - " + aux2;
            l3.add(aux3);
        }

        return l3;
    }
    
    public List<Turma> listaParaAlocacao() {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();


        Criteria cri = sessao.createCriteria(Turma.class);
        this.lista = cri.list();
        return this.lista;
    }
    
    public List<Turma> getList(String busca) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();



        Criteria cri = sessao.createCriteria(Turma.class);
        Criterion _codigo = Restrictions.like("codigo", busca, MatchMode.START);
        cri.add(_codigo);
        cri.addOrder(Order.asc("codigo"));
        this.lista = cri.list();
        return this.lista;
    }
    
    public Turma getTurma(String codigo){   
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();


        Criteria cri = sessao.createCriteria(Turma.class);
        Criterion _cod = Restrictions.like("codigo", codigo, MatchMode.EXACT);
        cri.add(_cod);
        cri.setMaxResults(1);
        Object result = cri.uniqueResult(); 
        Turma turma = new Turma();
        if (result != null){
            turma = (Turma)result;
        }
        return turma;
    }

    public String addTurma(Turma t) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.save(t);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return "erro";
        }
        return "sucesso";
    }

    public void removerTurma(Turma t) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.delete(t);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTurma(Turma t) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.update(t);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public void addHorarioTurma(HorarioTurma ht) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.save(ht);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public List<HorarioTurma> getList(String turma, String busca) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(HorarioTurma.class);
        Criterion _codigo = Restrictions.like("codigoTurma", turma, MatchMode.ANYWHERE);
        cri.add(_codigo);
        Criterion _ano = Restrictions.like("nomeDia", busca, MatchMode.ANYWHERE);
        cri.add(_ano);
        this.list2 = cri.list();
        return list2;
    }
    
    public List<HorarioTurma> getList2(String turma) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(HorarioTurma.class);
        Criterion _codigo = Restrictions.like("codigoTurma", turma, MatchMode.ANYWHERE);
        cri.add(_codigo);
        this.list2 = cri.list();
        return list2;
    }
    
    public List<HorarioTurma> getListAlocacao(){
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(HorarioTurma.class);
        List<HorarioTurma> listaAloc;
        listaAloc = cri.list();
        return listaAloc;
    }

    public void removeHorarioTurma(HorarioTurma ht) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.delete(ht);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }


    public void updateHorarioTurma(HorarioTurma ht) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.merge(ht);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
