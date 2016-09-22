package com.epam.brest.course2015.social.jpa;

import com.epam.brest.course2015.social.core.Image;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dao.UserDao;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by alexander on 25.12.15.
 */
@Repository
public class UserDaoJPA implements UserDao {
    @Value("${user.selectAllUsers}")
    private String selectAllUsers;
    @Value("${user.selectAllUsersByDate}")
    private String SelectAllUsersByDate;
    @Value("${user.selectByLogin}")
    private String selectUserByLogin;
    @Value("${user.selectByEmail}")
    private String selectUserByEmail;
    @Value("${user.getCountOfUsers}")
    private String getCountOfUsers;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Logged
    public Integer addUser(User user) {
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        entityManager.persist(user);
        return user.getUserId();
    }

    @Override
    @Logged
    public void changePassword(Integer id, String password) {
        User user = getUserById(id);
        user.setPassword(password);
        user.setUpdatedDate(new Date());
    }

    @Override
    @Logged
    public void changeLogin(Integer id, String login) {
        User user = getUserById(id);
        user.setLogin(login);
        user.setUpdatedDate(new Date());
    }

    @Override
    @Logged
    public void changeFirstName(Integer id, String firstName) {
        User user = getUserById(id);
        user.setFirstName(firstName);
        user.setUpdatedDate(new Date());
    }

    @Override
    @Logged
    public void changeLastName(Integer id, String lastName) {
        User user = getUserById(id);
        user.setLastName(lastName);
        user.setUpdatedDate(new Date());
    }

    @Override
    @Logged
    public void addImage(Integer id, Image image) {
        User user = getUserById(id);
        user.getImages().add(image);
        user.setUpdatedDate(new Date());
    }

    @Override
    @Logged
    public void deleteImage(Integer id, Image image) {
        User user = getUserById(id);
        user.getImages().remove(image);
        user.setUpdatedDate(new Date());
    }

    @Override
    @Logged
    public void deleteUser(Integer id) {
            entityManager.remove(getUserById(id));
    }

    @Override
    @Logged
    public List<User> getFriends(Integer id) {
        return getUserById(id).getFriends();
    }

    @Override
    @Logged
    public List<User> getNoFriends(Integer id) {
        List<User> list = getUserById(id).getFriends();
        List<User> list1 = entityManager
                .createQuery(selectAllUsers, User.class)
                .getResultList();
        list1.removeAll(list);
        list1.remove(getUserById(id));
        return list1;
    }

    @Override
    @Logged
    public List<User> getAllUsers() {
        return entityManager
                .createQuery(selectAllUsers, User.class)
                .getResultList();
    }

    @Override
    @Logged
    public List<User> getAllUsers(Date dateMin, Date dateMax) {
        return   entityManager
                .createQuery(SelectAllUsersByDate, User.class)
                .setParameter("dateMin", dateMin)
                .setParameter("dateMax", dateMax)
                .getResultList();
    }

    @Override
    @Logged
    public User getUserById(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Logged
    public User getUserByLogin(String login) throws EmptyResultDataAccessException {
        try {
            return entityManager
                    .createQuery(selectUserByLogin, User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Logged
    public User getUserByEmail(String email) {
        try {
            return entityManager
                    .createQuery(selectUserByEmail, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Logged
    public Integer getCountOfUsers() {
        Long count = (Long) entityManager
                .createQuery(getCountOfUsers)
                .getSingleResult();
        return count.intValue();
    }

    @Override
    @Logged
    public Integer getCountOfUserFriends(Integer id) {
        try {
            return getUserById(id).getFriends().size();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
