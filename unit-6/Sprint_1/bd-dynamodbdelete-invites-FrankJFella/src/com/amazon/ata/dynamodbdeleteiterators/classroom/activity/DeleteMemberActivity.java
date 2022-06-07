package com.amazon.ata.dynamodbdeleteiterators.classroom.activity;

import com.amazon.ata.dynamodbdeleteiterators.classroom.dao.InviteDao;
import com.amazon.ata.dynamodbdeleteiterators.classroom.dao.MemberDao;
import com.amazon.ata.dynamodbdeleteiterators.classroom.dao.models.Invite;

import javax.inject.Inject;
import java.util.List;

/**
 * Handles a request to delete a member. Actually deletes the member
 * from datastore for privacy reasons.
 */
public class DeleteMemberActivity {
    private MemberDao memberDao;
    private InviteDao inviteDao;

    /**
     * Constructs the activity handler.
     * @param memberDao The MemberDao to use for member item operations
     */
    @Inject
    public DeleteMemberActivity(MemberDao memberDao, InviteDao inviteDao) {
        this.memberDao = memberDao;
        this.inviteDao = inviteDao;
    }

    /**
     * Handles a request to delete a member permanently, by member ID.
     *
     * NOTE: A little deviation from usual.
     * Here we're using values directly in our arguments and return value,
     * whereas in a typical Coral service we'd have Request/Result objects
     * that would be generated from configuration via Coral. We haven't
     * created service infrastructure for this activity, so we're just
     * using the values directly.
     *
     * @param memberId The ID of the member to delete
     */
    public void handleRequest(final String memberId) {
        memberDao.deletePermanently(memberId);  // Call the MemberDao to delete member

        // Get all the Invites sent to the member
        List<Invite> memberInvites = inviteDao.getInvitesSentToMember(memberId);

        // Go through the list of invites sent to the member and delete them
        for( Invite anInvite : memberInvites) {
            inviteDao.deleteInvite(anInvite.getEventId(), anInvite.getMemberId());
        }
    }
}
