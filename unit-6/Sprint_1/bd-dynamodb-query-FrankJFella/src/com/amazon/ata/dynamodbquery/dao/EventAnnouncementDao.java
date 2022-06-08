package com.amazon.ata.dynamodbquery.dao;

import com.amazon.ata.dynamodbquery.converter.ZonedDateTimeConverter;
import com.amazon.ata.dynamodbquery.dao.models.Event;
import com.amazon.ata.dynamodbquery.dao.models.EventAnnouncement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.checkerframework.checker.units.qual.A;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Manages access to EventAnnouncement items.
 */
public class EventAnnouncementDao {

    private DynamoDBMapper mapper;

    // Define a constant to represent the Date/time converter we are using
    // This is done to make it easier if we need to change the converter
    // NOT REQUIRED FOR DYNAMODB ACCESS
    private static final ZonedDateTimeConverter ZONED_DATE_TIME_CONVERTER = new ZonedDateTimeConverter();
    /**
     * Creates an EventDao with the given DDB mapper.
     * @param mapper DynamoDBMapper
     */
    @Inject
    public EventAnnouncementDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Gets all event announcements for a specific event.
     *
     * @param eventId The event to get announcements for.
     * @return the list of event announcements.
     */
    public List<EventAnnouncement> getEventAnnouncements(String eventId) {
        // TODONE: implement

        // Instantiate EventAnnouncement object for interaction with DynamoDB
        EventAnnouncement anEventAnnoucement = new EventAnnouncement();
        //       and set it's eventId to the eventId we are given as a parameter
        anEventAnnoucement.setEventId(eventId);

        // Define a Query Expression to tell DynamoDB which rows we want to retrieve
        //       based on the "hash key" eventId
        DynamoDBQueryExpression<EventAnnouncement> whatWeWant =
                      new DynamoDBQueryExpression<EventAnnouncement>()
                         .withHashKeyValues(anEventAnnoucement);

        // Go to DynamoDB to get the rows based on the Query Expression
        //       and return them to caller
        //                  class-to-returned      , Query Expression
        return mapper.query(EventAnnouncement.class, whatWeWant);
    }

    /**
     * Get all event announcements posted between the given dates for the given event.
     *
     * @param eventId The event to get announcements for.
     * @param startTime The start time to get announcements for.
     * @param endTime The end time to get announcements for.
     * @return The list of event announcements.
     */
    public List<EventAnnouncement> getEventAnnouncementsBetweenDates(String eventId, ZonedDateTime startTime,
                                                                     ZonedDateTime endTime) {
        // TODO: implement
        // Because there are multiple conditions for our search
        // Define the values used in the condition to tell DynamoDB what we want in a Map
        // The key for the Map is an identifier for the values to be used
        //     in the condition for searching the data base
        // The value in the map contains the value to be used in the condition we send to DynamoDB
        //  identifer, data-type-of-value
        Map< String  , AttributeValue> searchValues = new HashMap<>();

        // Add the values to used in the condition for the Query Expression in to the Map
        // We need to convert the parameters to String to use in the condition for the Query Expression
        // It is common for the identifier of the value to be :column-name
        searchValues.put(":eventId"  , new AttributeValue().withS(eventId));
        searchValues.put(":startDate", new AttributeValue().withS(ZONED_DATE_TIME_CONVERTER.convert(startTime)));
        searchValues.put(":endDate"  , new AttributeValue().withS(new ZonedDateTimeConverter().convert(endTime)));

        // Define a Query Expression with a condition using the values in the Map
        DynamoDBQueryExpression<EventAnnouncement> querySearchExpression =
                  new DynamoDBQueryExpression<EventAnnouncement>()
                  //                          column  = id-in-map      column-in-table       id-in-map      id-in-map
                 .withKeyConditionExpression("eventId = :eventId  and  timePublished between :startDate and :endDate")
                 .withExpressionAttributeValues(searchValues);

        // Go to DynamoDB to retrieve the rows between the dates given using the Query Expression
        //    and return them
        return mapper.query(EventAnnouncement.class, querySearchExpression);
    }

    /**
     * Creates a new event announcement.
     *
     * @param eventAnnouncement The event announcement to create.
     * @return The newly created event announcement.
     */
    public EventAnnouncement createEventAnnouncement(EventAnnouncement eventAnnouncement) {
        mapper.save(eventAnnouncement);
        return eventAnnouncement;
    }
}
