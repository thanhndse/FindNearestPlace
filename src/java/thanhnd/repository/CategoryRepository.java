/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.repository;

import java.io.Serializable;
import java.util.Optional;
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
    
    public CategoryRepository(Session session) {
        this.session = session;
    }
    
    public Optional<Category> getCategoryByName(String name){
        session.beginTransaction();
        Optional<Category> optionalCategory = session.createQuery("from Category c where c.name = :name")
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
        session.getTransaction().commit();
        return optionalCategory;
    }
}
