/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.repository;

import java.util.Optional;
import org.hibernate.Session;
import thanhnd.entity.Place;
import thanhnd.utils.HibernateUtil;

/**
 *
 * @author thanh
 */
public class PlaceRepository {

    private final Session session;

    public PlaceRepository(Session session) {
        this.session = session;
    }

    public Optional<Place> save(Place place) {
        try {
            session.getTransaction().begin();
            session.persist(place);
            session.getTransaction().commit();
            return Optional.of(place);

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Place> findById(Integer id) {
        session.beginTransaction();
        Place place = session.get(Place.class, id);
        session.getTransaction().commit();
        return place != null ? Optional.of(place) : Optional.empty();
    }

    public boolean isPlaceExistedByName(String name) {
        session.beginTransaction();
        boolean result = session.createQuery("from Place where name = :name")
                .setParameter("name", name)
                .getResultStream()
                .findAny().isPresent();
        session.getTransaction().commit();
        return result;
    }
}
