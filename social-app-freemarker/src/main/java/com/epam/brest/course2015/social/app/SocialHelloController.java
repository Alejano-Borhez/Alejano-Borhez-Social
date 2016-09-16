package com.epam.brest.course2015.social.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alexander_borohov on 14.9.16.
 */
@Controller
@RequestMapping("/hello")
public class SocialHelloController {

    @RequestMapping("")
    public ModelAndView hello(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("hello");
        mav.addObject("hello", "Hello ");
        mav.addObject("login", request.getRemoteUser());
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView helloLogin(HttpServletRequest request,
                                   @RequestParam(name = "failure", required = false) boolean failure,
                                   @RequestParam(name = "logout", required = false) boolean logout
                    ) {
        ModelAndView mav = new ModelAndView("helloLogin");
        return mav;

    }

}
