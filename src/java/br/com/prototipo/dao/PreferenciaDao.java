/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.dao;

import br.com.prototipo.entity.Preferencia;
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
public class PreferenciaDao {
    private Session sessao = HibernateUtil.getSessionFactory().openSession();
    private Transaction trans;
    private List<Preferencia> list;

   public List<Preferencia> listaParaAlocacao(){
       if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();

        Criteria cri = sessao.createCriteria(Preferencia.class);
        this.list = cri.list();
        return list;
   }

    public Preferencia getPreferencia(String prof) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();
        trans = sessao.beginTransaction();

        Criteria cri = sessao.createCriteria(Preferencia.class);
        Criterion _nome = Restrictions.like("registroProfessor", prof, MatchMode.ANYWHERE);
        cri.add(_nome);
        return (Preferencia) cri.uniqueResult();
    }
    

    public void addPreferencia(Preferencia p) {
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

    public void removePreferencia(Preferencia p) {
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

    public void updatePreferencia(Preferencia p) {
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

