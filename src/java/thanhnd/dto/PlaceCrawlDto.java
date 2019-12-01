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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author thanh
 */
@XmlRootElement  
@XmlType(propOrder = {
    "name",
    "image",
    "fullAddress",
    "categoriesStringSet"
})
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
    
    
    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    @XmlElement
    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    @XmlElement
    public Set<String> getCategoriesStringSet() {
        return categoriesStringSet;
    }

    public void setCategoriesStringSet(Set<String> categoriesStringSet) {
        this.categoriesStringSet = categoriesStringSet;
    }

    
}
