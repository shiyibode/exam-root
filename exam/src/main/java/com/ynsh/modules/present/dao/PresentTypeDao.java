package com.ynsh.modules.present.dao;

import com.ynsh.common.core.annotation.MyBatisDao;
import com.ynsh.common.core.persistence.dao.CrudDao;
import com.ynsh.modules.present.entity.PresentType;

import java.util.List;

/**
 * Created by syb on 18-1-22.
 */
@MyBatisDao
public interface PresentTypeDao extends CrudDao<PresentType,Long> {

    List<PresentType> getValidPresents(PresentType presentType);

}
