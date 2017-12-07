package com.ynsh.service.impl;

import com.ynsh.dao.UnitDao;
import com.ynsh.model.MainPageGroupItem;
import com.ynsh.model.Unit;
import com.ynsh.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by syb on 2017/9/20.
 */
@Service
@Transactional(readOnly = true)
public class UnitServiceImpl implements UnitService{
    @Autowired
    private UnitDao unitDao;

    @Override
    public Unit selectById(Long id) {
        return unitDao.selectById(id);
    }

    @Override
    public List<Unit> getMainPageGroupItemUnitsList(MainPageGroupItem mainPageGroupItem) {
        return unitDao.getMainPageGroupItemUnitsList(mainPageGroupItem);
    }
}
