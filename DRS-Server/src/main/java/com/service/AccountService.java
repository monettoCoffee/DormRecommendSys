package com.service;

import com.dao.AccountDao;
import com.entry.AccountDO;
import com.entry.AccountInfoAO;
import com.entry.StudentStatusAO;
import com.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public void registerStudentInfo(AccountInfoAO accountInfoAO) throws DuplicateKeyException{
        accountDao.registerStudentInfo(accountInfoAO);
    }

    public AccountDO getStudentByUid(String uid) {
        return accountDao.getStudentByUid(uid);
    }

    public List<StudentStatusAO> getAllRegisterStudent() {
        return accountDao.getAllRegisterStudent();
    }

    public boolean adminPermission(String uid){
        AccountDO accountDO = this.getStudentByUid(uid);
        return accountDO == null || accountDO.getUserType() == 2;
    }

    public AccountDO getStudentByAccount(String account) {
        return accountDao.getStudentByAccount(account);
    }

    public void registerStudentAccount(AccountDO accountDO) {
        accountDO.setPassword(MD5Util.get(accountDO.getPassword()));
        accountDao.registerStudentAccount(accountDO);
    }

    public void deleteStudentAccountFromDatabase(String uid) {
        accountDao.deleteStudentAccountFromDatabase(uid);

    }
}
