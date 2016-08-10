package com.epam.brest.course2015.social.app;

import com.epam.brest.course2015.social.consumer.SocialConsumer;
import com.epam.brest.course2015.social.dto.SocialDto;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexander_borohov on 8.8.16.
 */
@Controller
@RequestMapping("/friend")
public class SocialFriendController extends SocialController {
    @Autowired
    private SocialConsumer socialConsumer;

    @RequestMapping("/unknown")
    @Logged
    public ModelAndView getNoFriendsDto(@CookieValue(name = "uid", required = false) Cookie cookie,
                                        HttpServletRequest req,
                                        HttpServletResponse resp) throws IOException {
        ModelAndView mav = new ModelAndView("nofriends");
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                SocialDto dto = socialConsumer.getAllNoFriendsOfAUser(token);
                mav.addObject("dto", dto);
                mav.addObject("mapping", "nofriendstab");
                return mav;
            }
        }
        setReferer(resp, req);
        resp.sendRedirect(getPath(req) + "/login");
        return mav;
    }

    @RequestMapping("/all")
    @Logged
    public ModelAndView getFriendsDto(@CookieValue(name = "uid", required = false) Cookie cookie,
                                      HttpServletRequest req,
                                      HttpServletResponse resp) throws IOException {
        ModelAndView mav = new ModelAndView("friends");
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                SocialDto dto = socialConsumer.getAllFriends(token);
                mav.addObject("dto", dto);
                mav.addObject("mapping", "friendstab");
                return mav;
            }
        }
        setReferer(resp, req);
        resp.sendRedirect(getPath(req) + "/login");
        return mav;
    }

    @RequestMapping("/del")
    @Logged
    public String deleteFriend(@CookieValue(name = "uid", required = false) Cookie cookie,
                               @RequestParam("id2") Integer id2) {
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                socialConsumer.deleteFriend(token, id2);
                return "forward:/user";
            }
        }
        return "forward:/friends";
    }

    @RequestMapping("/add")
    @Logged
    public String addFriendship(@CookieValue(name = "uid", required = false) Cookie cookie,
                                @RequestParam("id2") Integer id2) {
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                socialConsumer.addFriendship(token, id2);
                return "forward:/user";
            }
        }
        return "forward:/nofriends";
    }

}
