package com.epam.brest.course2015.social.service;

import com.epam.brest.course2015.social.core.Friendship;
import com.epam.brest.course2015.social.core.Image;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dao.FriendshipDao;
import com.epam.brest.course2015.social.dao.ImageDao;
import com.epam.brest.course2015.social.dao.UserDao;
import com.epam.brest.course2015.social.dto.SocialDto;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by alexander on 6.11.15.
 */
@Service
@Transactional
public class SocialServiceImpl implements SocialService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FriendshipDao friendshipDao;
    @Autowired
    private ImageDao imageDao;

    public SocialServiceImpl (UserDao userDao, FriendshipDao friendshipDao, ImageDao imageDao) {
        this.userDao = userDao;
        this.friendshipDao = friendshipDao;
        this.imageDao = imageDao;
    }

    public SocialServiceImpl () {};
    
    @Value("${addUser.notNullUser}")
    private String notNullUser;
    @Value("${addUser.nullId}")
    private String nullId;
    @Value("${addUser.notNullLogin}")
    private String notNullLogin;
    @Value("${addUser.notNullPassword}")
    private String notNullPassword;
    @Value("${addUser.notNullFirstName}")
    private String notNullFirstName;
    @Value("${addUser.notNullLastName}")
    private String notNullLastName;
    @Value("${deleteUser.notNullId}")
    private String notNullId;
    @Value("${deleteUser.correctId}")
    private String correctId;
    @Value("${addUser.notNullAge}")
    private String notNullAge;
    @Value("${addUser.positiveAge}")
    private String positiveAge;
    @Value("${addUser.notNullDate}")
    private String notNullDate;
    @Value("${addUser.dateCompare}")
    private String dateCompare;

    @Override
    @Logged
    public Integer addUser(User user) {
        Assert.notNull(user, notNullUser);
        Assert.isNull(user.getUserId(), nullId);
        Assert.notNull(user.getLogin(), notNullLogin);
        Assert.hasText(user.getLogin(), notNullLogin);
        Assert.notNull(user.getPassword(), notNullPassword);
        Assert.hasText(user.getPassword(), notNullPassword);
        Assert.notNull(user.getFirstName(), notNullFirstName);
        Assert.hasText(user.getFirstName(), notNullFirstName);
        Assert.notNull(user.getLastName(), notNullLastName);
        Assert.hasText(user.getLastName(), notNullLastName);
        Assert.notNull(user.getAge(), notNullAge);
        Assert.isTrue(user.getAge() > 0, positiveAge);
        if (userDao.getUserByLogin(user.getLogin()) == null) {
            return userDao.addUser(user);
        } else {
            return null;
        }
    }

    @Override
    @Logged
    public void deleteUser(Integer id) {
        Assert.notNull(id, notNullId);
        Assert.isTrue(id > 0, correctId);
        try {
            userDao.deleteUser(id);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteImage(Integer userId, Integer imageId) {
        Assert.notNull(userId, notNullId);
        Assert.isTrue(userId > 0, correctId);
        Assert.notNull(imageId, notNullId);
        Assert.isTrue(imageId > 0, correctId);
        try {
            Image image = imageDao.getImage(imageId);
            userDao.deleteImage(userId, image);
            imageDao.deleteImage(imageId);

        } catch (NullPointerException | EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Logged
    public void changePassword(Integer id, String password) {
        Assert.notNull(id, notNullId);
        Assert.isTrue(id > 0, correctId);
        Assert.notNull(password, notNullPassword);
        try {
            userDao.getUserById(id);
            userDao.changePassword(id, password);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Logged
    public void changeLogin(Integer id, String login) {
        Assert.notNull(id, notNullId);
        Assert.isTrue(id > 0, correctId);
        Assert.notNull(login, notNullLogin);
        try {
            userDao.getUserById(id);
            userDao.changeLogin(id, login);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Logged
    public void changeFirstName(Integer id, String firstName) {
        Assert.notNull(id, notNullId);
        Assert.isTrue(id > 0, correctId);
        Assert.notNull(firstName, notNullFirstName);
        try {
            userDao.getUserById(id);
            userDao.changeFirstName(id, firstName);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Logged
    public void changeLastName(Integer id, String lastName) {
        Assert.notNull(id, notNullId);
        Assert.isTrue(id > 0, correctId);
        Assert.notNull(lastName, notNullLastName);
        try {
            userDao.getUserById(id);
            userDao.changeLastName(id, lastName);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void renameImage(Integer id, String name) {
        Assert.notNull(id, notNullId);
        Assert.notNull(name, notNullLogin);
        Assert.isTrue(id > 0, correctId);

        try {
            imageDao.renameImage(id, name);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

    }

    @Override
    @Logged
    public User getUserById(Integer id) {
        Assert.notNull(id, notNullId);
        Assert.isTrue(id > 0, correctId);
        try {
            return userDao.getUserById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Logged
    public User getUserByLogin(String login) {
        Assert.notNull(login, notNullLogin);
        try {
            return userDao.getUserByLogin(login);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Assert.notNull(email, notNullUser);
        try {
            return userDao.getUserByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Logged
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Logged
    public List<User> getAllUsersByDate(Date dateMin, Date dateMax) {
        Assert.notNull(dateMin, notNullDate);
        Assert.notNull(dateMax, notNullDate);
        Assert.isTrue(dateMax.getTime() >= dateMin.getTime(), dateCompare);

        return userDao.getAllUsers(dateMin, dateMax);
    }

    @Override
    @Logged
    public List<User> getFriends(Integer id) {
        Assert.notNull(id, notNullId);
        Assert.isTrue(id > 0, correctId);
        try {
            userDao.getUserById(id);
            return userDao.getFriends(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return Collections.<User>emptyList();
        }
    }

    @Override
    @Logged
    public List<User> getNoFriends(Integer id) {
        Assert.notNull(id, notNullId);
        Assert.isTrue(id > 0, correctId);
        try {
            userDao.getUserById(id);
            return userDao.getNoFriends(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return Collections.<User>emptyList();
        }
    }

    @Override
    @Logged
    public void addFriendship(Integer id1, Integer id2) {
        Assert.notNull(id1, notNullId + " user1");
        Assert.notNull(id2, notNullId + " user2");
        Assert.isTrue(!friendshipDao.isAFriend(id1, id2),
                "Friendship already exists");
        friendshipDao.addFriendship(id1, id2);
    }

    @Override
    @Logged
    public boolean isAFriend(Integer id1, Integer id2) {
        Assert.notNull(id1, notNullId + " user1");
        Assert.notNull(id2, notNullId + " user2");
        return friendshipDao.isAFriend(id1, id2);
    }

    @Override
    @Logged
    public void discardFriendship(Integer id1, Integer id2) {
        Assert.notNull(id1, notNullId + " user1");
        Assert.notNull(id2, notNullId + " user2");
        Assert.isTrue(friendshipDao.isAFriend(id1, id2),
                "Friendship does not exist");
        friendshipDao.discardFriendship(id1, id2);
    }

    @Override
    @Logged
    public List<Friendship> getAllFriendships() {
        return friendshipDao.getAllFriendships();
    }

    @Override
    @Logged
    public void discardAllFriendshipsOfAUser(Integer id) {
        friendshipDao.discardAllFriendshipsOfAUser(id);
    }

    @Override
    @Logged
    public SocialDto getSocialUsersDto() {
        SocialDto dto = new SocialDto();
        dto.setTotalUsers(userDao.getCountOfUsers());
        if (dto.getTotalUsers() > 0) {
            dto.setUsers(userDao.getAllUsers());
            for (User user: dto.getUsers()) {
                user.setTotalFriends(userDao.getCountOfUserFriends(
                                                user.getUserId()));
            }
        } else  {
            dto.setUsers(Collections.<User>emptyList());
        }
        return dto;
    }

    @Override
    @Logged
    public SocialDto getSocialFriendsDto(Integer id) {
        SocialDto dto = new SocialDto();
        dto.setTotalUsers(userDao.getCountOfUserFriends(id));
        if (dto.getTotalUsers() > 0) {
            dto.setUsers(userDao.getFriends(id));
            for (User user: dto.getUsers()) {
                user.setTotalFriends(userDao.getCountOfUserFriends(
                                                user.getUserId()));
            }
        } else {
            dto.setUsers(Collections.<User>emptyList());
        }
        dto.setUser(userDao.getUserById(id));
        return dto;
    }

    @Override
    @Logged
    public SocialDto getSocialNoFriendsDto(Integer id) {
        SocialDto dto = new SocialDto();
        dto.setTotalUsers(getNoFriends(id).size());
        if (dto.getTotalUsers() > 0) {
            dto.setUsers(getNoFriends(id));
            for (User user: dto.getUsers()) {
                user.setTotalFriends(userDao.getCountOfUserFriends(
                                                user.getUserId()));
            }
        } else {
            dto.setUsers(Collections.<User>emptyList());
        }
        dto.setUser(userDao.getUserById(id));
        return dto;
    }


    @Override
    @Logged
    public SocialDto getSocialUsersDtoByDate(Date dateMin, Date dateMax) {
        SocialDto dto = new SocialDto();
        dto.setTotalUsers(getAllUsersByDate(dateMin, dateMax).size());
        if (dto.getTotalUsers() > 0) {
            dto.setUsers(getAllUsersByDate(dateMin, dateMax));
            for (User user: dto.getUsers()) {
                user.setTotalFriends(userDao.getCountOfUserFriends(
                        user.getUserId()));
            }
        } else {
            dto.setUsers(Collections.<User>emptyList());
        }
        return dto;
    }

    @Override
    @Logged
    public Integer addImage (Integer userId, String url, String url50, String url81) {
        Assert.notNull(userId, notNullId);
        Assert.notNull(url, "You must provide a valid URL");
        Image image = new Image();
        image.setCreatedDate(new Date());
        image.setUrl(url);
        image.setUrl50(url50);
        image.setUrl81(url81);
        Integer imageId = imageDao.addImage(image);
        Image addedImage = imageDao.getImage(imageId);
        userDao.addImage(userId, addedImage);
        return imageId;
    }

    @Override
    public List<Image> getAllImagesOfAUser(Integer id) {
        Assert.notNull(id, notNullId);
        return imageDao.getAllImagesOfAUser(id);
    }

}
