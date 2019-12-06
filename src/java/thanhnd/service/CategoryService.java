/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.service;

import java.util.List;
import org.hibernate.Session;
import thanhnd.entity.Category;
import thanhnd.repository.CategoryRepository;
import thanhnd.repository.PlaceRepository;

/**
 *
 * @author thanh
 */
public class CategoryService {

    private final Session session;
    private final CategoryRepository categoryRepository;
    private final String TAG = "CategoryService: ";

    public CategoryService(Session session) {
        this.session = session;
        this.categoryRepository = new CategoryRepository(session);
    }
    
    public List<Category> getAllCategories(){
        return categoryRepository.getAllCategories();
    }

    
}
