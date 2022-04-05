package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TicketDAOTest {

    // Pour accès à la base de Test (et non de prod)
    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    /*
    @BeforeAll
    private static void setUp() throws Exception {
        //ticketDAO.dataBaseConfig = dataBaseTestConfig;
        // Classe de "nettoyage de la base de Test "Ticket"
        dataBasePrepareService = new DataBasePrepareService();
    }*/

    /*
    @BeforeEach
    private void setUpPerTest() throws Exception {
        // Suppression de la base Ticket avant chaque lancement de test
        dataBasePrepareService.clearDataBaseEntries();
    }*/

    @Test
    public void verifyGetTicket() {
        // GIVEN
        TicketDAO ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        // WHEN
        // Implique d'avoir un enregistrement Ticket "ABCDEF" en base de test
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        // THEN
        assertThat(ticket).isNotNull();
        //assertThat(ticketDAO.getTicket("TESTIT").getPrice()).isNotEqualTo(0);
        //assertThat(ticketDAO.getTicket("TESTIT").getOutTime()).isNotNull();
    }

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
    }

    /*
    @Test
    public void verifyUpdateTicket() {
        // GIVEN
        TicketDAO ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        // WHEN
        // Implique d'avoir un enregistrement Ticket "ABCDEF" en base de test
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        int IdTicket = ticket.getId();
        ticket.setPrice(333);
        ticketDAO.saveTicket(ticket);
        ticketDAO.getTicket("ABCDEF");
        // THEN
        //assertThat(nbTicket).isEqualTo(1);
        assertThat(ticketDAO.getTicket("ABCDEF").getPrice()).isEqualTo(333);
        //assertThat(ticketDAO.getTicket("TESTIT").getOutTime()).isNotNull();
    }*/

    /*
    @Test
    public void verifySaveTicket() {
        // GIVEN
        TicketDAO ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        //ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        //parkingSpotDAO.updateParking(parkingSpot);
        Ticket ticket = new Ticket();
        ticket.setParkingSpot(1);
        ticket.setVehicleRegNumber("TESTSAVE");
        ticket.setPrice(0);
        ticket.setInTime(new Date(System.currentTimeMillis()));
        ticket.setOutTime(null);
        //ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        // WHEN
        ticketDAO.saveTicket(ticket);
        // THEN
        //assertThat(ticket).isNotNull();
        // assertThat(ticke)
        //assertThat(ticketDAO.getTicket("TESTIT").getOutTime()).isNotNull();
    }*/
}
