/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.dao;

import auca.library.model.BookView;
import auca.library.model.BookcategoryView;
import auca.library.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NISHIMWE Elyse
 */
public class BookDao {
    static Session session = null;
    public void saveBook(BookView b) {
    try{  
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(b);
        tx.commit();
        session.close();
    }catch(HibernateException ex){
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
    JOptionPane.showMessageDialog(null, "Data Saved SuccessFully !!!");
    }
    public void updateBook(BookView b) {
    try{    
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(b);
        tx.commit();
        session.close();
        }catch(HibernateException ex){
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
        JOptionPane.showMessageDialog(null, "Data Updated SuccessFully !!!");
    }
    public void deleteBook(BookView b) {
    try{    session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(b);
        tx.commit();
        session.close();
        }catch(HibernateException ex){
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
    JOptionPane.showMessageDialog(null, "Data Deleted SuccessFully !!!");
    }
   public List<BookView> getBookData(){
        List<BookView> books = new ArrayList<>();
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from BookView");
        books = q.list();
        tx.commit();
        session.close();
        return books;
    } 
    public BookView findById(String id){
        BookView b = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            b = (BookView) session.get(BookView.class, id);
            tx.commit();
            session.close();
            }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return b;
    }
    public List<BookView> findByTitle(String title)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(BookView.class);
        crt.add(Restrictions.eq("title", title));
        List<BookView> b = crt.list();
        tx.commit();
        session.close();
        return b;
    }
    public String getBookId(String title){
      session  = HibernateUtil.getSessionFactory().openSession();
      Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(BookcategoryView.class);
        crt.add(Restrictions.like("categoryname", title));
         List bcat =  crt.list();
         String bc = null;
         for(Iterator it = bcat.iterator();it.hasNext();){
             bc = it.next().toString();
         }
        tx.commit();
        session.close();
        return bc;
    }
    public String getId(String className, String extcol, String col, String newid) {
        String id = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select " + newid + " from " + className + " where " + extcol + "='" + col + "'");
        List<String> list = q.list();
        tx.commit();
        session.close();
        for(String l:list)
            id=l;
        return id;
    }
}
