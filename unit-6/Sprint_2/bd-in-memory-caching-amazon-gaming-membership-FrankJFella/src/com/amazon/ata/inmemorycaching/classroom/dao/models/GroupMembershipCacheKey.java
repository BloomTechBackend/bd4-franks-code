package com.amazon.ata.inmemorycaching.classroom.dao.models;

import java.util.Objects;
// Represents the Cache Key being sent to the Cache Manager
//      contains the values to be used to search the  cache

// Because the cache key contains multiple values we use a class to hold the values

// This is an immutable class - it's thread safe and may be used by multiple threads

public final class GroupMembershipCacheKey {

    private final String userId;   // userid of the user
    private final String groupId;  // group to check for membership

    // Adding final to a parameter makes unchangeable/immutable in the method
    // Even though a String is a reference type
    // No need to perform defensive copy - assign a copy not the reference
    // String does a defensive copy by definition
    public GroupMembershipCacheKey(final String userId, final String groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    // Even though a String is a reference type
    // No need to perform defensive return - return a copy not the reference
    // String does a defensive return by definition
    public String getUserId() {
        return userId;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        GroupMembershipCacheKey request = (GroupMembershipCacheKey) obj;

        return userId.equals(request.userId) && groupId.equals(request.groupId);
    }
}
