package com.ynsh.service.impl;


import com.ynsh.dao.UserMapper;
import com.ynsh.model.User;
import com.ynsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userDAO;

    public User create(User user) throws Exception {
        try {
            userDAO.insertSelective(user);
            return user;
        } catch (Exception e) {
            throw new Exception("添加用户失败！");
        }
    }

    public User update(User user) throws Exception {
        try {
            userDAO.updateByPrimaryKeySelective(user);
            return user;
        } catch (Exception e) {
            throw new Exception("修改用户信息失败！");
        }
    }

    public User get(Long id) throws Exception {
        try {
            User retUser = userDAO.selectByPrimaryKey(id);
            return retUser;
        } catch (Exception e) {
            throw new Exception("获取用户信息失败！");
        }
    }

    public User getByUserName(String userName) throws Exception {
        try {
            User retUser = userDAO.selectByUserName(userName);
            return retUser;
        } catch (Exception e) {
            throw new Exception("获取用户信息失败！");
        }
    }

    public void delete(Long id) throws Exception {
        try {
            userDAO.deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new Exception("删除用户失败！");
        }
    }

    public List<User> getForList() throws Exception {
        try {
            List<User> userList = userDAO.selectForList();
            return userList;
        } catch (Exception e) {
            throw new Exception("获取用户信息失败！");
        }
    }

    @Override
    public String getMineContactNumber() throws Exception {
        return userDAO.getMineContactNumber();
    }
}
