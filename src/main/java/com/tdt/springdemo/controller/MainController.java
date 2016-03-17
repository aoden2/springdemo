package com.tdt.springdemo.controller;

import com.tdt.springdemo.dao.UserDAO;
import com.tdt.springdemo.model.User;
import com.tdt.springdemo.utils.MailUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/profile")
    public String aaa() {

        return "profile";
    }

    @RequestMapping(value = "/login")
    public String login() {

        return "login";
    }

    @RequestMapping("/")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {

        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public String signup(@ModelAttribute @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(required = false) String repassword,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        if (!user.getPassword().equals(repassword)) {

            model.addAttribute("error", "Password not match!");
            return "signup";
        }
        if (userDAO.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "User existed!");
            return "signup";
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            userDAO.save(user);
        }
        return "login";
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String forgot(Model model) {
        return "forgot";
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public String forgot(@RequestParam String email) {

        User user = userDAO.findByEmail(email);
        if (user.getEmail().equals(email)) {
            String password = RandomStringUtils.randomAlphabetic(8);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            MailUtil.send(email, "Reset your password", "This is your new temporary password: " + password);
            user.setPassword(hashedPassword);
            userDAO.save(user);
            return "reset";
        }
        return "forgot";
    }
}
