/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.service;

import java.util.List;
import javax.ws.rs.BadRequestException;
import org.hibernate.Session;
import thanhnd.entity.Category;
import thanhnd.entity.Place;
import thanhnd.helper.argorithm.Point;
import thanhnd.helper.argorithm.WeiszfeldCalculator;
import thanhnd.repository.CategoryRepository;
import thanhnd.repository.PlaceRepository;

/**
 *
 * @author thanh
 */
public class PlaceService {

    private final Session session;
    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;
    private final String TAG = "PlaceService: ";

    public PlaceService(Session session) {
        this.session = session;
        this.placeRepository = new PlaceRepository(session);
        this.categoryRepository = new CategoryRepository(session);
    }

    public List<Place> getAll() {
        return placeRepository.findAll();
    }

    public List<Place> findPlacesByCategoryAndPoints(String category, List<Point> points) {
        double radian = 10; //in km
        WeiszfeldCalculator weiszfeldCalculator = new WeiszfeldCalculator(points);
        Point geometricPoint = weiszfeldCalculator.getGeometricMedianPoint();
        Category categoryObject = categoryRepository.getCategoryByName(category)
                .orElseThrow(() -> new BadRequestException("Wrong category: " + category));
        List<Place> places = placeRepository.findByDistanceSmallerThanWithCategory(geometricPoint.getX(), geometricPoint.getY(), radian, categoryObject);
        
        return places;
    }

}
