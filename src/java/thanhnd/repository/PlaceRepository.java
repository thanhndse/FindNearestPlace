/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import thanhnd.entity.Category;
import thanhnd.entity.Place;
import thanhnd.helper.argorithm.Point;
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

    public Optional<Place> findByNameAndAddress(String name, String fullAddress) {
        session.beginTransaction();
        Optional<Place> optionalPlace = session.createQuery("from Place where name = :name and fullAddress = :fullAddress")
                .setParameter("name", name)
                .setParameter("fullAddress", fullAddress)
                .getResultStream()
                .findFirst();
        session.getTransaction().commit();
        return optionalPlace;
    }

    public List<Place> findAll() {
        session.beginTransaction();
        List<Place> places = session.createQuery("from Place").getResultList();
        session.getTransaction().commit();
        return places;
    }

    public List<Place> findByDistanceSmallerThanWithCategory(double magicPointX, double magicPointY, double radius, Category category) {
        session.beginTransaction();
        List<Place> rawPlaces;
        if (category == null){
            rawPlaces = session.createQuery("from Place p")
                .getResultList();
        }
        else {
            rawPlaces = session.createQuery("from Place p where :category MEMBER OF p.categories")
                .setParameter("category", category)
                .getResultList();
        }
        
        List<Place> resultPlaces = new ArrayList<>();
        Point magicPoint = new Point(magicPointX, magicPointY);
        for (Place place : rawPlaces) {
            Point point = new Point(place.getLatitude(), place.getLongitude());
            double distance = point.calculateDistanceInReal(magicPoint);
            if (distance <= radius) {
                resultPlaces.add(place);
            }
        }
        session.getTransaction().commit();
        return resultPlaces;
    }
}
