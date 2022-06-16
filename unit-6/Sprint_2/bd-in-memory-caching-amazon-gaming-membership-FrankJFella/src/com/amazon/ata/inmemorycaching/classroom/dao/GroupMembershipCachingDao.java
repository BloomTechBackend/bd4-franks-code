package com.amazon.ata.inmemorycaching.classroom.dao;

import com.amazon.ata.inmemorycaching.classroom.dao.models.GroupMembershipCacheKey;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

// This manage the calls to the data store for membership validation
// using the Google Guava cache manager

// Insert calls to Guave cache manger when the application wants to verify membership
// if data is not found in the cache,
//    Guava will go to the data store using the DelegateDao will then use the original Dao method
//
// We need to mimic the behavior of  the original Dao method so application doesn't have to change
//
// We need to have a method called isUserInGroup that receives a userid and groupid and returns a boolean

public class GroupMembershipCachingDao {

    // Define a reference to a LoadingCache object for use by Guava
    // The LoadingCache object has a key (cache key) and a value (what is returned for cache key)

    private LoadingCache<GroupMembershipCacheKey, Boolean> theCache;

    @Inject
    // We receive the delegateDao object when it's called
    public GroupMembershipCachingDao(final GroupMembershipDao delegateDao) {
        // Instantiate a LoadingCache object and assign it to the reference
        this.theCache = CacheBuilder.newBuilder()
                .maximumSize(20000)                  // max number of entries to keep in the cache
                .expireAfterWrite(3, TimeUnit.HOURS) // how long after an entry is writing to cache should it be evicted
                .build(CacheLoader.from(delegateDao::isUserInGroup));  // go build the cache with the delegateDao
        // the delegateDao must have a method with name given after the :: that receives a cache key object
    }

    // The method the application will call to interact with the data store
    // It should mimic the behavior of the original method (not a bad to just copy method signature)
    public boolean isUserInGroup(final String userId, final String groupId) {
        // search the cache to the data requested using the cache key with the values provided
        // use getUnchecked() instead of get() to avoid Exception processing
        return theCache.getUnchecked(new GroupMembershipCacheKey(userId, groupId));
    }

}
