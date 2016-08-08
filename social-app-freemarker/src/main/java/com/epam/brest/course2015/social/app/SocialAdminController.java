package com.epam.brest.course2015.social.app;

import com.epam.brest.course2015.social.consumer.SocialConsumer;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dto.SocialDto;
import com.epam.brest.course2015.social.mail.SocialMail;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexander_borohov on 26.7.16.
 */
@Controller
@RequestMapping("/admin")
@ControllerAdvice
public class SocialAdminController extends SocialController {
    @Value("${role.admin}")
    private String roleAdmin;
    @Value("${role.user}")
    private String roleUser;
    @Value("${role.temp}")
    private String roleTemp;

    @Autowired
    private SocialConsumer socialConsumer;
    @Autowired
    private SocialMail socialMail;

    @RequestMapping("password/requestNew")
    @Logged
    public String requestNewPassword() {
        return "password/request";
    }

    @RequestMapping("password/reset")
    @Logged
    public ModelAndView resetPasswordAndSendToken(@ModelAttribute("email") String email,
                                                  HttpServletRequest req) {
        ModelAndView mav = new ModelAndView("password/reset");
        User user = socialConsumer.emailApprove(email);
        if (user != null) {
            String token = socialConsumer.getToken(user.getLogin(), roleTemp);
            if (token != null) {
                mav.addObject("e_mail", email);
                String path = getPath(req);

                socialMail.sendPasswordRecoveryEmail(path, token, user);
            }
        }
        return mav;
    }

    @RequestMapping("password/new")
    @Logged
    public ModelAndView enterNewPassword(@RequestParam("token") String token,
                                         @RequestParam(name="reset", required = false) String reset) {
        ModelAndView mav = new ModelAndView("password/new");
        SocialDto dto = socialConsumer.getUserDto(token);
        if (dto!=null) {
            mav.addObject("dto", dto);
            mav.addObject("token", token);
        }
        if (reset!=null) mav.addObject("reset", reset);

        return mav;
    }

    @RequestMapping("password/change")
    @Logged
    public String changePassword(@RequestParam("password1") String password1,
                                 @RequestParam("password2") String password2,
                                 @RequestParam("token") String token,
                                 HttpServletRequest req,
                                 HttpServletResponse resp) {
        String path = getPath(req);

        String newToken = null;
        if (password1 != null && password1.equals(password2)) {
            SocialDto dto = socialConsumer.getUserDto(token);
            if (dto != null && dto.getUser() != null) {
                newToken = socialConsumer.getToken(dto.getUser().getLogin(), roleUser);
                socialConsumer.changeUser(newToken, "password", password1);
            }
        }
        if (newToken != null) {
            settingACookie(resp, newToken);
            return "redirect:" + path + "/user";
        }
        return "redirect:"+ path + "/admin/password/new?token=" + token + "&reset=reset";

    }

    @RequestMapping("approve")
    @Logged
    public ModelAndView approveRegistration(@RequestParam("token") String token,
                                            HttpServletResponse resp) throws IOException {
        ModelAndView mav = new ModelAndView("loginapprove");

        String newToken = socialConsumer.tempTokenApprove(token);
        if (newToken != null) {
            mav.addObject("dto", socialConsumer.getUserDto(newToken));
        }
        return mav;
    }

    @RequestMapping("addusersubmit")
    @Logged
    public ModelAndView addUserSubmit(@ModelAttribute("user") User user,
                                      HttpServletRequest req,
                                      HttpServletResponse resp) throws IOException {
        ModelAndView mav = new ModelAndView("addusersubmit");

        if (socialConsumer.addUserSubmit(user)) {
            String token = socialConsumer.getToken(user.getLogin(), roleTemp);
            // Setting a Cookie
            settingACookie(resp, token);
            mav.addObject("email", user.getEmail());
            // Sending an E-mail with account verification details
            socialMail.sendApprovalEmail(getPath(req), token, user);
            return mav;
        }
        // Redirection if User is not added to DataBase
        resp.sendRedirect("../login");
        return null;
    }

}
