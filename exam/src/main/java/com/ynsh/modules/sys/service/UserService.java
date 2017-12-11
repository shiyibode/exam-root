package com.ynsh.modules.sys.service;

import com.ynsh.common.config.Global;
import com.ynsh.common.core.persistence.Page;
import com.ynsh.common.core.service.CrudService;
import com.ynsh.common.core.service.ServiceException;
import com.ynsh.common.utils.DateUtils;
import com.ynsh.common.utils.PasswordUtils;
import com.ynsh.common.utils.StringUtils;
import com.ynsh.modules.sys.dao.OrganizationDao;
import com.ynsh.modules.sys.dao.UserOrganizationDao;
import com.ynsh.modules.sys.entity.Organization;
import com.ynsh.modules.sys.entity.User;
import com.ynsh.modules.sys.entity.UserOrganization;
import com.ynsh.modules.sys.utils.DataScopeUtils;
import com.ynsh.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 区域Service
 */
@Service
@Transactional(readOnly = true)
public class UserService extends CrudService<UserOrganizationDao, UserOrganization, Long> {

    @Autowired
    private OrganizationDao organizationDao;

    /**
     * 根据登录名获取用户
     * @param code
     * @return
     */
    public User getUserByUserCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return UserUtils.getUserByUserCode(code);
    }

    /**
     * 分页查询人员
     * @param page 分页对象
     * @param userOrganization
     * @return
     */
    @Override
    public Page<UserOrganization> findPage(Page<UserOrganization> page, UserOrganization userOrganization) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        userOrganization.getSqlMap().put("dsf", DataScopeUtils.dataScopeFilter(Global.getSysModuleId(), userOrganization.getCurrentUserOrganization(), "o", "u"));

        logger.info("userCodeOrName: {}", userOrganization.getUserCodeOrName());

        page.setAutoPage(false);
        userOrganization.setPage(page);
        page.setCount(dao.findCount(userOrganization));
        if (page.getCount() > 0) {
            page.setList(dao.findList(userOrganization));
        }
        return page;
    }


    @Override
    @Transactional(readOnly = false)
    public void save(UserOrganization userOrganization) {
        if (userOrganization.getIsNewRecord()) {
            User user = userOrganization.getUser();
            user.setLoginPassword(PasswordUtils.encryptPassword(PasswordUtils.DEFAULT_PASSWORD));
            user.setAuditPassword(PasswordUtils.encryptPassword(PasswordUtils.DEFAULT_PASSWORD));

            user.preInsert();
            dao.insertUser(user);

            //userOrganization.setUserId(user.getId());
            userOrganization.setStartDate(user.getEntryDate());
            userOrganization.setStatus(1);
            userOrganization.preInsert();
            dao.insert(userOrganization);
        } else {
            User user = userOrganization.getUser();
            user.preUpdate();
            dao.updateUser(user);
        }
        UserUtils.clearCache(userOrganization);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(List<UserOrganization> entities) {
        super.save(entities);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(UserOrganization userOrganization) {
        dao.deleteUser(userOrganization.getUser());
        dao.deleteUserRole(userOrganization.getUser());
        super.delete(userOrganization);
        UserUtils.clearCache(userOrganization);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(List<UserOrganization> entities) {
        this.delete(entities);
    }

    @Transactional(readOnly = false)
    public void resetPassword(UserOrganization userOrganization) {
        if (userOrganization == null || userOrganization.getUser() == null || userOrganization.getUser().getId() == null) {
            throw new ServiceException("重置密码出错, 请求数据没有包含用户ID,无法完成密码重置!");
        }
        User user = userOrganization.getUser();
        if (StringUtils.isNotEmpty(user.getNewLoginPassword())) {
            user.setLoginPassword(PasswordUtils.encryptPassword(user.getNewLoginPassword()));
            user.preUpdate();
            dao.updateUserPassword(user);
            UserUtils.clearCache(userOrganization);
        }
    }

    @Transactional(readOnly = false)
    public void resetPassword(List<UserOrganization> entities) {
        for (UserOrganization entity :entities) {
            this.resetPassword(entity);
        }
    }


    @Transactional(readOnly = false)
    public void updatePassword(UserOrganization userOrganization) {
        if (userOrganization == null || userOrganization.getUser() == null) {
            return;
        }
        User user = userOrganization.getUser();
        User dbUser = null;
        if (user.getId() != null) {
            dbUser = UserUtils.getUserByUserId(user.getId());
        } else {
            dbUser = UserUtils.getUser();
            user.setId(dbUser.getId());
        }
        if (null == dbUser) {
            throw new ServiceException("修改密码出错, 获取用户信息失败!");
        }

        if (StringUtils.isNotEmpty(user.getLoginPassword()) && StringUtils.isNotEmpty(user.getNewLoginPassword())) {
            boolean oldLoginPasswordValid = PasswordUtils.validatePassword(user.getLoginPassword(), dbUser.getLoginPassword());
            if (!oldLoginPasswordValid) {
                throw new ServiceException("原密码错误, 请输入正确的密码!");
            }

            user.setLoginPassword(PasswordUtils.encryptPassword(user.getNewLoginPassword()));
            user.preUpdate();
            dao.updateUserPassword(user);
            UserUtils.clearCache(userOrganization);
        }
    }

    @Transactional(readOnly = false)
    public void updatePassword(List<UserOrganization> entities) {
        for (UserOrganization user : entities) {
            this.updatePassword(user);
        }
    }

    @Transactional(readOnly = false)
    public void alterOrganization(List<UserOrganization> userOrganizations) {
        for (UserOrganization userOrganization : userOrganizations) {
            this.alterOrganization(userOrganization);
        }
    }

    @Transactional(readOnly = false)
    public void alterOrganization(UserOrganization userOrganization) {
        //原用户机构信息
        UserOrganization dbUserOrganization = dao.getUserOrganizationById(userOrganization.getOldId());
        if (null == dbUserOrganization) {
            throw new ServiceException("机构调动失败, 获取用户机构信息时出错!");
        }

        Integer dbStatus = dbUserOrganization.getStatus();
        //用户是否已被调离该机构
        if (dbStatus.equals(UserOrganization.STATUS_LEAVE)) {
            throw new ServiceException("机构调动失败, 该用户已被调离当前机构!");
        }

        //用户在当前机构是揽储身份
        if (dbStatus.equals(UserOrganization.STATUS_LANCHU)) {
            throw new ServiceException("机构调动失败, 该用户在当前机构是揽储状态,不允许执行机构调动操作!");
        }

        //用户是否已被注销
        if (dbStatus.equals(UserOrganization.STATUS_RESIGN)
                || dbStatus.equals(UserOrganization.STATUS_DISMISS)
                || dbStatus.equals(UserOrganization.STATUS_RETIRE)) {
            throw new ServiceException("机构调动失败, 该用户已被注销!");
        }

        //调入机构与当前机构为同一机构
        if (dbUserOrganization.getOrganization().getId().equals(userOrganization.getOrganization().getId())) {
            throw new ServiceException("机构调动失败, 该用户当前机构与调入机构为同一机构!");
        }

        //调入机构
        Date startDate = new Date();
        Date endDate = DateUtils.addDays(startDate, -1);

        if (userOrganization.getOperationType().equals(1)) {
            userOrganization.setStartDate(startDate);   //设置调入日期
            Integer intoStatus = userOrganization.getStatus();
            if (intoStatus.equals(UserOrganization.STATUS_INOFFICE)) { //调离原机构调入新机构
                //1: 同一天用户有没有发生多次调动操作的情况
                UserOrganization filter = new UserOrganization();
                filter.getUser().setId(userOrganization.getUser().getId());
                filter.setStartDate(startDate);
                List<UserOrganization> startDateUoList = dao.findUserOrganizationList(filter);

                filter.setStartDate(null);
                filter.setEndDate(endDate);
                List<UserOrganization> endDateUoList = dao.findUserOrganizationList(filter);

                if (startDateUoList.size() > 2 || endDateUoList.size() > 2) {
                    //同一开始日期最多只能在两个机构,一个在职, 一个揽储
                    throw new ServiceException("机构调动失败, 后台数据出现不一致情况, 请联系管理员手工处理!");
                }

                UserOrganization uoInoffice = null;
                UserOrganization uoLanChu = null;
                for (UserOrganization uo : startDateUoList) {
                    if (uo.getStatus().equals(UserOrganization.STATUS_LANCHU)) {
                        uoLanChu = uo;
                    } else {
                        uoInoffice = uo;
                    }
                }

                if (null != uoLanChu && userOrganization.getOrganization().getId().equals(uoLanChu.getOrganization().getId())) {
                    //如果同一天以揽储方式已调入该机构,则将揽储方式的信息更新
                    uoLanChu.setStatus(intoStatus);
                    uoLanChu.setRemarks(userOrganization.getRemarks());
                    uoLanChu.setEndDate(null);
                    uoLanChu.preUpdate();
                    dao.update(uoLanChu);
                    if (null != uoInoffice) {
                        //删除同一天调入的另一家的机构信息
                        dao.realDelete(uoInoffice);
                    }
                    this.clearCache(uoLanChu);
                    return;

                } else if (null != uoInoffice){ //同一天已调入过其他机构
                    UserOrganization uoEndInoffice = null; //同一天调离的那条机构信息
                    UserOrganization uoEndLanChu = null;   //同一天结束揽储的那条机构信息
                    for (UserOrganization uo : endDateUoList) {
                        if (uo.getStatus().equals(UserOrganization.STATUS_LANCHU)) {
                            uoEndLanChu = uo;
                        } else {
                            uoEndInoffice = uo;
                        }
                    }

                    if (uoEndInoffice != null && uoEndInoffice.getOrganization().getId().equals(userOrganization.getOrganization().getId())) {
                        //调入的机构与当天调离的机构相同(同一天调离又调入的情况, 实际上该用户未做调动)
                        uoEndInoffice.setEndDate(null);
                        uoEndInoffice.setStatus(intoStatus);
                        uoEndInoffice.preUpdate();
                        dao.update(uoEndInoffice);

                        if (null != uoEndLanChu && uoEndLanChu.getOrganization().getId().equals(uoInoffice.getOrganization().getId())) {
                            //如果同一天调入该机构时,结束了用户在该机构的揽储状态, 调回原机构时,则将其揽储状态还原
                            uoEndLanChu.setEndDate(null);
                            uoEndLanChu.preUpdate();
                            dao.update(uoEndLanChu);
                        }
                        //删除同一天调入其他机构的信息
                        dao.realDelete(uoInoffice);
                        this.clearCache(uoInoffice);
                        return;
                    }

                    if (null != uoEndLanChu && uoEndLanChu.getOrganization().getId().equals(uoInoffice.getOrganization().getId())) {
                        //以前以揽储方式调入,同一天正式调入又调出, 调离时将揽储状态还原
                        uoEndLanChu.setEndDate(null);
                        uoEndLanChu.preUpdate();
                        dao.update(uoEndLanChu);
                    }

                    //检查用户之前有没有以揽储身份调入该机构,并结束其揽储状态
                    updateLanChuUserOrganization(userOrganization, endDate);

                    //将同一天调入其他机构的信息更新为新调入机构的信息
                    uoInoffice.getOrganization().setId(userOrganization.getOrganization().getId());
                    uoInoffice.setStatus(intoStatus);
                    uoInoffice.setRemarks(userOrganization.getRemarks());
                    uoInoffice.setEndDate(null);
                    uoInoffice.preUpdate();
                    dao.update(uoInoffice);
                    this.clearCache(uoInoffice);
                    return;
                }

                //以下是正常情况的处理
                //2: 查找用户之前有没有以揽储身份调入该机构
                updateLanChuUserOrganization(userOrganization, endDate);

                //3: 更新原机构信息中的状态
                dbUserOrganization.setEndDate(endDate);
                dbUserOrganization.setStatus(UserOrganization.STATUS_LEAVE);
                dbUserOrganization.preUpdate();
                dao.update(dbUserOrganization);

                //4: 调入新机构
                userOrganization.preInsert();
                dao.insert(userOrganization);

                this.clearCache(userOrganization);

            } else if (intoStatus.equals(UserOrganization.STATUS_LANCHU)) { //以揽储身份调入机构
                //1: 判断机构类型
                Organization organization = organizationDao.get(userOrganization.getOrganization().getId());
                if (null == organization) {
                    throw new ServiceException("调入的机构不存在, 可能已被删除!");
                }
                if (!(organization.getType().equals(Organization.TYPE_SALES_DEPARTMENT)
                        || organization.getType().equals(Organization.TYPE_NODE_BRANCH)
                        || organization.getType().equals(Organization.TYPE_NODE_SUB_BRANCH))) {
                    throw new ServiceException("机构调动失败, 以揽储方式调入其他机构, 调入机构的类型只能是: 营业部、支行或分理处!");
                }

                //2: 查找用户之前有没有调入过该机构
                UserOrganization uo = new UserOrganization();
                uo.getUser().setId(userOrganization.getUser().getId());
                uo.getOrganization().setId(userOrganization.getOrganization().getId());
                List<UserOrganization> uoList = dao.findUserOrganizationList(uo);
                if (uoList.size() > 0) {
                    throw new ServiceException("机构调动失败, 用户在该机构已存在相应信息, 不允许以揽储方式调入!");
                }

                //3: 以揽储方式调入机构
                userOrganization.preInsert();
                dao.insert(userOrganization);
                this.clearCache(userOrganization);
            }
        } else if (userOrganization.getOperationType().equals(2)) { //调离注销
            Integer leaveStatus = userOrganization.getStatus();

            String dbRemarks = dbUserOrganization.getRemarks();
            String remakrs = userOrganization.getRemarks();
            String newRemarks = "";
            if (StringUtils.isNotBlank(dbRemarks)) {
                newRemarks = dbRemarks;
            }
            if (StringUtils.isNotBlank(remakrs)) {
                newRemarks += "; " + remakrs;
            }
            if (StringUtils.isNotBlank(newRemarks)) {
                dbUserOrganization.setRemarks(newRemarks);
            }
            dbUserOrganization.setStatus(leaveStatus);
            dbUserOrganization.setEndDate(endDate);
            dbUserOrganization.preUpdate();
            dao.update(dbUserOrganization);
            this.clearCache(userOrganization);
        } else {
            throw new ServiceException("机构调动失败, 错误的机构调动方式!");
        }
    }


    @Transactional(readOnly = false)
    public void alterRole(UserOrganization userOrganization) {
        if (userOrganization == null || userOrganization.getUser() == null) {
            return;
        }
        User user = userOrganization.getUser();
        if (null != user.getId() && !user.getId().equals(0)) {
            dao.deleteUserRole(user);
            if (null != user.getRoleList() && user.getRoleList().size() > 0) {
                dao.insertUserRole(user);
            }
            UserUtils.clearCache(userOrganization);
        }
    }

    @Transactional(readOnly = false)
    public void alterRole(List<UserOrganization> userOrganizationList) {
        for (UserOrganization userOrganization : userOrganizationList) {
            this.alterRole(userOrganization);
        }
    }


    ////////////////////////////////////////////////////////
    /**
     * 检查用户有没有以揽储方式调入该机构, 如果有并且揽储没有结束,
     * 则将其揽储的结束时间,设为正式调入该机构的前一天。
     * @param userOrganization
     */
    private void updateLanChuUserOrganization(UserOrganization userOrganization, Date endDate) {
        UserOrganization lanChuFilter = new UserOrganization();
        lanChuFilter.getUser().setId(userOrganization.getUser().getId());
        lanChuFilter.getOrganization().setId(userOrganization.getOrganization().getId());
        lanChuFilter.setStatus(UserOrganization.STATUS_LANCHU);
        List<UserOrganization> lanChuUoList = dao.findUserOrganizationList(lanChuFilter);
        if (lanChuUoList.size() > 1) {
            throw new ServiceException("机构调动失败, 后台数据出现不一致情况, 请联系管理员手工处理!");
        }
        for (UserOrganization lanChuUo : lanChuUoList) {
            //之前以揽储方式调入过该机构, 则将endData设为调入日期的前一天
            if (null == lanChuUo.getEndDate()) {
                lanChuUo.setEndDate(endDate);
                lanChuUo.preUpdate();
                dao.update(lanChuUo);
            }
        }
    }

    /**
     * 当机构发生变动时,清除用户缓存
     * @param userOrganization
     */
    private void clearCache(UserOrganization userOrganization) {
        UserUtils.clearCache(userOrganization);
    }
}


