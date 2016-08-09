package com.epam.brest.course2015.social.app;

import com.epam.brest.course2015.social.consumer.SocialConsumer;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.mail.SocialMail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by alexander_borohov on 8.8.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SocialWebAppContext.class, SocialControllerConfig.class})
@WebAppConfiguration
public class SocialRootControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SocialConsumer socialConsumer;
    @Autowired
    private SocialMail socialMail;

    private User user;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User(login, password, firstName, lastName, age, email);
    }

    @Test
    public void loginApprove() throws Exception {
        String testURL = "/loginapprove";
        String testRef = "http://localhost:8080/social/";

        expect(socialConsumer.isUserInDB(new User(user.getLogin(), user.getPassword()))).andReturn(true);
        expect(socialConsumer.getToken(user.getLogin(), testRole)).andReturn(testToken);
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                post(testURL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("login", user.getLogin())
                        .param("password", user.getPassword())
                        .cookie(new Cookie("Referer", testRef))
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().value("uid", testToken))
                .andExpect(cookie().doesNotExist("Referer"))
                .andExpect(redirectedUrl(testRef))
        ;
    }

    @After
    public void tearDown() throws Exception {
        verify(socialConsumer, socialMail);
        reset(socialConsumer, socialMail);
    }

//    @Test
    public void restPrefix() throws Exception {

    }

//    @Test
    public void init() throws Exception {

    }

//    @Test
    public void login() throws Exception {

    }

//    @Test
    public void getPhoto() throws Exception {

    }

//    @Test
    public void getMessages() throws Exception {

    }
    @Value("${test.firstName}")
    private String firstName;
    @Value("${test.lastName}")
    private String lastName;
    @Value("${test.age}")
    private Integer age;
    @Value("${test.login}")
    private String login;
    @Value("${test.password}")
    private String password;
    @Value("${test.email}")
    private String email;
    @Value("${test.userId1}")
    private Integer userId;
    @Value("${test.token}")
    private String testToken;
    @Value("${test.token1}")
    private String testToken1;
    @Value("${test.userId1}")
    private Integer testUserId;
    @Value("${test.role}")
    private String testRole;
    @Value("${test.role1}")
    private String testRole1;
    @Value("${test.role2}")
    private String testRole2;
}