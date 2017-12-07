package com.ynsh.service.impl;

import com.ynsh.dao.ZanFocusDao;
import com.ynsh.model.Client;
import com.ynsh.model.FocusedUnit;
import com.ynsh.model.Unit;
import com.ynsh.model.ZanedUnit;
import com.ynsh.service.ZanFocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by syb on 2017/10/14.
 */
@Service
@Transactional(readOnly = true)
public class ZanFocusServiceImpl implements ZanFocusService{

    @Autowired
    private ZanFocusDao zanFocusDao;

    @Override
    @Transactional(readOnly = false)
    public void zanUnit(ZanedUnit zanedUnit) {
        zanFocusDao.zanUnit(zanedUnit);
    }

    @Override
    @Transactional(readOnly = false)
    public void reZanUnit(ZanedUnit zanedUnit) {
        zanFocusDao.reZanUnit(zanedUnit);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteZanUnit(ZanedUnit zanedUnit) {
        zanFocusDao.deleteZanUnit(zanedUnit);
    }

    @Override
    @Transactional(readOnly = false)
    public void focusUnit(FocusedUnit focusedUnit) {
        zanFocusDao.focusUnit(focusedUnit);
    }

    @Override
    @Transactional(readOnly = false)
    public void reFocusUnit(FocusedUnit focusedUnit) {
        zanFocusDao.reFocusUnit(focusedUnit);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFocusUnit(FocusedUnit focusedUnit) {
        zanFocusDao.deleteFocusUnit(focusedUnit);
    }

    @Override
    public ZanedUnit getZanedUnit(ZanedUnit zanedUnit) {
        return zanFocusDao.getZanedUnit(zanedUnit);
    }

    @Override
    public FocusedUnit getFocusedUnit(FocusedUnit focusedUnit) {
        return zanFocusDao.getFocusedUnit(focusedUnit);
    }

    @Override
    public List<Unit> getSingleClientZanedUnits(Client client) {
        return zanFocusDao.getSingleClientZanedUnits(client);
    }

    @Override
    public List<Unit> getSingleClientFocusedUnits(Client client) {
        return zanFocusDao.getSingleClientFocusedUnits(client);
    }

    @Override
    @Transactional(readOnly = false)
    public void addZanedTimes(ZanedUnit zanedUnit) {
        zanFocusDao.addZanedTimes(zanedUnit);
    }

    @Override
    @Transactional(readOnly = false)
    public void reduceZanedTimes(ZanedUnit zanedUnit) {
        zanFocusDao.reduceZanedTimes(zanedUnit);
    }

    @Override
    @Transactional(readOnly = false)
    public void addFocusedTimes(FocusedUnit focusedUnit) {
        zanFocusDao.addFocusedTimes(focusedUnit);
    }

    @Override
    @Transactional(readOnly = false)
    public void reduceFocusedTimes(FocusedUnit focusedUnit) {
        zanFocusDao.reduceFocusedTimes(focusedUnit);
    }
}
