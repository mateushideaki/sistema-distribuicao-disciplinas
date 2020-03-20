/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.dao;

import br.com.prototipo.entity.Professor;
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
public class ProfessorDao {

    private Session sessao = HibernateUtil.getSessionFactory().openSession();
    private Transaction trans;
    private List<Professor> list;

    public List<Professor> getList(String p) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();
        trans = sessao.beginTransaction();

        Criteria cri = sessao.createCriteria(Professor.class);
        Criterion _nome = Restrictions.like("nome", p, MatchMode.ANYWHERE);
        cri.add(_nome);
        cri.addOrder(Order.asc("nome"));
        this.list = cri.list();
        return this.list;
    }

    public List<String> getListProfessoresString() {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Professor.class);
        Criteria cri2 = sessao.createCriteria(Professor.class);
        cri.addOrder(Order.asc("registro"));
        cri2.addOrder(Order.asc("registro"));

        ProjectionList projection = Projections.projectionList();
        projection.add(Projections.property("registro"));

        ProjectionList projection2 = Projections.projectionList();
        projection2.add(Projections.property("nome"));

        cri.setProjection(projection);
        cri2.setProjection(projection2);

        List<String> l1 = cri.list();
        List<String> l2 = cri2.list();
        List<String> l3 = new ArrayList<>();
        Iterator<String> it1 = l1.iterator();
        Iterator<String> it2 = l2.iterator();

        while (it1.hasNext()) {
            String aux1 = it1.next();
            String aux2 = it2.next();
            String aux3 = aux1 + " - " + aux2;
            l3.add(aux3);
        }

        return l3;
    }

    public Professor getProfessor(String codigo) { //codigo usado para retornar a disciplina cujo codigo e passado por parametro
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Professor.class);
        Criterion _cod = Restrictions.like("registro", codigo, MatchMode.EXACT);
        cri.add(_cod);
        cri.setMaxResults(1);
        Object result = cri.uniqueResult();
        Professor p = new Professor();
        if (result != null) {
            p = (Professor) result;
        }
        return p;
    }

    public void addProfessor(Professor p) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.save(p);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sessao.close();
        }
    }

    public void removeProfessor(Professor p) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.delete(p);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProfessor(Professor p) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.update(p);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
