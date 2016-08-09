package com.epam.brest.course2015.social.jpa;

import com.epam.brest.course2015.social.core.Friendship;
import com.epam.brest.course2015.social.dao.FriendshipDao;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by alexander on 26.10.15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-persistence-HSQL.xml"})
@Transactional
public class FriendshipDaoJPATest extends TestCase {
    private static final Integer TEST_FRIEND_1 = 1;
    private static final Integer TEST_FRIEND_2 = 2;
    private static final Integer TEST_FRIEND_3 = 3;
    private static final Integer TEST_FRIEND_4 = 4;

    @Autowired
    private FriendshipDao friendshipDao;

    @Test
    public void testAddFriendship() throws Exception {

        assertFalse(friendshipDao.isAFriend(TEST_FRIEND_3, TEST_FRIEND_4));
        assertFalse(friendshipDao.isAFriend(TEST_FRIEND_4, TEST_FRIEND_3));
        friendshipDao.addFriendship(TEST_FRIEND_3, TEST_FRIEND_4);
        assertTrue(friendshipDao.isAFriend(TEST_FRIEND_3, TEST_FRIEND_4));
        assertTrue(friendshipDao.isAFriend(TEST_FRIEND_4, TEST_FRIEND_3));
        friendshipDao.addFriendship(TEST_FRIEND_3, TEST_FRIEND_4);

    }

    @Test
    public void testIsAFriendFalse() throws Exception {
        assertFalse(friendshipDao.isAFriend(TEST_FRIEND_3, TEST_FRIEND_4));
        assertFalse(friendshipDao.isAFriend(TEST_FRIEND_4, TEST_FRIEND_3));

    }

    @Test
    public void testIsAFriendTrue() throws Exception {
        assertTrue(friendshipDao.isAFriend(TEST_FRIEND_1, TEST_FRIEND_2));
        assertTrue(friendshipDao.isAFriend(TEST_FRIEND_2, TEST_FRIEND_1));
    }

    @Test
    public void testDiscardFriendship() throws Exception {
        assertTrue(friendshipDao.isAFriend(TEST_FRIEND_1, TEST_FRIEND_2));
        assertTrue(friendshipDao.isAFriend(TEST_FRIEND_2, TEST_FRIEND_1));
        friendshipDao.discardFriendship(TEST_FRIEND_1, TEST_FRIEND_2);
        assertFalse(friendshipDao.isAFriend(TEST_FRIEND_1, TEST_FRIEND_2));
        assertFalse(friendshipDao.isAFriend(TEST_FRIEND_2, TEST_FRIEND_1));
    }

//    @Test
    public void testGetAllFriendships () throws Exception {
        List<Friendship> friendshipsList = friendshipDao.getAllFriendships();
        assertNotNull(friendshipsList);
        assertTrue(friendshipsList.size() > 0);
        assertEquals(friendshipsList.get(0).getClass(), Friendship.class);
        assertEquals(friendshipsList.get(0).getFriend1Id().getClass(), Integer.class);
        assertEquals(friendshipsList.get(0).getFriend2Id().getClass(), Integer.class);
    }

//    @Test
    public void testDiscardAllFriendshipsOfAUser() throws Exception {
        assertTrue(friendshipDao.isAFriend(TEST_FRIEND_1, TEST_FRIEND_2));
        assertTrue(friendshipDao.isAFriend(TEST_FRIEND_1, TEST_FRIEND_3));
        assertTrue(friendshipDao.isAFriend(TEST_FRIEND_1, TEST_FRIEND_4));
        friendshipDao.discardAllFriendshipsOfAUser(1);
        assertFalse(friendshipDao.isAFriend(TEST_FRIEND_1, TEST_FRIEND_2));
        assertFalse(friendshipDao.isAFriend(TEST_FRIEND_1, TEST_FRIEND_3));
        assertFalse(friendshipDao.isAFriend(TEST_FRIEND_1, TEST_FRIEND_4));
    }
}