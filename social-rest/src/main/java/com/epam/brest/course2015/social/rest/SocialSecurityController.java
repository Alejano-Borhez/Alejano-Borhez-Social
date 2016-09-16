package com.epam.brest.course2015.social.rest;

import com.epam.brest.course2015.social.dao.SecurityDao;
import com.epam.brest.course2015.social.dao.UserDao;
import com.epam.brest.course2015.social.service.SocialSecurityServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by alexander_borohov on 14.9.16.
 */
@RestController
@RequestMapping("/security")
public class SocialSecurityController {
    @Autowired
    private SecurityDao securityDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping("/{options}")
    public Object getPassword(HttpServletRequest request,
                              @RequestParam("username") String username,
                              @PathVariable("options") String options) {
        if (request.getHeader("Security") != null) {
            switch (options) {
                case "username":
                    return userDao.getUserByLogin(username).getLogin();
                case "password":
                    return userDao.getUserByLogin(username).getPassword();
                case "accEnabled":
                    return (userDao.getUserByLogin(username) != null);
                case "authorities":
                    return securityDao.getTokenByUserId(userDao.getUserByLogin(username).getUserId()).getRole().toString();
                case "accNonLocked":
                    return (userDao.getUserByLogin(username) != null);
                case "credNonExpired":
                    return securityDao.getTokenByUserId(userDao.getUserByLogin(username).getUserId()).getExpires().after(new Date());
                case "isEnabled":
                    return (userDao.getUserByLogin(username) != null);
                default:
                    return null;
            }
        }
        return null;
    }

}
