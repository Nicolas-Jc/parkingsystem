package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

       /* DEBUG La méthode getHours de la classe Date est dépréciée.
       // A remplacer par "getTime()" - type long
       // int inHour = ticket.getInTime().getHours();
       // int outHour = ticket.getOutTime().getHours();*/

        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();

        // FONCTIONNALITE_2
        double billingRate = 1;

        // TODO: Some tests are failing here. Need to check if this logic is correct
        // DEBUG : la durée (ci-dessous "duration") pour
        // toutes les durées <1H de stationnement, la méthode rend un résultat erronné
        // et uniquement ces 2 valeurs : 0 ou 1
        //int duration = outHour - inHour;
        long duration = outHour - inHour;

        // conversion durée millisecondes => heures et transtypage en double
        double hourDuration = ((double) duration / 1000 / 60 / 60);

        // FONCTIONNALITE_1 - 30 MN GRATUITES
        if (hourDuration <= Fare.DURATION_REDUCE_RATE) {
            ticket.setPrice(Fare.REDUCED_RATE_30MN);
        }
        // Fin Fonctionnalite_1
        else {
            // FONCTIONNALITE_2 - MISE A JOUR EVENTUEL DU TAUX DE REDUCTION
            if (ticket.getTopDiscount()) {
                billingRate = Fare.REDUCED_RATE_FIDELITY;
            }

            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    // FONCTIONNALITE_2 - Ajout Taux remise
                    ticket.setPrice(hourDuration * Fare.CAR_RATE_PER_HOUR * billingRate);
                    //ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                    break;
                }
                case BIKE: {
                    // FONCTIONNALITE_2 - Ajout Taux remise
                    ticket.setPrice(hourDuration * Fare.BIKE_RATE_PER_HOUR * billingRate);
                    //ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
            }
        }
    }
}