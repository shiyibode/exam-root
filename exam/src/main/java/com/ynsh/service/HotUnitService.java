package com.ynsh.service;

import com.ynsh.model.SearchUtil;
import com.ynsh.model.Unit;

import java.util.List;

/**
 * Created by syb on 2017/9/30.
 */
public interface HotUnitService {

    public List<Unit> getTop3HotPerson(Unit unit) throws Exception;

    public List<Unit> getTop3HotShop(Unit unit) throws Exception;

    public List<Unit> getTop3RecentOnline(Unit unit) throws Exception;

    public List<Unit> getMoreHotPerson(Unit unit) throws Exception;

    public List<Unit> getMoreHotShop(Unit unit) throws Exception;

    public List<Unit> getMoreRecentOnline(Unit unit) throws Exception;

    public List<Unit> getSearchResultList(SearchUtil searchUtil) throws Exception;

}
