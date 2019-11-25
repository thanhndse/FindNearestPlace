/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.repository;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import thanhnd.entity.Category;
import thanhnd.utils.HibernateUtil;

/**
 *
 * @author thanh
 */
public class CategoryRepository implements Serializable{
    
    private final Session session;

    public CategoryRepository() {
       session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public void saveCategory(Category category){
        try{
            session.getTransaction().begin();
            session.persist(category);
            session.getTransaction().commit();
        }
        catch(Exception ex){
            if (session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
        }   
    }
}
