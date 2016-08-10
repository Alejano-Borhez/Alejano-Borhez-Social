package com.epam.brest.course2015.social.jpa;

import com.epam.brest.course2015.social.core.Friendship;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dao.FriendshipDao;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by alexander on 13.1.16.
 */
@Repository
public class FriendshipDaoJPA implements FriendshipDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${friends.getAllFriendships}")
    private String getAllFriendship;
    @Value("${friends.deleteAllFriendships}")
    private String deleteAllFriendships;

    @Override
    @Logged
    @CacheEvict(cacheNames = {
            "friends",
            "noFriends",
            "isAFriend",
            "usersCount",
            "userFriendsCount"
            },
            allEntries = true)
    public void addFriendship(Integer id1, Integer id2) {
            User user1 = entityManager.find(User.class, id1);
            User user2 = entityManager.find(User.class, id2);

            user1.getFriends().add(user2);
            user2.getFriends().add(user1);
    }

    @Override
    @Logged
    @Cacheable("isAFriend")
    public boolean isAFriend(Integer id1, Integer id2) {
        User user11 = entityManager.find(User.class, id1);
        User user21 = entityManager.find(User.class, id2);
        return user11.getFriends().contains(user21) && user21.getFriends().contains(user11);
    }


    @Override
    @Logged
    @CacheEvict(cacheNames = {
            "friends",
            "noFriends",
            "isAFriend",
            "usersCount",
            "userFriendsCount"
    },
            allEntries = true)
    public void discardFriendship(Integer id1, Integer id2) {
        User user1 = entityManager.find(User.class, id1);
        User user2 = entityManager.find(User.class, id2);

        user1.getFriends().remove(user2);
        user2.getFriends().remove(user1);

    }

    @Override
    @Logged
    public List<Friendship> getAllFriendships() {
        return null;
    }

    @Override
    @Logged
    public void discardAllFriendshipsOfAUser(Integer userId) {

    }
}
