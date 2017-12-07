package com.ynsh.service;

import com.ynsh.model.User;

import java.util.List;

/**
 * Created by chenjianjun on 15/7/15.
 */
public interface UserService {
    User create(User user) throws Exception;

    User update(User user)  throws Exception;

    User get(Long id)  throws Exception;

    User getByUserName(String userName) throws Exception;

    void delete(Long id)  throws Exception;

    List<User> getForList() throws Exception;

    String getMineContactNumber() throws Exception;
}
