package com.ynsh.dao;

import com.ynsh.model.FocusedUnit;
import com.ynsh.model.MainPageGroup;
import com.ynsh.model.MainPageGroupItem;
import com.ynsh.model.ZanedUnit;

import java.util.List;

/**
 * Created by syb on 2017/9/5.
 */

public interface XiaoChengXuDao {

    List<MainPageGroup> getMainPageGroups();

    List<MainPageGroupItem> getItemsByGroupId(Long id);

    ZanedUnit getZanedClient(ZanedUnit zanedUnit);

    FocusedUnit getFocusedClient(FocusedUnit focusedUnit);

}
