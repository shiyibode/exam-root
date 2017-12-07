package com.ynsh.controller;

import com.ynsh.model.User;
import com.ynsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by chenjianjun on 15/7/21.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/createUser.do")
    public ModelAndView createUser(User user) {
        ModelAndView mav = new ModelAndView();

        try {
            userService.create(user);
            mav.addObject("user", user);
            mav.setViewName("/success.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("user", user);
            mav.setViewName("/createUser.jsp");
        }

        return mav;
    }

    @RequestMapping("/getUser.do")
    public ModelAndView getUser(@RequestParam("userName")String userName) {
        ModelAndView mav = new ModelAndView();

        try {
            User user = userService.getByUserName(userName);
            mav.addObject("user", user);
            mav.setViewName("/success.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("message", e.getMessage());
            mav.setViewName("/error.jsp");
        }

        return mav;
    }
}
