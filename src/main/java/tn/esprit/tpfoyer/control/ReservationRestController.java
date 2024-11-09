package tn.esprit.tpfoyer.control;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationRestController {

    private static final Logger logger = LogManager.getLogger(ReservationRestController.class);

    IReservationService reservationService;
    // ttt
    // http://localhost:8089/tpfoyer/reservation/retrieve-all-reservations
    @GetMapping("/retrieve-all-reservations")
    public List<Reservation> getReservations() {
        logger.info("Retrieving all reservations");
        List<Reservation> listReservations = reservationService.retrieveAllReservations();
        logger.debug("Number of reservations retrieved: {}", listReservations.size());
        return listReservations;
    }

    // http://localhost:8089/tpfoyer/reservation/retrieve-reservation/8
    @GetMapping("/retrieve-reservation/{reservation-id}")
    public Reservation retrieveReservation(@PathVariable("reservation-id") String rId) {
        logger.info("Retrieving reservation with ID: {}", rId);
        Reservation reservation = reservationService.retrieveReservation(rId);
        if (reservation == null) {
            logger.error("Reservation with ID: {} not found", rId);
        } else {
            logger.debug("Reservation retrieved: {}", reservation);
        }
        return reservation;
    }

    // http://localhost:8089/tpfoyer/reservation/retrieve-reservation-date-status/{d}/{v}
    @GetMapping("/retrieve-reservation-date-status/{d}/{v}")
    public List<Reservation> retrieveReservationParDateEtStatus
            (@PathVariable("d") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date d, @PathVariable("v") boolean b) {
        logger.info("Retrieving reservations for date: {} and status: {}", d, b);
        List<Reservation> reservations = reservationService.trouverResSelonDateEtStatus(d, b);
        logger.debug("Number of reservations retrieved: {}", reservations.size());
        return reservations;
    }

    // http://localhost:8089/tpfoyer/reservation/add-reservation
    @PostMapping("/add-reservation")
    public Reservation addReservation(@RequestBody Reservation r) {
        logger.info("Adding a new reservation: {}", r);
        Reservation reservation = reservationService.addReservation(r);
        logger.debug("Reservation added: {}", reservation);
        return reservation;
    }

    // http://localhost:8089/tpfoyer/reservation/remove-reservation/{reservation-id}
    @DeleteMapping("/remove-reservation/{reservation-id}")
    public void removeReservation(@PathVariable("reservation-id") String rId) {
        logger.info("Removing reservation with ID: {}", rId);
        reservationService.removeReservation(rId);
        logger.debug("Reservation with ID: {} removed", rId);
    }

    // http://localhost:8089/tpfoyer/reservation/modify-reservation
    @PutMapping("/modify-reservation")
    public Reservation modifyReservation(@RequestBody Reservation r) {
        logger.info("Modifying reservation: {}", r);
        Reservation reservation = reservationService.modifyReservation(r);
        logger.debug("Reservation modified: {}", reservation);
        return reservation;
    }
}

