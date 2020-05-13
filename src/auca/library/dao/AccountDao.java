/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.dao;

import auca.library.util.HibernateUtil;
import auca.library.model.Account;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NISHIMWE Elyse
 */
public class AccountDao {
    Session session = null;
    public void createAccount(Account acc){
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(acc);
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public void updateAccount(Account acc){
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
                session.update(acc);
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
     public void changePassword(String  username,String newpass){
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
                session.createQuery("update Account set password = '"+newpass+"' where username = '"+username+"'").executeUpdate();
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public void deleteAccount(Account acc){
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(acc);
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public List<Account> displayAccount(){
        List<Account> accounts =null;
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            accounts = session.createCriteria(Account.class).list();
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return accounts;
    }
    public List<Account> findByEmail(String email){
        List<Account> accounts =null;
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            accounts = session.createCriteria(Account.class).add(Restrictions.eq("email", email)).list();
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return accounts;
    }
    public String findByUsername(String username){
        String password = null;
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            List accounts = session.createCriteria(Account.class).add(Restrictions.eq("username", username)).setProjection(Projections.property("password")).list();
            tx.commit();
            for(Object a:accounts){
                password = a.toString();
            }
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return password;
    }
    public List<Account> findByPhoneNumber(String phonenumber){
        List<Account> accounts =null;
        try{
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            accounts =  session.createCriteria(Account.class).add(Restrictions.eq("phonenumber", phonenumber)).list();
            tx.commit();
            session.close();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return accounts;
    }
}
