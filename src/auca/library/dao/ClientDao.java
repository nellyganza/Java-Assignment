/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.dao;

import auca.library.model.ClientView;
import auca.library.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author NISHIMWE Elyse
 */
public class ClientDao {
    static Session session = null;
    public void saveClient(ClientView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(b);
        tx.commit();
        session.close();
    }
    public void updateClient(ClientView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(b);
        tx.commit();
        session.close();
    }
    public void deleteClient(ClientView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(b);
        tx.commit();
        session.close();
    }
    public List<ClientView> getClient() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(ClientView.class);
        List<ClientView> book = crt.list();
        tx.commit();
        session.close();
        return book;
    }    
    public ClientView findById(String id){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        ClientView c = (ClientView) session.get(ClientView.class, id);
        tx.commit();
        session.close();
        return c;
    }
    public List<ClientView> findByName(String name){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from ClientView where FirstName||' '||LastName = '"+name+"'");
        List<ClientView> c = q.list();
        tx.commit();
        session.close();
        return c;
    }
}
