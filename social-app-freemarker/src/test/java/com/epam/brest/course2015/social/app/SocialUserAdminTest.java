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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.CookieResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by alexander_borohov on 1.8.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SocialUserAdministratorConfig.class, SocialWebAppContext.class})
@WebAppConfiguration
public class SocialUserAdminTest {
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


//    Testing WebApp context
    @Autowired
    private WebApplicationContext webAppContext;

//    MockMVC infrastructure
    private MockMvc mockMvc;

//    Mocks and test data
    @Autowired
    private SocialConsumer socialConsumer;
    @Autowired
    private SocialMail socialMail;
    private User user;
    private SocialDto dto;
    private MultiValueMap<String, String> userMap;

    @Before
    public void setUp() throws Exception {
        userMap = new LinkedMultiValueMap<>();
        user = new User(login, password, firstName, lastName, age, email);
        dto = new SocialDto();
        dto.setUser(user);
        dto.setUsers(Arrays.asList(user, user));

        Map<String, String> map = new HashMap<>();
        map.put("login", user.getLogin());
        map.put("password", user.getPassword());
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("age", user.getAge().toString());
        map.put("email", user.getEmail());

        userMap.setAll(map);

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        verify(socialConsumer);
        reset(socialConsumer);
        verify(socialMail);
        reset(socialMail);
    }

    @Test
    public void addUserSubmit() throws Exception {
        String testUrl = "/admin/addusersubmit";
        expect(socialConsumer.addUserSubmit(user)).andReturn(true);
        expect(socialConsumer.getToken(user.getLogin(), testRole2)).andReturn(testToken);
        socialMail.sendApprovalEmail("http://localhost" + testUrl, testToken, user);
        replay(socialConsumer);
        replay(socialMail);

        mockMvc.perform(
                post(testUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .params(userMap)
                )
                .andDo(print())
                .andExpect(view().name("addusersubmit"))
                .andExpect(model().attribute("email", user.getEmail()))
                .andExpect(model().attribute("user", user))
                .andExpect(cookie().value("uid", testToken))
                .andExpect(cookie().maxAge("uid", 60*60*24))
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
                .andExpect(content().encoding("UTF-8"))
                ;
    }

    @Test
    public void testSubmitFail() throws Exception {
        String testUrl = "/admin/addusersubmit";

        expect(socialConsumer.addUserSubmit(user)).andReturn(false);
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                post(testUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .params(userMap)
                )
                .andDo(print())
                .andExpect(redirectedUrl("../login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "../login"))
                .andExpect(cookie().doesNotExist("uid"))
                .andExpect(content().string(""));

    }

    @Test
    public void testApprove() throws Exception {
        String testUrl = "/admin/approve";
        SocialDto dto = new SocialDto();

        expect(socialConsumer.tempTokenApprove(testToken)).andReturn(testToken1);
        expect(socialConsumer.getUserDto(testToken1)).andReturn(dto);
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", testToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("loginapprove"))
                .andExpect(model().attribute("dto", dto))
                .andExpect(model().hasNoErrors())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
        ;

    }

    @Test
    public void testApproveFail() throws Exception {
        String testUrl = "/admin/approve";

        expect(socialConsumer.tempTokenApprove(testToken)).andReturn(null);
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", testToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("loginapprove"))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
        ;

    }

    @Test(expected = IllegalArgumentException.class)
    public void testApproveFailNullTempToken() throws Exception {
        String testUrl = "/admin/approve";

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("token")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("loginapprove"))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
        ;
    }

    @Test
    public void testRequestNewPassword() throws Exception {
        String testUrl = "/admin/password/requestNew";

        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                )
                .andDo(print())
                .andExpect(view().name("password/request"))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
                .andExpect(cookie().doesNotExist("uid"))
        ;

    }

    @Test
    public void testResetPasswordSuccess() throws Exception {
        String testUrl = "/admin/password/reset";

        expect(socialConsumer.emailApprove(user.getEmail())).andReturn(user);
        expect(socialConsumer.getToken(user.getLogin(), testRole2)).andReturn(testToken);
        socialMail.sendPasswordRecoveryEmail("http://localhost"+testUrl, testToken, user);
        expectLastCall();
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                post(testUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("email", user.getEmail())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("password/reset"))
                .andExpect(model().attribute("email", user.getEmail()))
                .andExpect(model().attribute("e_mail", user.getEmail()))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
                .andExpect(cookie().doesNotExist("uid"))
        ;
    }

    @Test
    public void testResetPasswordFailInvalidEmail() throws Exception {
        String testUrl = "/admin/password/reset";

        expect(socialConsumer.emailApprove(user.getEmail())).andReturn(null);
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                post(testUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("email", user.getEmail())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("password/reset"))
                .andExpect(model().attributeDoesNotExist("e_mail"))
                .andExpect(model().attribute("email", user.getEmail()))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
                .andExpect(cookie().doesNotExist("uid"))
        ;
    }

    @Test
    public void testResetPasswordFailInvalidToken() throws Exception {
        String testUrl = "/admin/password/reset";

        expect(socialConsumer.emailApprove(user.getEmail())).andReturn(user);
        expect(socialConsumer.getToken(user.getLogin(), testRole2)).andReturn(null);
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                post(testUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", user.getEmail())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("password/reset"))
                .andExpect(model().attributeDoesNotExist("e_mail"))
                .andExpect(model().attribute("email", user.getEmail()))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
                .andExpect(cookie().doesNotExist("uid"))
        ;

    }

    @Test
    public void testNewPassword() throws Exception {
        String testUrl = "/admin/password/new";

        expect(socialConsumer.getUserDto(testToken)).andReturn(dto);
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                get(testUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("token", testToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("password/new"))
                .andExpect(model().attribute("dto", dto))
                .andExpect(model().attributeDoesNotExist("reset"))
        ;
    }

    @Test
    public void testChangePassword() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.set("password1", password);
            params.set("password2", password);
            params.set("token", testToken);

        expect(socialConsumer.getUserDto(testToken)).andReturn(dto);
        expect(socialConsumer.getToken(login, testRole)).andReturn(testToken1);
        socialConsumer.changePassword(testToken1, password);
        expectLastCall();
        replay(socialConsumer, socialMail);

        mockMvc.perform(
                post("/admin/password/change")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .params(params)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/admin/password/change/user"))
                .andExpect(cookie().value("uid", testToken1));


    }
}