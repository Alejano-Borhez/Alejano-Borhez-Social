package com.epam.brest.course2015.social.jpa;

import com.epam.brest.course2015.social.core.Friendship;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dao.FriendshipDao;
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

    @Override
    public void addFriendship(Integer id1, Integer id2) {
            User user1 = entityManager.find(User.class, id1);
            User user2 = entityManager.find(User.class, id2);

            user1.getFriends().add(user2);
            user2.getFriends().add(user1);
    }

    @Override
    public boolean isAFriend(Integer id1, Integer id2) {
        User user11 = entityManager.find(User.class, id1);
        User user21 = entityManager.find(User.class, id2);
        return user11.getFriends().contains(user21) && user21.getFriends().contains(user11);
    }

    @Override
    public void discardFriendship(Integer id1, Integer id2) {
        User user1 = entityManager.find(User.class, id1);
        User user2 = entityManager.find(User.class, id2);

        user1.getFriends().remove(user2);
        user2.getFriends().remove(user1);

    }

    @Override
    public List<Friendship> getAllFriendships() {
        return null;
    }

    @Override
    public void discardAllFriendshipsOfAUser(Integer userId) {

    }
}
