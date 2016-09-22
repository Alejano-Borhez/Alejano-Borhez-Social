package com.epam.brest.course2015.social.dao;

import com.epam.brest.course2015.social.core.Image;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.Date;
import java.util.List;

/**
 * Created by alexander on 25.10.15.
 */
public interface UserDao {
    @Caching(evict = {
        @CacheEvict(cacheNames = {"usersAll", "usersCount"}, allEntries = true),
        @CacheEvict(value = "noFriends", key = "#result")
    })
    Integer addUser(User user);

    @CacheEvict(cacheNames = {"userById"}, key = "#id")
    void changePassword(Integer id, String password);

    @CacheEvict(cacheNames = {"userById"}, key = "#id")
    void changeLogin(Integer id, String login);

    @CacheEvict(cacheNames = {"userById"}, key = "#id")
    void changeFirstName(Integer id, String firstName);

    @CacheEvict(cacheNames = {"userById"}, key = "#id")
    void changeLastName(Integer id, String lastName);

    @CacheEvict(cacheNames = {"imagesList"}, key = "#userId")
    void addImage(Integer userId, Image image);

    @CacheEvict(cacheNames = {"imagesList"}, key = "#userId")
    void deleteImage(Integer userId, Image image);

    @Caching(evict = {
            @CacheEvict(cacheNames = {
                    "friends",
                    "noFriends",
                    "userById",
                    "imagesList",
                    "tokenById"
            },
                    key = "#id",
                    beforeInvocation = true),
            @CacheEvict(cacheNames = {
                    "usersAll",
                    "token",
                    "userIdByToken",
                    "usersCount"
            }, allEntries = true,
                    beforeInvocation = true)
    })
    void deleteUser(Integer id);

    @Cacheable(value = "friends", key = "#id")
    List<User> getFriends(Integer id);

    @Cacheable(value = "noFriends", key = "#id")
    List<User> getNoFriends(Integer id);

    @Cacheable(value = "usersAll")
    List<User> getAllUsers();

    List<User> getAllUsers(Date dateMin, Date dateMax);

    @Caching(
        cacheable = {
            @Cacheable(value = "userById", unless = "#result != null", key = "#id")
        },
        evict = {
            @CacheEvict(value = "userById", condition = "#result == null", key = "#id")
        }
    )
    User getUserById(Integer id);

    User getUserByLogin(String login);

    User getUserByEmail(String email);

    @Cacheable(value = "usersCount")
    Integer getCountOfUsers();

    @Cacheable(value = "userFriendsCount", key = "#id")
    Integer getCountOfUserFriends(Integer id);

}
