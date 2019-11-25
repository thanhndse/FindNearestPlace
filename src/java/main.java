
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import thanhnd.entity.Category;
import thanhnd.utils.HibernateUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thanh
 */
public class main {
    public static void main(String[] args) {
        Category category = new Category();
        category.setName("123");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
        catch (Exception ex){
            if (session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        }
    }
}
