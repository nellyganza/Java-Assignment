/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.dao;

import auca.library.model.CheckinoutView;
import auca.library.model.ClientView;
import auca.library.util.HibernateUtil;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

/**
 *
 * @author NISHIMWE Elyse
 */
public class Methods {

    private Session session = null;

    public List<Object> getBookCat() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select categoryname from Bookcategory");
        List<Object> catnames = q.list();
        tx.commit();
        session.close();
        return catnames;
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
    public String getRegNo(String name) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select regno from ClientView where FirstName||' '||LastName = '"+name+"'");
        Object names = q.uniqueResult();
        tx.commit();
        session.close();
        return names.toString();
    }
    public String getName(String names,String ClassName,String existencecol,String findcol){
        String name = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select "+names+" from "+ClassName+" where "+existencecol+" = '"+findcol+"'");
        List<String> list = q.list();
        tx.commit();
        session.close();
        for(String s:list)
            name=s;
        return name;
    }
        public Object getNameOfClient(String id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select FirstName||'  '||LastName as Name from ClientView where regno = '"+id+"'");
        Object catnames = q.uniqueResult();
        tx.commit();
        session.close();
        return catnames;
    }  
    public void increseOrDecrese(String bookid,String operationcategory){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q;
        if(operationcategory.equals("CHECK OUT")){
            q = session.createQuery("update BookView set numberofcopies = numberofcopies-1 where bookid = '"+bookid+"'");
        }
        else
        {
            q = session.createQuery("update BookView set numberofcopies = numberofcopies+1 where bookid = '"+bookid+"'");
        }
        q.executeUpdate();
        tx.commit();
        session.close();
    }
    public BigDecimal getNUmberofCopiesRem(String bookid){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select numberofcopies from BookView  where bookid = '"+bookid+"'");
        List<BigDecimal> num = q.list();
        tx.commit();
        session.close();
        BigDecimal n = null;
        for(BigDecimal i:num)
            n=i;
        return n;
    }
    public List<Object> getBookIntoCmb() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select title from Book");
        List<Object> catnames = q.list();
        tx.commit();
        session.close();
        return catnames;
    }
    public List<Object> getNameIntoCmb() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select FirstName||'  '||LastName as Name from Client");
        List<Object> catnames = q.list();
        tx.commit();
        session.close();
        return catnames;
    }  
    
    public List<ClientView> getBookIntoCmbIn(String id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("select title from Book where bookid in (select bookid from Checkinout where regno = '"+id+"' and status = 'Given')");
        List<ClientView> catnames = q.list();
        tx.commit();
        session.close();
        System.out.println(catnames);
        return catnames;
    }
    public List<Object> getNameIntoCmbIn() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("select FirstName||'  '||LastName as Name from Client where regno in (select regno from Checkinout where status like 'Given')");
        List<Object> catnames = q.list();
        tx.commit();
        session.close();
        return catnames;
    }
    public boolean checkIfHaveBook(String cid,String bid){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx= session.beginTransaction();
        Query c = session.createSQLQuery("select status from checkinout_View where bookid='"+bid+"' and regno = '"+cid+"' and operationcategory = 'CHECK OUT' and status = 'have' and rownum=1");
        Object status = c.uniqueResult();
        tx.commit();
        session.close();
        if(status!=null)
            return status.equals("have");
        else
            return false;
    }
    public String getOperationId(String date){
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria crt = session.createCriteria(CheckinoutView.class);
        Integer totalResult = ((Number) crt.setProjection(Projections.rowCount()).uniqueResult()).intValue()+1;
        session.close();
        String opid = "OP"+totalResult+date;
        return opid;
    }
}
