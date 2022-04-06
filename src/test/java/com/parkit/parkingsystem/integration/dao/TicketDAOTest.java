package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class TicketDAOTest {

    // Pour accès à la base de Test (et non de prod)
    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @BeforeAll
    private static void setUp() throws Exception {
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        // Classe de "nettoyage de la base
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        // Suppression de la base Ticket avant chaque lancement de test
        // et mise à jour slot 1 Parking à disponible
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    public void verifyGetTicket() {
        // GIVEN
        Ticket ticket = new Ticket();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("TESTGET");
        ticket.setPrice(0);
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setOutTime(null);
        ticketDAO.saveTicket(ticket);
        // WHEN
        Ticket returnTicket = ticketDAO.getTicket("TESTGET");
        // THEN
        assertThat(returnTicket).isNotNull();
    }

    @Test
    public void verifyGetTicketCount() {
        //GIVEN
        Ticket ticket = new Ticket();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("TESTGET");
        ticket.setPrice(0);
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setOutTime(null);
        ticketDAO.saveTicket(ticket);
        // WHEN
        //Ticket returnTicket = ticketDAO.getTicket("TESTGET");
        int nbTicket = ticketDAO.getTicketCount("TESTGET");
        // THEN
        assertThat(nbTicket).isEqualTo(1);
    }

    @Test
    public void verifyUpdateTicket() {
        // GIVEN
        Ticket ticket = new Ticket();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setId(1);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("TESTUPD");
        ticket.setPrice(0);
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setOutTime(new Date(System.currentTimeMillis() - (5 * 60 * 1000)));
        ticketDAO.saveTicket(ticket);
        ticket.setPrice(333);
        // WHEN
        ticketDAO.updateTicket(ticket);
        // THEN
        //assertThat(nbTicket).isEqualTo(1);
        assertThat(ticketDAO.getTicket("TESTUPD").getPrice()).isEqualTo(333);

    }

}
