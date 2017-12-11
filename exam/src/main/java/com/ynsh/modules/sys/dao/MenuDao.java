package com.ynsh.modules.sys.dao;

import com.ynsh.common.core.annotation.MyBatisDao;
import com.ynsh.common.core.persistence.dao.TreeDao;
import com.ynsh.modules.sys.entity.Menu;

import java.util.List;

/**
 * 菜单DAO接口
 */
@MyBatisDao
public interface MenuDao extends TreeDao<Menu, Long> {

    public List<Menu> findByUserId(Menu menu);


    public int updateSort(Menu menu);
}
