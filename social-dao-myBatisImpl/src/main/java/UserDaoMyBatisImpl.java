import com.epam.brest.course2015.social.core.Image;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dao.UserDao;

import java.util.Date;
import java.util.List;

/**
 * Created by alexander_borohov on 12.9.16.
 */
public class UserDaoMyBatisImpl implements UserDao {

    @Override
    public Integer addUser(User user) {
        return null;
    }

    @Override
    public void changePassword(Integer id, String password) {

    }

    @Override
    public void changeLogin(Integer id, String login) {

    }

    @Override
    public void changeFirstName(Integer id, String firstName) {

    }

    @Override
    public void changeLastName(Integer id, String lastName) {

    }

    @Override
    public void addImage(Integer userId, Image image) {

    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public void deleteImage(Integer userId, Image image) {

    }

    @Override
    public List<User> getFriends(Integer id) {
        return null;
    }

    @Override
    public List<User> getNoFriends(Integer id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<User> getAllUsers(Date dateMin, Date dateMax) {
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public Integer getCountOfUsers() {
        return null;
    }

    @Override
    public Integer getCountOfUserFriends(Integer id) {
        return null;
    }
}
