package com.amazon.ata.metrics.classroom.activity;

import com.amazon.ata.metrics.classroom.dao.ReservationDao;
import com.amazon.ata.metrics.classroom.dao.models.UpdatedReservation;
import com.amazon.ata.metrics.classroom.metrics.MetricsConstants;
import com.amazon.ata.metrics.classroom.metrics.MetricsPublisher;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import java.time.ZonedDateTime;
import javax.inject.Inject;

/**
 * Handles requests to modify a reservation
 */
public class ModifyReservationActivity {

    private ReservationDao reservationDao;
    private MetricsPublisher metricsPublisher;

    /**
     * Construct ModifyReservationActivity.
     * @param reservationDao Dao used for modify reservations.
     */
    @Inject
    public ModifyReservationActivity(ReservationDao reservationDao, MetricsPublisher metricsPublisher) {
        this.reservationDao = reservationDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Modifies the given reservation.
     * Increment the ModifiedReservationCount metric
     *
     * @param reservationId Id to modify reservations for
     * @param checkInDate modified check in date
     * @param numberOfNights modified number of nights
     * @return UpdatedReservation that includes the old reservation and the updated reservation details.
     */
    public UpdatedReservation handleRequest(final String reservationId, final ZonedDateTime checkInDate,
                                            final Integer numberOfNights) {
        // Update an existing reservation
        UpdatedReservation updatedReservation = reservationDao.modifyReservation(reservationId, checkInDate,
            numberOfNights);

        // Increment the ModifiedReservationCount metric
        //                         metric-name                  , value, unit-of-measure-for-value
        metricsPublisher.addMetric(MetricsConstants.MODIFY_COUNT, 1    , StandardUnit.Count);

        // Store a ReservationRevenue metric using the difference in totalCost of modified Reservation and original
        // The UpdatedReservation object contains teh new and original reservation

        double revenueDifference = updatedReservation.getModifiedReservation().getTotalCost()
                         .subtract(updatedReservation.getOriginalReservation().getTotalCost())
                         .doubleValue();

        metricsPublisher.addMetric(MetricsConstants.BOOKING_REVENUE,      // metric name
                                   revenueDifference,                     // value as a double
                                   StandardUnit.None);                    // unit-of-measure

        return updatedReservation;
    }
}
