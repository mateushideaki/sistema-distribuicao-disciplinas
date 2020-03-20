/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.dao;

import br.com.prototipo.entity.Disciplina;
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
public class DisciplinaDao {

    private Session sessao = HibernateUtil.getSessionFactory().openSession();
    private Transaction trans;
    private List<Disciplina> list;
    private List<String> listaParaTurmas;

    public List<String> getListParaTurmas() {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Disciplina.class);
        cri.addOrder(Order.asc("codigo"));
        ProjectionList projection = Projections.projectionList();
        projection.add(Projections.property("codigo"));
        cri.setProjection(projection);
        listaParaTurmas = cri.list();
        return listaParaTurmas;
    }
    
    public List<String> getListDisciplinasString() {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Disciplina.class);
        Criteria cri2 = sessao.createCriteria(Disciplina.class);
        cri.addOrder(Order.asc("codigo"));
        cri2.addOrder(Order.asc("codigo"));
        
        ProjectionList projection = Projections.projectionList();
        projection.add(Projections.property("codigo"));
        
        ProjectionList projection2 = Projections.projectionList();
        projection2.add(Projections.property("nome"));
        
        cri.setProjection(projection);
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

    
    public Disciplina getDisciplina(String codigo) { //codigo usado para retornar a disciplina cujo codigo e passado por parametro
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Disciplina.class);
        Criterion _cod = Restrictions.like("codigo", codigo, MatchMode.EXACT);
        cri.add(_cod);
        cri.setMaxResults(1);
        Object result = cri.uniqueResult(); 
        Disciplina d = new Disciplina();
        if (result != null){
            d = (Disciplina)result;
        }
        return d;
    }

    public List<Disciplina> getList(Disciplina d) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Disciplina.class);
        Criterion _nome = Restrictions.like("nome", d.getNome(), MatchMode.ANYWHERE);
        cri.add(_nome);
        cri.addOrder(Order.asc("nome"));
        this.list = cri.list();
        return list;
    }

    public void addDisciplina(Disciplina d) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.save(d);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
            sessao.close();
        }
    }

    public void removeDisciplina(Disciplina d) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.delete(d);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void updateDisciplina(Disciplina d) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.update(d);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

}
