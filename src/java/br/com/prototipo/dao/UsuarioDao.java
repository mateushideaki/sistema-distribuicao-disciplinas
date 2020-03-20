/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.dao;

import br.com.prototipo.entity.Professor;
import br.com.prototipo.entity.Usuario;
import br.com.prototipo.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mateus
 */
public class UsuarioDao {

    private Session sessao = HibernateUtil.getSessionFactory().openSession();
    private Transaction trans;

    public String addUSuarioProfessor(Usuario u, String reg) {
        String retorno = null;
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            Criteria cri = sessao.createCriteria(Professor.class);
            Criterion _login = Restrictions.like("registro", reg, MatchMode.EXACT);
            cri.add(_login);
            if (cri.uniqueResult() != null) {
                u.setProfessor((Professor) cri.uniqueResult());
                u.setRegistro(u.getProfessor().getRegistro());
                Criteria cri2 = sessao.createCriteria(Usuario.class);
                _login = Restrictions.like("registro", u.getProfessor().getRegistro(), MatchMode.EXACT);
                cri2.add(_login);
                if (cri2.uniqueResult() == null) {
                    sessao.save(u);
                    trans.commit();
                    retorno = "sucesso";
                } else {
                    retorno = "jaCadastrado";
                }
            } else {
                retorno = "naoEncontrado";
            }
        } catch (Exception e) {
            e.printStackTrace();
            sessao.close();
        } finally {
            return retorno;
        }
    }

    public void addUsuario(Usuario u, String reg) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();
            Criteria cri = sessao.createCriteria(Usuario.class);
            Criterion _login = Restrictions.like("registro", reg, MatchMode.EXACT);
            cri.add(_login);
            if (cri.list().isEmpty()) {
                sessao.save(u);
                trans.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
            sessao.close();
        }
    }

    public void alterarUsuario(Usuario u) {
        try {
            if (sessao.isConnected()) {
                sessao.close();
            }
            sessao = HibernateUtil.getSessionFactory().openSession();
            trans = sessao.beginTransaction();

            sessao.update(u);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario validarLogin2(Usuario u) {
        if (sessao.isConnected()) {
            sessao.close();
        }
        sessao = HibernateUtil.getSessionFactory().openSession();
        Criteria cri = sessao.createCriteria(Usuario.class);
        Criterion _login = Restrictions.eq("login", u.getLogin());
        cri.add(_login);
        Criterion _senha = Restrictions.eq("senha", u.getSenha());
        cri.add(_senha);
        return (Usuario) cri.uniqueResult();
    }

}
