package com.epam.brest.course2015.social.app;

import com.epam.brest.course2015.social.consumer.SocialConsumer;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dto.SocialDto;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexander_borohov on 27.6.16.
 */
@Controller
public class SocialRootController implements SocialController {
    @Value("${rest.prefix}")
    private String restPrefix;

    @Autowired
    private SocialConsumer socialConsumer;

    @RequestMapping("/restAPI")
    @ResponseBody
    @Logged
    public String restPrefix() {
        return restPrefix + "websocket/endpoint";
    }

    @RequestMapping("/")
    @Logged
    public String init(@CookieValue(name = "uid", required = false) Cookie cookie,
                       HttpServletResponse resp, HttpServletRequest req) {
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                return "redirect:/user";
            }
        }
        setReferer(resp, req);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    @Logged
    public ModelAndView login(HttpServletRequest req,
                              HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        Cookie cookie1 = WebUtils.getCookie(req, "uid");

        Cookie cookie = new Cookie("uid", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/loginapprove", method = RequestMethod.POST)
    @Logged
    public String loginApprove(HttpServletResponse resp,
                               HttpServletRequest req,
                               @ModelAttribute("user") User user,
                               @CookieValue(name = "Referer", required = false) String referer) throws IOException {
        // Checking for a user from DataBase
        if (socialConsumer.isUserInDB(user)) {
            // Generating or receiving existing token
            String token = socialConsumer.getToken(user.getLogin(), "USER");
            // Setting a Cookie
            Cookie cookie = new Cookie("uid", token);
            cookie.setMaxAge(60*60*24);
            resp.addCookie(cookie);
            // Proceed to User page or to a page where user got redirect to "login"
            return (referer != null)? "redirect:" + referer: "redirect:user";
        }
        else {
            // Proceed to Login Page
            return "redirect:/login";
        }
    }

    @RequestMapping("photo")
    @Logged
    public ModelAndView getPhoto(@CookieValue(name = "uid", required = false) Cookie cookie,
                                 HttpServletRequest req,
                                 HttpServletResponse resp) throws IOException {
        ModelAndView mav = new ModelAndView("photo");
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                SocialDto dto = socialConsumer.getUserDto(token);
                mav.addObject("dto", dto);
                mav.addObject("mapping", "phototab");
                return mav;
            }
        }
        setReferer(resp, req);
        resp.sendRedirect("login");
        return mav;
    }

    @RequestMapping("messages")
    @Logged
    public ModelAndView getMessages(@CookieValue(name = "uid", required = false) Cookie cookie,
                                    HttpServletRequest req,
                                   HttpServletResponse resp) throws IOException {
        ModelAndView mav = new ModelAndView("messages");
        if (cookie != null) {
            String token = cookie.getValue();
            if (token != null) {
                SocialDto dto = socialConsumer.getUserDto(token);
                mav.addObject("dto", dto);
                mav.addObject("mapping", "messagestab");
                return mav;
            }

        }
        setReferer(resp, req);
        resp.sendRedirect("login");
        return mav;
    }

}
