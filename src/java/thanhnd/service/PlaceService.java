/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.service;

import thanhnd.repository.CategoryRepository;
import thanhnd.repository.PlaceRepository;

/**
 *
 * @author thanh
 */
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService() {
        this.placeRepository = new PlaceRepository();
    }
}
