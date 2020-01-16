package com.controller;

import com.entry.AccountDO;
import com.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author monetto
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    // 登记学生学号信息
    @RequestMapping(value = "/register/info", method = RequestMethod.POST)
    public String registerStudentInfo(Integer uid) {

        return "";
    }

    @RequestMapping(value = "/register/account", method = RequestMethod.POST)
    public String registerStudentAccount(AccountDO account) {

        return "";
    }
}
