/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.helper.argorithm;

/**
 *
 * @author thanh
 */
public class Point {
    private double x;
    private double y;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double calculateDistance(Point otherPoint){
        return Math.sqrt((this.getX() - otherPoint.getX()) * (this.getX() - otherPoint.getX()) + (this.getY() - otherPoint.getY()) * (this.getY() - otherPoint.getY()));
    }
    
    //find real distance in meter with x is latitude and y is longitude with Haversine formula
    public double calculateDistanceInReal(Point point){
        int r = 6371;
        double latitude1 = Math.toRadians(getX());
        double latitude2 = Math.toRadians(point.getX());
        double deltaLatitude = Math.toRadians(getX() - point.getX());
        double deltaLongitude = Math.toRadians(getY() - point.getY());
        
        double a = Math.sin(deltaLatitude/2) * Math.sin(deltaLatitude/2)
                + Math.cos(latitude1) * Math.cos(latitude2)
                * Math.sin(deltaLongitude/2) * Math.sin(deltaLongitude/2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        double d = r * c;
        
        return d;
    }
    
}
