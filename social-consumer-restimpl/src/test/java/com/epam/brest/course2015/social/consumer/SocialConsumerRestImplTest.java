package com.epam.brest.course2015.social.consumer;

import com.epam.brest.course2015.social.core.Image;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dto.SocialDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Created by alexander_borohov on 1.8.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SocialConsumerTestContext.class})
public class SocialConsumerRestImplTest {
    @Value("${test.userId1}")
    private Integer testId;
    @Value("${test.token}")
    private String testToken;
    @Value("${test.firstName}")
    private String testName;
    @Value("${test.firstName}")
    private String firstName;
    @Value("${test.lastName}")
    private String lastName;
    @Value("${test.age}")
    private Integer age;
    @Value("${test.password}")
    private String password;
    @Value("${test.email}")
    private String email;
    @Value("${test.userId1}")
    private Integer userId;
    @Value("${test.login}")
    private String login;
    @Value("${test.role1}")
    private String role;

    private static final String TEST_DATE_MIN = "2015-10-01";
    private static final String TEST_DATE_MAX = "2015-10-02";

    @Autowired
    private SocialConsumer socialConsumer;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Image image;
    @Value("${rest.prefix}")
    private String restPrefix;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        verify(restTemplate);
        reset(restTemplate);
    }

    @Test
    public void addImage() throws Exception {
        expect(restTemplate.postForObject(
                restPrefix
                + "image/upload"
                + "?url="
                + image.getUrl()
                + "&url50="
                + image.getUrl50()
                + "&url81="
                + image.getUrl81(),
                testToken, Integer.class))
                .andReturn(testId);
        replay(restTemplate);
        Integer userId = socialConsumer.addImage(testToken, image.getUrl(), image.getUrl50(), image.getUrl81());
        assertTrue(userId == testId);
    }

    @Test
    public void deleteUser() throws Exception {
        restTemplate.delete(restPrefix + "user?id=" + testId);
        expectLastCall();
        replay(restTemplate);
        socialConsumer.deleteUser(testId);
    }

    @Test
    public void deleteImage() throws Exception {
        expect(restTemplate.postForLocation(restPrefix + "image/delete?imageId=" + testId, testToken))
                .andReturn(new URI(restPrefix));
        replay(restTemplate);
        socialConsumer.deleteImage(testToken, testId);
    }

    @Test
    public void renameImage() throws Exception {
        expect(restTemplate.postForLocation(restPrefix + "image/rename?name=" + testName, testToken))
                .andReturn(new URI(restPrefix));
        replay(restTemplate);
        socialConsumer.renameImage(testToken, testName);
    }

    @Test
    public void deleteFriend() throws Exception {
        expect(restTemplate.postForLocation(restPrefix + "friendship/delete?id2=" + testId, testToken))
                .andReturn(new URI(restPrefix));
        replay(restTemplate);
        socialConsumer.deleteFriend(testToken, testId);
    }

    @Test
    public void addFriendship() throws Exception {
        expect(restTemplate.postForLocation(restPrefix + "friendship/add?id2=" + testId, testToken))
                .andReturn(new URI(restPrefix));
        replay(restTemplate);
        socialConsumer.addFriendship(testToken, testId);
    }

    @Test
    public void getAllUsersByDate() throws Exception {
        String url = restPrefix + "userdtobydate?datemin=" + TEST_DATE_MIN + "&datemax=" + TEST_DATE_MAX;
        expect(restTemplate.postForObject(url, testToken, SocialDto.class))
                .andReturn(new SocialDto());
        replay(restTemplate);
        Object test = socialConsumer.getAllUsersByDate(testToken, TEST_DATE_MIN, TEST_DATE_MAX);
        assertEquals(test.getClass(), SocialDto.class);
    }

    @Test
    public void addUserSubmit() throws Exception {
        User user = new User();
        expect(restTemplate.postForObject(restPrefix + "user", user, Boolean.class))
                .andReturn(true);
        replay(restTemplate);
        Boolean test = socialConsumer.addUserSubmit(user);
        assertTrue(test);
    }

    @Test
    public void changePassword() throws Exception {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .pathSegment("user")
                .pathSegment("{action}")
                .queryParam("param", password)
                .buildAndExpand("password");
        expect(restTemplate.postForObject(uriComponents.toUriString(), testToken, Object.class)).andReturn(new Object());
        replay(restTemplate);

        socialConsumer.changeUser(testToken, "password", password);
    }

    @Test
    public void changeLastName() throws Exception {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .pathSegment("user")
                .pathSegment("{action}")
                .queryParam("param", lastName)
                .buildAndExpand("lastname");
        expect(restTemplate.postForObject(uriComponents.toUriString(), testToken, Object.class)).andReturn(new Object());
        replay(restTemplate);

        socialConsumer.changeUser(testToken, "lastname", lastName);
    }

    @Test
    public void getAllNoFriendsOfAUser() throws Exception {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .path("/nofriendsdto")
                .build();

        String url = uriComponents.toUriString();

        expect(restTemplate.postForObject(url, testToken, SocialDto.class)).andReturn(new SocialDto());
        replay(restTemplate);

        socialConsumer.getAllNoFriendsOfAUser(testToken);
    }

    @Test
    public void isUserInDB() throws Exception {
        expect(restTemplate.postForObject(restPrefix + "user/db", new User(testId), Boolean.class))
                .andReturn(true);
        replay(restTemplate);
        assertTrue(socialConsumer.isUserInDB(new User(testId)));
    }

    @Test
    public void isTokenValid() throws Exception {
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .pathSegment("token")
                .pathSegment("validate")
                .build();

        String url = uri.toUriString();

        expect(restTemplate.postForObject(url, testToken, Boolean.class)).andReturn(true);
        replay(restTemplate);

        socialConsumer.isTokenValid(testToken);
    }

    @Test
    public void getToken() throws Exception {
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .path("token")
                .queryParam("login", login)
                .build();

        String url = uri.toUriString();

        expect(restTemplate.getForObject(url, String.class)).andReturn(testToken);
        replay(restTemplate);


        assertTrue(socialConsumer.getToken(login, role).equals(testToken));
    }

    @Test
    public void getUserDto() throws Exception {
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .path("friendsdto")
                .build();

        String url = uri.toUriString();
        SocialDto dto = new SocialDto();

        expect(restTemplate.postForObject(url, testToken, SocialDto.class)).andReturn(dto);
        replay(restTemplate);

        SocialDto newDto = socialConsumer.getUserDto(testToken);

        assertEquals(newDto, dto);

    }

    @Test
    public void getAllFriends() throws Exception {
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .path("friendsdto")
                .build();

        String url = uri.toUriString();
        SocialDto dto = new SocialDto();

        expect(restTemplate.postForObject(url, testToken, SocialDto.class)).andReturn(dto);
        replay(restTemplate);

        SocialDto newDto = socialConsumer.getAllFriends(testToken);

        assertEquals(newDto, dto);
    }

    @Test
    public void getAllUsers() throws Exception {
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(restPrefix)
                .path("userdto")
                .build();

        String url = uri.toUriString();
        SocialDto dto = new SocialDto();

        expect(restTemplate.postForObject(url, testToken, SocialDto.class)).andReturn(dto);
        replay(restTemplate);

        SocialDto newDto = socialConsumer.getAllUsers(testToken);

        assertEquals(newDto, dto);
    }

    @Test
    public void emailApprove() throws Exception {
         UriComponents uri = UriComponentsBuilder
        .fromHttpUrl(restPrefix)
        .pathSegment("user")
        .pathSegment("byEmail")
        .queryParam("email", email)
        .build();

        expect(restTemplate.getForObject(uri.toUriString(), User.class)).andReturn(new User(testId));

        replay(restTemplate);

        User newUser = socialConsumer.emailApprove(email);
        assertEquals(newUser.getUserId(), testId);

    }
}