package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        // Classe de "nettoyage de la base de Test "Ticket"
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        // Suppression de la base Ticket avant chaque lancement de test
        dataBasePrepareService.clearDataBaseEntries();
    }

    /*
    @AfterAll
    private static void tearDown() {
    }*/

    @Test
    public void testParkingACar() throws Exception {
        //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability
        //
        // Le test consiste à vérifier, qu'après son execution :
        // 1. un enregistrement avec la plaque d'immatriculation "ABCDEF"
        // est bien créé en base de Test "Ticket" via l'Objet ticketDAO
        // 2. que la place de parking (enregistrement No2 type CAR) est bien topée "1" (disponible)
        // L'enregistrement No1 étant lui topé "0" (non disponible)
        // GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        // WHEN
        parkingService.processIncomingVehicle();
        // THEN
        assertThat(ticketDAO.getTicket("ABCDEF")).isNotNull();
        // Le 1er Spot de Parking disponible est le deuxième (le premier étant pris pour "ABCDEF")
        assertThat(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).isEqualTo(2);
    }


    @Test
    public void testParkingLotExit() throws Exception {
        //TODO: check that the fare generated and out time are populated correctly in the database
        //
        // GIVEN
        // Pre_Requis : CRER un Ticket en base avec heure d'entrée moins 1 heure
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("TESTIT");
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        parkingSpotDAO.updateParking(parkingSpot);
        Ticket ticket = new Ticket();
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("TESTIT");
        ticket.setPrice(0);
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setOutTime(null);
        ticketDAO.saveTicket(ticket);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        // WHEN
        parkingService.processExitingVehicle();
        // THEN
        assertThat(ticketDAO.getTicket("TESTIT").getPrice()).isNotEqualTo(0);
        assertThat(ticketDAO.getTicket("TESTIT").getOutTime()).isNotNull();

    }

}
