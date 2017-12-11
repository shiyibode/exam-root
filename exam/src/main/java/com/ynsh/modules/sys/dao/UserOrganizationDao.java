package com.ynsh.modules.sys.dao;

import com.ynsh.common.core.annotation.MyBatisDao;
import com.ynsh.common.core.persistence.dao.CrudDao;
import com.ynsh.modules.sys.entity.User;
import com.ynsh.modules.sys.entity.UserOrganization;

import java.util.List;

/**
 * 用户DAO接口
 */
@MyBatisDao
public interface UserOrganizationDao extends CrudDao<UserOrganization, Long> {

    /**
     * 根据用户ID查询用户, 且用户在机构是“在职”状态
     * @param userId
     * @return
     */
    public UserOrganization getByUserId(Long userId);

    /**
     * 根据用户编号/柜员号查询用户, 且用户在机构是“在职”状态
     * @param userCode
     * @return
     */
    public UserOrganization getByUserCode(String userCode);

    /**
     * 查询全部用户数目
     * @return
     */
    public long findAllCount(UserOrganization userOrganization);


    /**
     * 通过柜员号获取用户信息，不包含机构信息
     * @param tellerCode
     * @return
     */
    public User getUserByUserCode(String tellerCode);

    /**
     * 新增用户
     * @param user
     * @return
     */
    public int insertUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public int updateUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public int deleteUser(User user);

    /**
     * 更新用户密码
     * @param user
     * @return
     */
    public int updateUserPassword(User user);

    /**
     * 更新登录信息，如：登录IP、登录时间
     * @param user
     * @return
     */
    public int updateUserLoginInfo(User user);


    /**
     * 删除用户角色关联数据
     * @param user
     * @return
     */
    public int deleteUserRole(User user);

    /**
     * 插入用户角色关联数据
     * @param user
     * @return
     */
    public int insertUserRole(User user);

    /**
     * 获取拥有某一角色的所有用户列表
     * @param roleId
     * @return
     */
    List<UserOrganization> findUserListByRoleId(Long roleId);


    /**
     * 通过id获取用户机构信息
     * @param id
     * @return
     */
    public UserOrganization getUserOrganizationById(Long id);


    public List<UserOrganization> findUserOrganizationList(UserOrganization userOrganization);

    /**
     * 物理删除用户机构关联数据
     * @param userOrganization
     * @return
     */
    public int realDelete(UserOrganization userOrganization);


    ///**
    // * 逻辑删除用户机构关联数据
    // * @param userOrganization
    // * @return
    // */
    //public int deleteByUserId(UserOrganization userOrganization);
    //
    ///**
    // * 逻辑删除用户机构关联数据
    // * @param userOrganization
    // * @return
    // */
    //public int deleteByOrganizationId(UserOrganization userOrganization);
    //
}
