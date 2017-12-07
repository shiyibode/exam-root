package com.ynsh.service;

import com.ynsh.model.*;

import java.util.List;

/**
 * Created by syb on 2017/9/5.
 */
public interface XiaoChengXuService {

    public List<MainPageGroup> getMainPageGroups() throws Exception;

    public List<MainPageGroupItem> getMainPageGroupItemByGroupId(Long id) throws Exception;

    public List<MainPageGroup> getAllGroups() throws Exception;

    public String getOpenIdByCode(String code) throws Exception;

    public ZanedUnit getZanedClient(ZanedUnit zanedUnit) throws Exception;

    public FocusedUnit getFocusedClient(FocusedUnit focusedUnit) throws Exception;

}
