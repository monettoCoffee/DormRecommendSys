package com.dao;

import com.entry.UserChosenDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author monetto
 */
@Mapper
public interface UserChosenDao {

    Object saveUserChosen(UserChosenDO userChosen);

}
