/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.helper.googleapi;

/**
 *
 * @author thanh
 */
public class Candidate {
    private String formatted_address;
    private Geometry geometry;
    private String name;

    public Candidate() {
    }

    public Candidate(String formatted_address, Geometry geometry, String name) {
        this.formatted_address = formatted_address;
        this.geometry = geometry;
        this.name = name;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
