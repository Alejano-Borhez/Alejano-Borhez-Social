package com.epam.brest.course2015.social.dao;

import com.epam.brest.course2015.social.core.Friendship;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

/**
 * Created by alexander on 26.10.15.
 */
public class FriendshipDaoImpl implements FriendshipDao {
    @Value("${friend.addFriendship}")
    private String addFriendship;
    @Value("${friend.findFriendship}")
    private String findFriendship;
    @Value("${friend.discardFriendship}")
    private String deleteFriendship;
    @Value("${friend.selectAllFriendship}")
    private String getAllFriendships;
    @Value("${friend.deleteAllFriendships}")
    private String deleteAllFriendshipsOfAUser;

    private RowMapper<Friendship> friendshipRowMapper =
            new BeanPropertyRowMapper<>(Friendship.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Logged
    public void addFriendship(User friend1, User friend2) {
        Friendship friendship12 = new Friendship(friend1.getUserId(),
                                                 friend2.getUserId());
        BeanPropertySqlParameterSource parameterSource12 =
                new BeanPropertySqlParameterSource(friendship12);
        namedParameterJdbcTemplate.update(addFriendship, parameterSource12);
        Friendship friendship21 = new Friendship(friend2.getUserId(),
                                                 friend1.getUserId());
        BeanPropertySqlParameterSource parameterSource21 =
                new BeanPropertySqlParameterSource(friendship21);
        namedParameterJdbcTemplate.update(addFriendship, parameterSource21);
    }

    @Override
    @Logged
    public boolean isAFriend(User user1, User user2) {
        Friendship friendship = new Friendship (user1.getUserId(),
                                                user2.getUserId());
        BeanPropertySqlParameterSource source =
                new BeanPropertySqlParameterSource(friendship);
        try {
            Friendship testFriendship = namedParameterJdbcTemplate
                    .queryForObject(findFriendship,
                                    source,
                                    friendshipRowMapper);
            if (testFriendship.getClass().equals(Friendship.class)) {
                return true;
            }
            return false;
        } catch (EmptyResultDataAccessException ex) {

            return false;
        }
    }

    @Override
    @Logged
    public void discardFriendship(User enemy1, User enemy2) {
        Friendship friendship12 = new Friendship(enemy1.getUserId(),
                                                enemy2.getUserId());
        Friendship friendship21 = new Friendship(enemy2.getUserId(),
                                                enemy1.getUserId());
        BeanPropertySqlParameterSource source12 =
                new BeanPropertySqlParameterSource(friendship12);
        BeanPropertySqlParameterSource source21 =
                new BeanPropertySqlParameterSource(friendship21);
        namedParameterJdbcTemplate.update(deleteFriendship, source12);
        namedParameterJdbcTemplate.update(deleteFriendship, source21);
    }

    @Override
    @Logged
    public List<Friendship> getAllFriendships () {
        return namedParameterJdbcTemplate.query(getAllFriendships,
                                                friendshipRowMapper);
    }

    @Override
    @Logged
    public void discardAllFriendshipsOfAUser(Integer userId) {
        SqlParameterSource source = new MapSqlParameterSource("id", userId);
        namedParameterJdbcTemplate.update(deleteAllFriendshipsOfAUser,
                                          source);
    }
}
