/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.dao;

import br.com.prototipo.entity.Curso;
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
public class CursoDao {
    private Session sessao = HibernateUtil.getSessionFactory().openSession();
    private Transaction trans;
    private List<Curso> list;

    
    public List<String> getListParaTurmas() {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Curso.class);
        Criteria cri2 = sessao.createCriteria(Curso.class);
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
    
    public Curso getCursoEscolhido(String cod) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Curso.class);
        Criterion _cod = Restrictions.like("codigo", cod, MatchMode.EXACT);
        cri.add(_cod);
        
        cri.setMaxResults(1);
        Object result = cri.uniqueResult(); 
        Curso c = new Curso();
        if (result != null){
            c = (Curso)result;
        }
        return c;
    }

    public List<Curso> getList(String c) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();
        trans = sessao.beginTransaction();

        Criteria cri = sessao.createCriteria(Curso.class);
        Criterion _nome = Restrictions.like("nome", c, MatchMode.ANYWHERE);
        cri.add(_nome);
        cri.addOrder(Order.asc("nome"));
        this.list = cri.list();
        return list;
    }
    

    public void addCurso(Curso c) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.save(c);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
            sessao.close();
        } 
    }

    public void removeCurso(Curso c) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.delete(c);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCurso(Curso c) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.update(c);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
