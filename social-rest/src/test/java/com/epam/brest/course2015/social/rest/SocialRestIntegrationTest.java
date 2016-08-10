package com.epam.brest.course2015.social.rest;

import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dao.SecurityDao;
import com.epam.brest.course2015.social.dao.UserDao;
import com.epam.brest.course2015.social.dto.SocialDto;
import com.epam.brest.course2015.social.service.SocialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by alexander_borohov on 10.8.16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {SocialRestIntegrationConfig.class})
//@Transactional
public class SocialRestIntegrationTest {
//    @Resource
    private SocialRestController restController;

    private MockMvc mockMvc;
    private User user = new User();
    private SocialDto dto = new SocialDto();

//    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(restController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
        user.setUserId(userId);
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        dto.setUser(user);
        dto.setUsers(Arrays.asList(user, user, user));
    }

//    @Test
    public void testGetUserDto() throws Exception {
        String testUrl = "/userdto";
        String token = new ObjectMapper().writeValueAsString(testToken);
        mockMvc.perform(post(testUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(token)
                )
                .andDo(print());

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
    @Value("${test.role}")
    private String roleUser;
    @Value("${test.role1}")
    private String roleAdmin;
    @Value("${test.role2}")
    private String roleTemp;
    @Value("${test.token}")
    private String testToken;
}
