package com.epam.brest.course2015.social.service;

import com.epam.brest.course2015.social.dao.SecurityDao;
import com.epam.brest.course2015.social.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Created by alexander_borohov on 14.9.16.
 */
public class SocialSecurityServiceProvider implements UserDetails {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SecurityDao securityDao;
    @Autowired
    private SocialRole role;

    private static String name;

    public SocialSecurityServiceProvider() {}

    @Override
    public Collection<SocialRole> getAuthorities() {
        SocialRole.setUsername(name);
        Collection<SocialRole> socialRoles = new HashSet<>();
        socialRoles.add(role);
        return socialRoles;
    }

    @Override
    public String getPassword() {
        return userDao.getUserByLogin(name).getPassword();
    }

    @Override
    public String getUsername() {
        return userDao.getUserByLogin(name).getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return (userDao.getUserByLogin(name) != null);
    }

    @Override
    public boolean isAccountNonLocked() {
        return (userDao.getUserByLogin(name) != null);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return securityDao.getTokenByUserId(userDao.getUserByLogin(name).getUserId())
                .getExpires().after(new Date());
    }

    @Override
    public boolean isEnabled() {
        return (userDao.getUserByLogin(name) != null);
    }

    public static void setName(String name) {
        SocialSecurityServiceProvider.name = name;
    }
}
