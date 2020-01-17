package com.controller;

import com.utils.IpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author monetto
 */
@Controller
public class PageController {

    @RequestMapping("/login")
    public String loginIndex() {
        return "login";
    }

    @RequestMapping("/question")
    public String questionIndex() {
        return "question";
    }

    @RequestMapping("/manage")
    public String manageIndex() {
        return "manage";
    }



}
