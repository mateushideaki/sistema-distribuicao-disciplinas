/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.dao;

import br.com.prototipo.entity.Horario;
import br.com.prototipo.util.HibernateUtil;
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
public class HorarioDao {
    private Session sessao = HibernateUtil.getSessionFactory().openSession();
    private Transaction trans;
    private List<Horario> list;


    public List<Horario> getList(String h) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();
        trans = sessao.beginTransaction();

        Criteria cri = sessao.createCriteria(Horario.class);
        Criterion _nome = Restrictions.like("nomeDia", h, MatchMode.ANYWHERE);
        cri.add(_nome);
        this.list = cri.list();
        return list;
    }
    
    public List<String> getListParaDisciplinas(String dia) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Horario.class);
        Criterion _nome = Restrictions.like("nomeDia", dia, MatchMode.ANYWHERE);
        cri.add(_nome);
        ProjectionList projection = Projections.projectionList();
        projection.add(Projections.property("aula"));
        cri.setProjection(projection);
        return cri.list();
    }
    
    public Horario getHorarioEscolhido(String dia, String aula) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Horario.class);
        Criterion _nome = Restrictions.like("nomeDia", dia, MatchMode.EXACT);
        cri.add(_nome);
        Criterion _aula = Restrictions.like("aula", aula, MatchMode.EXACT);
        cri.add(_aula);
        return (Horario) cri.uniqueResult();
    }
    

    public void addHorario(Horario h) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.save(h);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
            sessao.close();
        } 
    }

    public void removeHorario(Horario h) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.delete(h);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHorario(Horario h) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.update(h);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}


