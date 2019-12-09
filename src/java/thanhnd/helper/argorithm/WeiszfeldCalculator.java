/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.helper.argorithm;

import java.util.List;

/**
 *
 * @author thanh
 */
public class WeiszfeldCalculator {

    private final List<Point> memberPoints;
    private Point currentPoint;
    private Point geometricMedianPoint;

    private final double epsilon = 0.000000000001;

    public WeiszfeldCalculator(List<Point> memberPoints) {
        this.memberPoints = memberPoints;
        this.currentPoint = null;
        this.geometricMedianPoint = null;
    }

    public Point getGeometricMedianPoint() {
        if (geometricMedianPoint == null) {
            // first assign current point to the mid point
            currentPoint = getMidPoint();

            double previousDistance;
            double afterDistance;
            //loop until differrence about distances between 2 time is smaller than epsilon
            boolean shouldContinue;
            Point pointBeforeCalculate = new Point(currentPoint.getX(), currentPoint.getY());
            do {
                shouldContinue = false;
                previousDistance = getCurrentTotalDistance();
                double sum1X = 0;
                double sum1Y = 0;
                double sum2 = 0;
                for (Point memberPoint : memberPoints) {
                    double distance = currentPoint.calculateDistance(memberPoint);
                    if (distance == 0) {
                        break;
                    } else {
                        sum2 += 1.0 / distance;
                        sum1X += memberPoint.getX() / distance;
                        sum1Y += memberPoint.getY() / distance;
                        currentPoint.setX(sum1X / sum2);
                        currentPoint.setY(sum1Y / sum2);
                    }
                }

                afterDistance = getCurrentTotalDistance();
                if (previousDistance - afterDistance < 0) {
                    currentPoint = pointBeforeCalculate;
                } else {
                    if ((previousDistance - afterDistance) > epsilon){
                        shouldContinue = true;
                    }
                }
            } while (shouldContinue);
            geometricMedianPoint = currentPoint;
        }
        return geometricMedianPoint;
    }

    //Geographic MidPoint 
    //or Center of Gravity
    private Point getMidPoint() {
        double sumX = 0;
        double sumY = 0;
        for (Point point : memberPoints) {
            sumX += point.getX();
            sumY += point.getY();
        }
        return new Point(sumX / memberPoints.size(), sumY / memberPoints.size());
    }

    private double getCurrentTotalDistance() {
        double sum = 0;
        for (Point point : memberPoints) {
            sum += Math.sqrt((currentPoint.getX() - point.getX()) * (currentPoint.getX() - point.getX()) + (currentPoint.getY() - point.getY()) * (currentPoint.getY() - point.getY()));
        }
        return sum;
    }

}
