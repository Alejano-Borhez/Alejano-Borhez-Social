package com.epam.brest.course2015.social.app;

import com.epam.brest.course2015.social.consumer.SocialConsumer;
import com.epam.brest.course2015.social.dto.SocialDto;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexander_borohov on 8.8.16.
 */
@Controller
@RequestMapping("/user")
public class SocialUserController extends SocialController {
    @Autowired
    private SocialConsumer socialConsumer;

    @RequestMapping("")
    @Logged
    public ModelAndView getUserDto(@CookieValue(name = "uid", required = false) Cookie cookie,
                                   HttpServletRequest req,
                                   HttpServletResponse resp) throws IOException {
        ModelAndView mav = new ModelAndView("user");
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                SocialDto dto = socialConsumer.getUserDto(token);
                mav.addObject("dto", dto);
                mav.addObject("mapping", "usertab");
                return mav;
            }
        }
        setReferer(resp, req);
        resp.sendRedirect(getPath(req) + "/login");
        return mav;
    }

    @RequestMapping("/find/{login}")
    @Logged
    public ModelAndView getUser(@CookieValue(name = "uid", required = false) Cookie cookie,
                                @PathVariable("login") String userLogin,
                                HttpServletRequest req,
                                HttpServletResponse resp) throws IOException {
        ModelAndView mav = new ModelAndView("user");
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                SocialDto dto = socialConsumer.getUserDto(token);
                mav.addObject("dto", dto);
                mav.addObject("mapping", "foreignusertab");
                return mav;
            }
        }
        setReferer(resp, req);
        resp.sendRedirect(getPath(req) + "/login");
        return mav;
    }

    @RequestMapping("/add")
    @Logged
    public ModelAndView addUser() {
        return new ModelAndView("adduser");
    }

    @RequestMapping("/delete")
    @Logged
    public String deleteUser(@RequestParam("id")
                                     Integer id) {
        socialConsumer.deleteUser(id);
        return "forward:/user/all";
    }

    @RequestMapping("/change/{action}")
    @Logged
    public String changeUser(@CookieValue(name = "uid", required = false) Cookie cookie,
                             @RequestParam("param") String param,
                             @PathVariable("action") String action) {
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                socialConsumer.changeUser(token, action, param);
                return "forward:/user";
            }
        }
        return "forward:/user";
    }

    @RequestMapping("/bydate")
    @Logged
    public ModelAndView getAllUsersByDate(
            @CookieValue(name = "uid", required = false) Cookie cookie,
            HttpServletRequest req,
            HttpServletResponse resp,
            @RequestParam("datemin") String dateMin,
            @RequestParam("datemax") String dateMax) throws IOException {
        ModelAndView mav = new ModelAndView("usersbydate");
        if (cookie != null) {
            String token = cookie.getValue();
            SocialDto dto = socialConsumer
                    .getAllUsersByDate(token, dateMin, dateMax);
            if (dto != null) {
                mav.addObject("dto", dto);
                mav.addObject("mapping", "usersbydatetab");
                return mav;
            }
        }
        setReferer(resp, req);
        resp.sendRedirect(getPath(req) + "/login");
        return mav;
    }

    @RequestMapping("/all")
    @Logged
    public ModelAndView getAllUsersDto(@CookieValue(name = "uid", required = false) Cookie cookie,
                                       HttpServletRequest req,
                                       HttpServletResponse resp
    ) throws IOException {
        ModelAndView mav = new ModelAndView("users");
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                SocialDto dto = socialConsumer.getAllUsers(token);
                mav.addObject("dto", dto);
                mav.addObject("mapping", "userstab");
                return mav;
            }
        }
        setReferer(resp, req);
        resp.sendRedirect(getPath(req) + "/login");
        return mav;
    }


}
