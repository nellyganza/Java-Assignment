/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.dao;

import auca.library.model.CheckinoutView;
import auca.library.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NISHIMWE Elyse
 */
public class CheckInOutDao {
      static Session session = null;
    public void saveCheckInOut(CheckinoutView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(b);
        tx.commit();
        session.close();
    }
    public void updateCheckInOut(CheckinoutView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(b);
        tx.commit();
        session.close();
    }
    public void deleteCheckInOut(CheckinoutView b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(b);
        tx.commit();
        session.close();
    }
    public List<CheckinoutView> getCheckInOut() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(CheckinoutView.class);
        List<CheckinoutView> book = crt.list();
        tx.commit();
        session.close();
        return book;
    }    
    public CheckinoutView findById(String enity, String id){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        CheckinoutView b = (CheckinoutView) session.get(enity, id);
        tx.commit();
        session.close();
        return b;
    }
    
    public String getOperationNumber(String cid,String bid){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx= session.beginTransaction();
        Query c = session.createSQLQuery("select opnumber from checkinout_View where bookid='"+bid+"' and regno = '"+cid+"' and operationcategory = 'CHECK OUT' and status = 'have' and rownum=1");
        Object opnumber = c.uniqueResult();
        System.out.println(opnumber);
        tx.commit();
        session.close();
        return opnumber.toString();
    }
    
    public void updateOperation(String cid,String bid){
        String opnum = getOperationNumber(cid,bid);
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("update checkinout_View set status = 'returned' where opnumber = '"+opnum+"'");
        q.executeUpdate();
        tx.commit();
        session.close();
    }
    public List<CheckinoutView> getReportOfData(String colname,String id,String opcat){
      List<CheckinoutView> data = new ArrayList<>();
       try{ 
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from CheckinoutView where "+colname+" = '"+id+"' and operationcategory = '"+opcat+"'");
        data = q.list();
        tx.commit();
        session.close();
    }catch(HibernateException ex){
           JOptionPane.showMessageDialog(null, ex.getMessage());
    }
        return data;
    }
    public List<CheckinoutView> getReportOfDataDate(Date date1,Date date2,String opcat){
        List<CheckinoutView> data = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria c = session.createCriteria(CheckinoutView.class);
        c.add(Restrictions.between("datetime", date1, date2));
        c.add(Restrictions.eq("operationcategory", opcat));
        data = c.list();
        tx.commit();
        session.close();
        return data;
    }
    public List<Object[]> getReportOfDataCategory(String cat,String opcat){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("select checkinout_view.opnumber,checkinout_view.regno,checkinout_view.bookid,checkinout_view.datetime,checkinout_view.operationcategory,checkinout_view.status from checkinout_view  join book_view  on checkinout_view.bookid=book_view.bookid join bookcategory_view  on book_view.categoryid=bookcategory_view.categoryid  where categoryname = '"+cat+"' and operationcategory= '"+opcat+"'");
        //Query q = session.createSQLQuery("select * from checkinout_view c, book_view b, bookcategory_view bc where c.bookid= b.bookid and b.categoryid=bc.categoryid and categoryname = '"+cat+"' and operationcategory= '"+opcat+"'");
        List<Object[]> data = q.list();
        tx.commit();
        session.close();
        return data;
    }
}
