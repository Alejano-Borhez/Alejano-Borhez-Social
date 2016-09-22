package com.epam.brest.course2015.social.dao;

import com.epam.brest.course2015.social.core.Friendship;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

/**
 * Created by alexander on 26.10.15.
 */
public interface FriendshipDao {
    @Caching(evict = {
        @CacheEvict(cacheNames = {
            "friends",
            "noFriends",
            "userFriendsCount"
        }, key = "#id1"),
        @CacheEvict(cacheNames = {
            "friends",
            "noFriends",
            "userFriendsCount"
        }, key = "#id2"),
        @CacheEvict(value = "isAFriend", key = "#id1.toString() + '_' + #id2.toString()"),
        @CacheEvict(value = "isAFriend", key = "#id2.toString() + '_' + #id1.toString()")
    })
    void addFriendship(Integer id1, Integer id2);

    @Caching(cacheable = {
        @Cacheable(value = "isAFriend", key = "#id1.toString() + '_' + #id2.toString()"),
        @Cacheable(value = "isAFriend", key = "#id2.toString() + '_' + #id1.toString()")
    })
    boolean isAFriend(Integer id1, Integer id2);

    @Caching(evict = {
        @CacheEvict(cacheNames = {
            "friends",
            "noFriends",
            "userFriendsCount"
        }, key = "#id1"),
        @CacheEvict(cacheNames = {
            "friends",
            "noFriends",
            "userFriendsCount"
        }, key = "#id2"),
        @CacheEvict(value = "isAFriend", key = "#id1.toString() + '_' + #id2.toString()"),
        @CacheEvict(value = "isAFriend", key = "#id2.toString() + '_' + #id1.toString()")
    })
    void discardFriendship(Integer id1, Integer id2);

    List<Friendship> getAllFriendships();
    void discardAllFriendshipsOfAUser(Integer userId);
}
