/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.repository;


import org.hibernate.Session;
import org.hibernate.query.Query;
import thanhnd.entity.User;
import thanhnd.utils.HibernateUtil;

/**
 *
 * @author thanh
 */
public class UserRepository {
    
    private final Session session;

    public UserRepository() {
       session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public User findByUsernameAndPassword(String username, String password) {
        session.beginTransaction();
        Query query = session.createQuery("select u from User u where u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User) query.getResultList().stream().findFirst().orElse(null);
        session.getTransaction().commit();
        return user;
    }
}
