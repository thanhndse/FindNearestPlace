/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.webservice.service.service;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;
import thanhnd.entity.Category;
import thanhnd.entity.Place;
import thanhnd.helper.argorithm.Point;
import thanhnd.service.PlaceService;
import thanhnd.utils.HibernateUtil;

/**
 * REST Web Service
 *
 * @author thanh
 */
@Path("Places")
public class PlaceResource {
    
    private final PlaceService placeService;

    /**
     * Creates a new instance of GenericResource
     */
    public PlaceResource() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        placeService = new PlaceService(hibernateSession);
    }

    /**
     * Retrieves representation of an instance of thanhnd.webservice.service.service.GenericResource
     * @param category
     * @param points
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public List<Place> getPlacesByCategoryAndPoints(List<Point> points) {
        //TODO return proper representation object
        List<Place> places = placeService.findPlacesByPoints(points);
        return places;
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
