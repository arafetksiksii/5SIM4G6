package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.text.SimpleDateFormat;
import java.util.Date;
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
       void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        reservation.setIdReservation("123");

        // Parsing the date once
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = sdf.parse("2024");
            reservation.setAnneeUniversitaire(date);  // Set the date here
        } catch (java.text.ParseException e) {
            e.printStackTrace(); // You can also log it or handle it as needed
        }

        reservation.setEstValide(false);
        reservation.setEtudiants(null);
    }

    @Test
    void testRetrieveAllReservations() {
        Reservation reservation2 = new Reservation();
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation, reservation2));

        assertEquals(2, reservationService.retrieveAllReservations().size());
    }

    @Test
    void testRetrieveReservation() {
        when(reservationRepository.findById("123")).thenReturn(Optional.of(reservation));

        Reservation retrievedReservation = reservationService.retrieveReservation("123");
        assertNotNull(retrievedReservation);
        assertEquals("123", retrievedReservation.getIdReservation());
    }

    @Test
    void testAddReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation addedReservation = reservationService.addReservation(reservation);
        assertNotNull(addedReservation);
        assertEquals("123", addedReservation.getIdReservation());
    }

    @Test
    void testModifyReservation() {
        when(reservationRepository.existsById("123")).thenReturn(true);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation modifiedReservation = reservationService.modifyReservation(reservation);
        assertNotNull(modifiedReservation);
        assertEquals("123", modifiedReservation.getIdReservation());
    }

    @Test
    void testRemoveReservation() {
        doNothing().when(reservationRepository).deleteById("123");

        reservationService.removeReservation("123");
        verify(reservationRepository, times(1)).deleteById("123");
    }
}
