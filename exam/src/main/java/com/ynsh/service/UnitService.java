package com.ynsh.service;

import com.ynsh.model.MainPageGroupItem;
import com.ynsh.model.Unit;

import java.util.List;

/**
 * Created by syb on 2017/9/20.
 */
public interface UnitService {

    Unit selectById(Long id);

    List<Unit> getMainPageGroupItemUnitsList(MainPageGroupItem mainPageGroupItem);

}
