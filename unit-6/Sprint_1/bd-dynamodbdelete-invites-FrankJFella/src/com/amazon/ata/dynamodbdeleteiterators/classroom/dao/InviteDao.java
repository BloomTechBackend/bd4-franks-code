package com.amazon.ata.dynamodbdeleteiterators.classroom.dao;

import com.amazon.ata.dynamodbdeleteiterators.classroom.dao.models.Invite;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

/**
 * Manages access to Invite items.
 */
public class InviteDao {
    private DynamoDBMapper mapper;

    /**
     * Constructs a DAO with the given mapper.
     * @param mapper The DynamoDBMapper to use
     */
    @Inject
    public InviteDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Fetches an invite by event ID and member ID.
     * @param eventId The event ID of the invite
     * @param memberId The member ID of the invite
     * @return the invite, if found; null otherwise
     */
    public Invite getInvite(String eventId, String memberId) {
        return mapper.load(Invite.class, eventId, memberId);
    }

    /**
     * Fetches all invites sent to a given member.
     * @param memberId The ID of the member to fetch invites for (sent to)
     * @return List of Invite objects sent to the given member
     */
    public List<Invite> getInvitesSentToMember(String memberId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("memberId = :memberId")
            .withExpressionAttributeValues(ImmutableMap.of(":memberId", new AttributeValue(memberId)));
        return new ArrayList<>(mapper.scan(Invite.class, scanExpression));
    }

    /**
     * Creates a new invite.
     * @param invite The invite to create
     * @return The newly created invite
     */
    public Invite createInvite(Invite invite) {
        mapper.save(invite);
        return invite;
    }

    /**
     * Cancels the invite corresponding to the event + member IDs.
     * @param eventId event ID for the invite to cancel
     * @param memberId member ID for the invite to cancel
     * @return The updated Invite if found; null otherwise.
     */
    public Invite cancelInvite(String eventId, String memberId) {
        Invite invite = mapper.load(Invite.class, eventId, memberId);
        if (Objects.isNull(invite)) {
            return null;
        }

        if (!invite.isCanceled()) {
            invite.setCanceled(true);
            mapper.save(invite);
        }
        return invite;
    }

    /**
     * Deletes the invite indicated by eventId, memberId.
     * For extra safety, deletes conditional on the invite not having been
     * accepted (isAttending is false).
     * @param eventId The event the invite is for
     * @param memberId The member the invite is sent to
     * @return true if the invite was deleted; false if it was not deleted because the
     *         invite isAttending is set to true.
     */
    public boolean deleteInvite(String eventId, String memberId) {
        Invite inviteToDelete = new Invite();   // Instantiate object to give DynamoDB for deletion
        inviteToDelete.setMemberId(memberId);   // Set member to be deleted
        inviteToDelete.setEventId(eventId);     //     and event to be deleted

        // To have DynamoDB perform a conditional delete...
        //     1. Define a condition object for delete to be sent to DynamoDB - DynamoDBDeleteExpression
        //     2. Define the conditional expression to control the delete - ExpectedAttributeExpression
        //     3. Connect the condition, the conditional expression and the table attribute
        //     4. Send the combination of condition object and conditional expression to DynamoDB
        //     5. If an exception DOES NOT occur, the delete was successful

        //  1. Define a condition object for delete to be sent to DynamoDB
        DynamoDBDeleteExpression aDeleteExpression = new DynamoDBDeleteExpression();

        //  2. Define the conditional expression to control the delete
        //        .withComparisionOperator - identify the relational condition to be used
        //        .withValue - identify the value to be used in the condition expression
        //                     use Attribute() to specify the value you need
        ExpectedAttributeValue deleteCondition = new ExpectedAttributeValue()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withValue(new AttributeValue().withBOOL(false));
        // 3. Connect the condition object (DynamoDBDeleteExpression)
        //    with the conditional expression (ExpectedAttributeValue)
        //    and attribute in the table to be used in the condition
        // In Java: if (isAttending == false)
        // DynamoDBDeleteExpression.withExpectedEntry("table-attribute-name", ExpectedAttributeValue
        aDeleteExpression.withExpectedEntry("isAttending", deleteCondition);

        //  4. Send the combination of condition object and conditional expression to DynamoDB
        try {  // Since DynamoDB might throw and exception when to try tp delete
            //          object-to-delete, DynamoDBDeleteExpression
            mapper.delete(inviteToDelete, aDeleteExpression);
        }
        catch(ConditionalCheckFailedException anExceptionObject) {
            return false;  // indicate the delete failed
        }
        return true;       // indicate the delete was successful
    }
}
