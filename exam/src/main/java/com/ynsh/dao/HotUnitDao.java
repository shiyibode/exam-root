package com.ynsh.dao;

import com.ynsh.model.SearchUtil;
import com.ynsh.model.Unit;

import java.util.List;

/**
 * Created by syb on 2017/9/30.
 */
public interface HotUnitDao {

    public List<Unit> getTop3HotPerson(Unit unit);

    public List<Unit> getTop3HotShop(Unit unit);

    public List<Unit> getTop3RecentOnline(Unit unit);

    public List<Unit> getMoreHotPerson(Unit unit);

    public List<Unit> getMoreHotShop(Unit unit);

    public List<Unit> getMoreRecentOnline(Unit unit);

    public List<Unit> getSearchResultList(SearchUtil searchUtil);

}
