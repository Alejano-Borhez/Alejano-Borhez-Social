package com.epam.brest.course2015.social.app;

import com.epam.brest.course2015.social.consumer.SocialConsumer;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dto.SocialDto;
import com.epam.brest.course2015.social.mail.SocialMail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by alexander_borohov on 5.8.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SocialWebAppContext.class, SocialControllerConfig.class})
@WebAppConfiguration
public class SocialUserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SocialConsumer socialConsumer;

    @Autowired
    private SocialMail socialMail;

    private User user;
    private MockMvc mockMvc;
    private SocialDto dto;
    private Cookie cookie;
    private String servletContext = "http://localhost/user";

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User(login, password, firstName, lastName, age, email);
        user.setUserId(userId);
        dto = new SocialDto();
            dto.setUser(user);
            dto.setUsers(Arrays.asList(user, user, user));
        cookie = new Cookie("uid", testToken);
    }

    @After
    public void tearDown() throws Exception {
        verify(socialConsumer, socialMail);
        reset(socialConsumer, socialMail);
    }

    @Test
    public void getUserDto() throws Exception {
        String testUrl = servletContext + "";

        expect(socialConsumer.getUserDto(testToken)).andReturn(dto);
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                .cookie(cookie)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(model().attribute("dto", dto))
                .andExpect(model().attribute("mapping", "usertab"))
                .andExpect(cookie().doesNotExist("Referer"))
        ;

    }


    @Test
    public void getUserDtoFailNullCookie() throws Exception {
        String testUrl = servletContext + "";

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"))
                .andExpect(cookie().value("Referer", testUrl))
        ;

    }

    @Test
    public void getUserDtoFailNullToken() throws Exception {
        String testUrl = servletContext + "";

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                .cookie(new Cookie("uid", null))
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"))
                .andExpect(cookie().value("Referer", testUrl))
        ;

    }

    @Test
    public void adduser() throws Exception {
        String testUrl = servletContext + "/add";

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("adduser"));

    }

    @Test
    public void deleteUser() throws Exception {
        String testUrl = servletContext + "/delete";
        socialConsumer.deleteUser(testUserId);
        expectLastCall();

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                .param("id", testUserId.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;

    }

    @Test
    public void changeUserPassword() throws Exception {
        String testUrl = servletContext + "/change/password";
        socialConsumer.changeUser(testToken, "password", password);
        expectLastCall();

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                .param("param", password)
                .cookie(cookie)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/user"));

    }

    @Test
    public void changeUserLogin() throws Exception {
        String testUrl = servletContext + "/change/login";
        socialConsumer.changeUser(testToken, "login", login);
        expectLastCall();

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                        .param("param", login)
                        .cookie(cookie)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/user"));

    }

    @Test
    public void changeUserFirstName() throws Exception {
        String testUrl = servletContext + "/change/firstname";
        socialConsumer.changeUser(testToken, "firstname", firstName);
        expectLastCall();

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                        .param("param", firstName)
                        .cookie(cookie)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/user"));

    }

    @Test
    public void changeUserLastName() throws Exception {
        String testUrl = servletContext + "/change/lastname";
        socialConsumer.changeUser(testToken, "lastname", lastName);
        expectLastCall();

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                        .param("param", lastName)
                        .cookie(cookie)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/user"));

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