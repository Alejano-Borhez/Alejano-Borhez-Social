package com.epam.brest.course2015.social.app;

import com.epam.brest.course2015.social.test.Logged;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alexander_borohov on 8.8.16.
 */
public class SocialController {
    void setReferer(HttpServletResponse resp, HttpServletRequest req) {
        Cookie referer = new Cookie("Referer", req.getRequestURL().toString());
        referer.setMaxAge(60*60*4);
        resp.addCookie(referer);
    }

    String getPath(HttpServletRequest req) {
        return req.getRequestURL().toString().replace(req.getServletPath(), "");
    }

    void settingACookie(HttpServletResponse resp, String token) {
        Cookie cookie = new Cookie("uid", token);
        cookie.setMaxAge(60 * 60 * 24);
        resp.addCookie(cookie);
    }
}
