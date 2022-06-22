package com.amazon.ata.metrics.classroom.activity;

import com.amazon.ata.metrics.classroom.dao.ReservationDao;
import com.amazon.ata.metrics.classroom.dao.models.Reservation;
import com.amazon.ata.metrics.classroom.metrics.MetricsConstants;
import com.amazon.ata.metrics.classroom.metrics.MetricsPublisher;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import javax.inject.Inject;

/**
 * Handles requests to book a reservation.
 */
public class BookReservationActivity {

    private ReservationDao   reservationDao;
    private MetricsPublisher metricsPublisher;

    /**
     * Constructs a BookReservationActivity
     * @param reservationDao Dao used to create reservations.
     */
    @Inject
    public BookReservationActivity(ReservationDao reservationDao, MetricsPublisher metricsPublisher) {
        this.reservationDao = reservationDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Creates a reservation with the provided details.
     * Increment the BookedReservationCount metric
     * Store a ReservationRevenue metric using the totalCost of Reservation
     *
     * @param reservation Reservation to create.
     * @return
     */
    public Reservation handleRequest(Reservation reservation) {
        // Create a new reservation in the data store - response contains information about reservation
        Reservation response = reservationDao.bookReservation(reservation);

        // Increment the BookedReservationCount metric
        //                         metric-name                              , value, unit-of-measure-for-value
        metricsPublisher.addMetric(MetricsConstants.BOOKED_RESERVATION_COUNT, 1    , StandardUnit.Count);

        // Store a ReservationRevenue metric using the totalCost of Reservation
        metricsPublisher.addMetric(MetricsConstants.BOOKING_REVENUE,      // metric name
                                   response.getTotalCost().doubleValue(), // value as a double
                                   StandardUnit.None);                    // unit-of-measure
        return response;
    }
}
