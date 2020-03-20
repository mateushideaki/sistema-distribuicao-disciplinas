/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.dao;

import br.com.prototipo.entity.Alocacao;
import br.com.prototipo.entity.Curso;
import br.com.prototipo.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mateus
 */
public class AlocacaoDao {
    private Session sessao = HibernateUtil.getSessionFactory().openSession();
    private Transaction trans;
    private List<Alocacao> list;
   

    public List<Alocacao> getList(String ano) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();
        trans = sessao.beginTransaction();

        Criteria cri = sessao.createCriteria(Alocacao.class);
        Criterion _ano = Restrictions.like("ano", ano, MatchMode.ANYWHERE);
        cri.add(_ano);
        this.list = cri.list();
        return list;
    }
    

    public void addAlocacao(Alocacao a) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.save(a);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAlocacao(Alocacao a) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.delete(a);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void updateAlocacao(Alocacao a) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.update(a);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
}
