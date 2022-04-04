package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
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
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        // Suppression de la base Ticket avant chaque lancement de test
        // La base de test Ticket est donc vidée AVANT chaque lancement de test
        // La base parking est censée avoir tous les spots remis disponibles
        // APRES CHAQUE TEST DONC :
        // Un enregistrement Ticket "ABCDEF" est créé et l'indicateur du parking Slot 1 topé "Non disponible"
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown() {

    }

    @Test
    public void testParkingACar() {
        //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability
        //
        // Le test consiste à vérifier, qu'après lancement du test :
        // 1. un enregistrement avec la plaque d'immatriculation "ABCDEF"
        // est bien créé en base de Test "Ticket"
        // 2. que la place de parking (enregistrement No2 type CAR) est bien topée "1" (disponible)
        // GIVEN
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        // WHEN
        parkingService.processIncomingVehicle();
        // THEN
        assertThat(ticketDAO.getTicket("ABCDEF")).isNotNull();
        // Le 1er Spot de Parking disponible est le deuxième (le premier étant pris pour "ABCDEF")
        assertThat(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).isEqualTo(2);
    }

    /*
    @Test
    public void testParkingLotExit() {
        //TODO: check that the fare generated and out time are populated correctly in the database
        //
        // GIVEN
        testParkingACar();
        // Un Ticket "ABCDEF" est bien enregistré en base et le 1er Spot Parking est non disponible
        //when(inputReaderUtil.readSelection()).thenReturn(1);
        // when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        // Ecrire un Ticket "SaveTicket" - Avoir un ticket en base
        //
        // WHEN
        parkingService.processExitingVehicle();
        // THEN
        assertThat(ticketDAO.)

    }*/

}
