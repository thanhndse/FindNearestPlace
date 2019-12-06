/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thanh
 */
@Entity
@Table(name = "Place")
@XmlRootElement
public class Place implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String image;
    private String fullAddress;
    private String fullAddressFormatted;
    private double latitude;
    private double longitude;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "PlaceCategory",
            joinColumns = {@JoinColumn(name = "placeId")},
            inverseJoinColumns = {@JoinColumn(name = "categoryId")}
    )
    private List<Category> categories = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public void addCategory(Category category){
        categories.add(category);
        category.getPlaces().add(this);
    }
    
    public void addManyCategories(List<Category> newCategories){
        newCategories.forEach(newCategory -> {
            this.categories.add(newCategory);
            newCategory.getPlaces().add(this);
        });
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getFullAddressFormatted() {
        return fullAddressFormatted;
    }

    public void setFullAddressFormatted(String fullAddressFormatted) {
        this.fullAddressFormatted = fullAddressFormatted;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    
    
}
