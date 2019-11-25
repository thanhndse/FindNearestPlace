/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import thanhnd.utils.HibernateUtil;

/**
 *
 * @author thanh
 */
public class PlaceRepository {

    private final Session session;

    public PlaceRepository() {
       session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

}
