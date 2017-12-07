package com.ynsh.controller;

import com.ynsh.model.HotUnit;
import com.ynsh.model.SearchUtil;
import com.ynsh.model.Unit;
import com.ynsh.service.HotUnitService;
import com.ynsh.util.Page;
import com.ynsh.util.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syb on 2017/9/29.
 */
@Controller
@RequestMapping("hotunit")
public class HotUnitController {
    private static Logger logger = LoggerFactory.getLogger(XiaoChengXuController.class);

    @Autowired
    private HotUnitService hotUnitService;

    @RequestMapping("/get.do")
    public @ResponseBody Object getHotUnits(){
        List<HotUnit> data = new ArrayList<>();
        ResponseJson responseJson = new ResponseJson();
        Unit unit = new Unit();
        HotUnit hotPerson = new HotUnit();
        HotUnit hotShop = new HotUnit();
        HotUnit recentOnline = new HotUnit();

        try {
            List<Unit> top3HotPerson = hotUnitService.getTop3HotPerson(unit);
            List<Unit> top3HotShop = hotUnitService.getTop3HotShop(unit);
            List<Unit> top3RecentOnline = hotUnitService.getTop3RecentOnline(unit);

            hotPerson.setCategoryTitle("热门工匠");
            hotPerson.setUnits(top3HotPerson);
            data.add(hotPerson);
            hotShop.setCategoryTitle("热门店铺");
            hotShop.setUnits(top3HotShop);
            data.add(hotShop);
            recentOnline.setCategoryTitle("最新上线");
            recentOnline.setUnits(top3RecentOnline);
            data.add(recentOnline);

            responseJson.setData(data);
            responseJson.setSuccess(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return responseJson;
    }

    @RequestMapping("/morehotperson.do")
    public @ResponseBody Object getMoreHotPerson(@RequestParam("currentpage") int currentPage){
        ResponseJson responseJson = new ResponseJson();
        Unit unit = new Unit();
        Page page = new Page();
        page.setPageFlag(true);
        page.setCurrentPage(currentPage);
        page.setPageSize(9);
        page.setOffset(page.getPageSize()*(currentPage-1));
        unit.setPage(page);

        List<Unit> data = new ArrayList<>();
        try {
            data = hotUnitService.getMoreHotPerson(unit);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        responseJson.setSuccess(true);
        responseJson.setTotal(new Long(data.size()));
        responseJson.setData(data);
        return responseJson;
    }

    @RequestMapping("/morehotshop.do")
    public @ResponseBody Object getMoreHotShop(@RequestParam("currentpage") int currentPage){
        ResponseJson responseJson = new ResponseJson();
        Unit unit = new Unit();
        Page page = new Page();
        page.setPageFlag(true);
        page.setCurrentPage(currentPage);
        page.setPageSize(9);
        page.setOffset(page.getPageSize()*(currentPage-1));
        unit.setPage(page);

        List<Unit> data = new ArrayList<>();
        try {
            data = hotUnitService.getMoreHotShop(unit);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        responseJson.setSuccess(true);
        responseJson.setTotal(new Long(data.size()));
        responseJson.setData(data);
        return responseJson;
    }

    @RequestMapping("/morerecentonline.do")
    public @ResponseBody Object getMoreRecentOnline(@RequestParam("currentpage") int currentPage){
        ResponseJson responseJson = new ResponseJson();
        Unit unit = new Unit();
        Page page = new Page();
        page.setPageFlag(true);
        page.setCurrentPage(currentPage);
        page.setPageSize(9);
        page.setOffset(page.getPageSize()*(currentPage-1));
        unit.setPage(page);

        List<Unit> data = new ArrayList<>();
        try {
            data = hotUnitService.getMoreRecentOnline(unit);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        responseJson.setSuccess(true);
        responseJson.setTotal(new Long(data.size()));
        responseJson.setData(data);
        return responseJson;
    }

    @RequestMapping("/search.do")
    public @ResponseBody Object search(@RequestParam("searchkeyword") String searchKeyWord,@RequestParam("searchpagenum") int searchPageNum){
        ResponseJson responseJson = new ResponseJson();
        List<Unit> units = new ArrayList<>();

        SearchUtil searchUtil =  new SearchUtil();
        searchUtil.setKey(searchKeyWord);
        Page page = new Page();
        page.setPageFlag(true);
        page.setCurrentPage(searchPageNum);
        page.setPageSize(6);
        page.setOffset(page.getPageSize()*(searchPageNum-1));
        searchUtil.setPage(page);

        try {
            units = hotUnitService.getSearchResultList(searchUtil);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        responseJson.setData(units);
        responseJson.setTotal(new Long(units.size()));
        responseJson.setSuccess(true);
        return responseJson;
    }

}
