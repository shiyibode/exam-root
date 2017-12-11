package com.ynsh.modules.sys.dao;

import com.ynsh.common.core.annotation.MyBatisDao;
import com.ynsh.common.core.persistence.dao.TreeDao;
import com.ynsh.modules.sys.entity.Organization;


/**
 * 机构DAO接口
 */
@MyBatisDao
public interface OrganizationDao extends TreeDao<Organization, Long> {


}
