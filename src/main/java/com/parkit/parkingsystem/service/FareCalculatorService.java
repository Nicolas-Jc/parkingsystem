package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        // DEBUG La méthode getHours de la classe Date est dépréciée.
        // Remplacée par "getTime()" - type long
        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();

        // FONCTIONNALITE_2 - Reduction 5%
        double billingRate = 1;

        // TODO: Some tests are failing here. Need to check if this logic is correct
        long duration = outHour - inHour;
        double hourDuration = ((double) duration / 1000 / 60 / 60);

        // FONCTIONNALITE_1 - 30 MN GRATUITES
        if (hourDuration <= Fare.DURATION_REDUCE_RATE) {
            ticket.setPrice(Fare.REDUCED_RATE_TIME);
        } else {
            // FONCTIONNALITE_2 - MISE A JOUR EVENTUEL DU TAUX DE REDUCTION
            if (ticket.isDiscount()) {
                billingRate = Fare.REDUCED_RATE_FIDELITY;
            }

            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(hourDuration * Fare.CAR_RATE_PER_HOUR * billingRate);
                    break;
                }
                case BIKE: {
                    ticket.setPrice(hourDuration * Fare.BIKE_RATE_PER_HOUR * billingRate);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
            }
        }
    }
}