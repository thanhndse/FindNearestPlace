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
class Viewport {
    public Location northeast; 
    public Location southwest; 

    public Viewport() {
    }

    public Viewport(Location northeast, Location southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public Location getNortheast() {
        return northeast;
    }

    public void setNortheast(Location northeast) {
        this.northeast = northeast;
    }

    public Location getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Location southwest) {
        this.southwest = southwest;
    }
    
    
}
