package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParkingSpotDAOTest {

    // Pour accès à la base de Test (et non de prod)
    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        // Classe de "nettoyage de la base de Test "Ticket"
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        // Suppression de la base Ticket avant chaque lancement de test
        // et mise à jour slot 1 Parking à disponible
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    public void verifyGetNextAvailableSlot() {
        // GIVEN
        ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        // WHEN
        // Implique d'avoir la disponibilité du premier parking à "False"
        // Disponibilité du deuxième parking à "True"
        int nextAvailableSlot = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
        // THEN
        assertThat(nextAvailableSlot).isEqualTo(2);

    }

    /*
    @Test
    public void verifyGetTicketCount() {
        // GIVEN
        TicketDAO ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        // WHEN
        // Implique d'avoir un enregistrement Ticket "ABCDEF" en base de test
        int nbTicket = ticketDAO.getTicketCount("ABCDEF");
        // THEN
        assertThat(nbTicket).isEqualTo(1);
    }*/

}
