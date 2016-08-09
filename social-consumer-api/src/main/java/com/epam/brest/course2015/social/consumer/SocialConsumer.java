package com.epam.brest.course2015.social.consumer;

import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dto.SocialDto;

/**
 * Created by alexander on 19.1.16.
 */
public interface SocialConsumer {
    String getToken(String login, String role);
    String tempTokenApprove(String token);
    SocialDto getUserDto(String token);
    SocialDto getAllFriends(String token);
    SocialDto getAllUsers(String token);
    SocialDto getAllUsersByDate(String token, String dateMin, String dateMax);
    SocialDto getAllNoFriendsOfAUser(String token);
    User emailApprove(String email);
    void changeUser(String token, String action, String newValue);
    boolean addUserSubmit(User user);
    void addFriendship(String token, Integer id2);
    void deleteFriend(String token, Integer id2);
    Integer addImage(String token, String url, String url50, String url81);
    void deleteImage(String token, Integer imageId);
    void renameImage(String token, String name);
    boolean isUserInDB(User user);
    boolean isTokenValid(String token);
//    Not implemented
    void deleteUser(Integer id);

}