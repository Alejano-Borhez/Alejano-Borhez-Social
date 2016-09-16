package com.epam.brest.course2015.social.service;

import com.epam.brest.course2015.social.dao.SecurityDao;
import com.epam.brest.course2015.social.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created by alexander_borohov on 14.9.16.
 */
public class SocialRole implements GrantedAuthority, Serializable {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SecurityDao securityDao;

    private static String username;

    private String authority;

    public static void setUsername(String username) {
        SocialRole.username = username;
    }


    @Override
    public String getAuthority() {
        this.authority = securityDao.getTokenByUserId(userDao.getUserByLogin(username).getUserId()).getRole().toString();
        return authority;
    }
}
