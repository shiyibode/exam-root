package com.ynsh.service.impl;

import com.ynsh.dao.HotUnitDao;
import com.ynsh.model.SearchUtil;
import com.ynsh.model.Unit;
import com.ynsh.service.HotUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by syb on 2017/9/30.
 */
@Service
@Transactional(readOnly = true)
public class HotServiceImpl implements HotUnitService{

    @Autowired
    private HotUnitDao hotUnitDao;

    @Override
    public List<Unit> getTop3HotPerson(Unit unit) throws Exception {
        return hotUnitDao.getTop3HotPerson(unit);
    }

    @Override
    public List<Unit> getTop3HotShop(Unit unit) throws Exception {
        return hotUnitDao.getTop3HotShop(unit);
    }

    @Override
    public List<Unit> getTop3RecentOnline(Unit unit) throws Exception {
        return hotUnitDao.getTop3RecentOnline(unit);
    }

    @Override
    public List<Unit> getMoreHotPerson(Unit unit) throws Exception {
        return hotUnitDao.getMoreHotPerson(unit);
    }

    @Override
    public List<Unit> getMoreHotShop(Unit unit) throws Exception {
        return hotUnitDao.getMoreHotShop(unit);
    }

    @Override
    public List<Unit> getMoreRecentOnline(Unit unit) throws Exception {
        return hotUnitDao.getMoreRecentOnline(unit);
    }

    @Override
    public List<Unit> getSearchResultList(SearchUtil searchUtil) throws Exception {
        return hotUnitDao.getSearchResultList(searchUtil);
    }
}
