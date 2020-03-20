/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.util;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Mateus
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final ThreadLocal sessionThread;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            sessionThread = new ThreadLocal();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getCurrentSession() {
        Session session = (Session)sessionThread.get();
		if (session == null) {
			session = sessionFactory.openSession(); 
			sessionThread.set(session);
		}
		return session;
    }
    
    public static void closeSession() {
		Session session = (Session)sessionThread.get();
		sessionThread.set(null);
		if (session != null && session.isOpen()) {
			session.flush();
			session.close();
		}
	}
    
    
}
