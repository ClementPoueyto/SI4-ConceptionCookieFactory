package fr.unice.polytech.marceleat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MarcelEat{

    private double meterFee = 0.0005;
    private double constantFee = 0.5;

    public MarcelEat(){

    }

    /**
     * Entry point of the Marcel Eats API that provides the cost of using their delivery system
     * @param distance
     * @param pickupDate
     * @return the delivery fee taken by MarcelEats
     */
    public double askDelivery(double distance, LocalDateTime pickupDate){
        LocalDateTime currentDateTime = LocalDateTime.now();
        double deliveryFee = 0.0;
        if(distance<=10000){
            deliveryFee = constantFee + distance*meterFee;
            deliveryFee = (ChronoUnit.MINUTES.between(currentDateTime, pickupDate)>30) ? deliveryFee : deliveryFee*1.5;
        }
        return deliveryFee;
    }

}