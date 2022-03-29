package com.parkit.parkingsystem.constants;

public class Fare {
    public static final double BIKE_RATE_PER_HOUR = 1.0;
    public static final double CAR_RATE_PER_HOUR = 1.5;
    // Ajout pour gestion Fonstionnalité No 1 : 30' gratuites
    // 1 - Prix tarif réduit
    public static final double REDUCED_RATE = 0;
    // 2 - Durée pour bénéficier tarif réduit (ici = 30')
    public static final double DURATION_REDUCE_RATE = 0.5;
}
