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
    
}
