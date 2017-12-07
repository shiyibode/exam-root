package com.ynsh.dao;

import com.ynsh.model.MainPageGroupItem;
import com.ynsh.model.Unit;

import java.util.List;

/**
 * Created by syb on 2017/9/20.
 */
public interface UnitDao {

    Unit selectById(Long id);

    List<Unit> getMainPageGroupItemUnitsList(MainPageGroupItem mainPageGroupItem);

}
