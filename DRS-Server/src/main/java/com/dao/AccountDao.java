package com.dao;

import com.entry.AccountDO;
import com.entry.AccountInfoAO;
import com.entry.StudentStatusAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountDao {

    public void registerStudentInfo(AccountInfoAO accountInfoAO);

    public AccountDO getStudentByUid(String uid);

    public List<StudentStatusAO> getAllRegisterStudent();

    public AccountDO getStudentByAccount(String account);

    public void registerStudentAccount(AccountDO accountDO);

    public void deleteStudentAccountFromDatabase(String uid);

}
