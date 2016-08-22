package com.epam.brest.course2015.social.core;

import com.epam.brest.course2015.social.test.Logged;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by alexander on 25.10.15.
 *
 * @author alexander.borohov
 * @version 1.0
 *
 * {@link User} class is one of entities of my Simple-Social-Network
 * study project.
 *
 *  Class is ready to use as Entity in Dao-JPA Implementation to persist data in database
 *
 */
@Component
@Entity
@Table(name = "user")
public class User {
//  Class variables declaration
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String login;
    private String password;
    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @OneToMany(
              fetch = FetchType.EAGER,
              cascade = CascadeType.REMOVE,
              orphanRemoval = true
    )
    private List<Image> images;

    @OneToMany
    @JoinTable(
        name = "friends",
        joinColumns = @JoinColumn(name = "friend1Id")
      , inverseJoinColumns = @JoinColumn(name = "friend2Id")
    )
    @JsonIgnore
    private List<User> friends;

    @Transient
    private Integer totalFriends;

    //  Dates are formatted to be transferred via JSON
    @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

//  Getters and setters for class variables
    @Logged
    public String getFirstName() {
        return firstName;
    }
    @Logged
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Logged
    public String getLastName() {
        return lastName;
    }
    @Logged
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Logged
    public Integer getAge() {
        return age;
    }
    @Logged
    public void setAge(Integer age) {
        this.age = age;
    }
    @Logged
    public String getLogin() {
        return login;
    }
    @Logged
    public void setLogin(String login) {
        this.login = login;
    }
    @Logged
    public String getPassword() {
        return password;
    }
    @Logged
    public void setPassword(String password) {
        this.password = password;
    }
    @Logged
    public Integer getUserId() {
        return userId;
    }
    @Logged
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Logged
    public Integer getTotalFriends() {
        return totalFriends;
    }
    @Logged
    public void setTotalFriends(Integer totalFriends) {
        this.totalFriends = totalFriends;
    }
    @Logged
    public Date getCreatedDate() {
        return createdDate;
    }
    @Logged
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    @Logged
    public Date getUpdatedDate() {
        return updatedDate;
    }
    @Logged
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Logged
    public List<Image> getImages() {
        return images;
    }
    @Logged
    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Logged
    public List<User> getFriends() {
        return friends;
    }

    @Logged
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Logged
    public String getEmail() {
        return email;
    }

    @Logged
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Basic constructor for {@link User} object.
     * It is usually used for basic operations within Dao processes.
     * <p>
     * Some parameters like createdDate & updatedDate are evaluated by default.
     *
     * @param  login    may contain only lowercase english letters and
     *                  _- signs and numbers.
     * @param password  may contain only english letters and numbers.
     * @param firstName must have a first capital letter.
     *                  Spaces are not allowed.
     * @param lastName  must have a first capital letter.
     *                  Spaces are not allowed.
     * @param age       a positive Integer.
     */
    public User(String login,
                String password,
                String firstName,
                String lastName,
                Integer age,
                String email) {
        this.password = password;

        this.login = login;
        this.age = age;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.createdDate = new Date();
        this.updatedDate = new Date();
        this.totalFriends = 0;
        this.images = new ArrayList<>();
    }

    /**
     * Empty constructor for {@link User} object.
     * Used for JPA purposes
     */
    public User() { }

    /**
     * Simple constructor for {@link User} object.
     * Usually used for test purposes.
     * @param userId should be a positive Integer.
     */
    public User(Integer userId) {
        this.userId = userId;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getAge(), user.getAge()) &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getAge(), getLogin(), getPassword(), getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
