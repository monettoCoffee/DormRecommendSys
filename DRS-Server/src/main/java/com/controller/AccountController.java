package com.controller;

import com.alibaba.fastjson.JSON;
import com.aop.HeaderTokenInterceptor;
import com.bean.RequestResult;
import com.entry.AccountDO;
import com.entry.AccountInfoAO;
import com.entry.StudentStatusAO;
import com.service.AccountService;
import com.utils.IpUtil;
import com.utils.MD5Util;
import com.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author monetto
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private HeaderTokenInterceptor headerTokenInterceptor;

    // 登记学生学号信息
    @ResponseBody
    @RequestMapping(value = "/register/info", method = RequestMethod.POST)
    public String registerStudentInfo(String regInfoJson) {
        AccountInfoAO regInfo = JSON.parseObject(regInfoJson, AccountInfoAO.class);
        try {
            accountService.registerStudentInfo(regInfo);
        } catch (DuplicateKeyException e) {
            return new RequestResult(2001, "Account already register!", null).toString();
        }
        return RequestResult.success().setMsg("Register successful!").toString();
    }

    // 获取所有学生信息
    @ResponseBody
    @RequestMapping(value = "/select/account", method = RequestMethod.POST)
    public String selectAllAccount() {
        List<StudentStatusAO> allStudent = accountService.getAllRegisterStudent();
        for (StudentStatusAO statusAO: allStudent) {
//            System.out.println("AOName: " + statusAO.getName());
//            System.out.println("AOPlanName: " + statusAO.getPlanName());
        }
        return new RequestResult(2000, "Success", allStudent).toString();
    }

    // 删除学生账号
    @ResponseBody
    @RequestMapping(value = "/delete/account", method = RequestMethod.POST)
    public String deleteStudentAccount(String delInfo) {
        AccountInfoAO accountInfoAO = JSON.parseObject(delInfo, AccountInfoAO.class);
        accountService.deleteStudentAccountFromDatabase(accountInfoAO.getUid());
        return RequestResult.success().setMsg("Register successful!").toString();
    }

    @ResponseBody
    @RequestMapping(value = "/register/account", method = RequestMethod.POST)
    public String registerStudentAccount(AccountDO registerAccountInfo) {
        AccountDO studentAccountInfo = accountService.getStudentByUid(registerAccountInfo.getUid());
        if (studentAccountInfo == null) {
            return new RequestResult(2001, "Info have not register!", null).toString();
        } else if (studentAccountInfo.getUserType() != 0) {
            return new RequestResult(2002, "Info has been register!", null).toString();
        } else {
            accountService.registerStudentAccount(registerAccountInfo);
            return new RequestResult(2000, "Register successful!", null).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(AccountDO accountDO, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/login : " + accountDO.getAccount());
        AccountDO studentAccountInfo = accountService.getStudentByAccount(accountDO.getAccount());
        if (studentAccountInfo == null) {
            return new RequestResult(2001, "Account have not register!", null).toString();
        } else if (MD5Util.get(accountDO.getPassword()).equals(studentAccountInfo.getPassword())) {
            String token = headerTokenInterceptor.setToken(request, response, studentAccountInfo.getUid(), IpUtil.getIpAddress(request));
            return new RequestResult(2000, "Login successful!", token).toString();
        } else {
            return new RequestResult(2002, "Password incorrect!", null).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public String permission(AccountDO accountDO, HttpServletRequest request, HttpServletResponse response) {
        String uid = RequestUtil.getSessionByName(request, "uid");
        if (accountService.adminPermission(uid)) {
            return new RequestResult(2000, null, null).toString();
        } else {
            return new RequestResult(2001, null, null).toString();
        }
    }


}
