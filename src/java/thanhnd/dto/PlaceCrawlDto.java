/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author thanh
 */
public class PlaceCrawlDto implements Serializable {
    private String name;
    private String image;
    private String fullAddress;
    private Set<String> categoriesStringSet = new HashSet<>();

    public PlaceCrawlDto() {
    }

    public PlaceCrawlDto(String name, String image, String fullAddress) {
        this.name = name;
        this.image = image;
        this.fullAddress = fullAddress;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Set<String> getCategoriesStringSet() {
        return categoriesStringSet;
    }

    public void setCategoriesStringSet(Set<String> categoriesStringSet) {
        this.categoriesStringSet = categoriesStringSet;
    }

    
}
