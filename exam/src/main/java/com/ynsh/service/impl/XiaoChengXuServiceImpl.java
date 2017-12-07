package com.ynsh.service.impl;

import com.ynsh.controller.XiaoChengXuController;
import com.ynsh.model.*;
import com.ynsh.dao.XiaoChengXuDao;
import com.ynsh.service.XiaoChengXuService;
import com.ynsh.util.Constant;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syb on 2017/9/5.
 */
@Service
@Transactional(readOnly = true)
public class XiaoChengXuServiceImpl implements XiaoChengXuService{
    private static Logger logger = LoggerFactory.getLogger(XiaoChengXuController.class);
    @Autowired
    private XiaoChengXuDao xiaoChengXuDao;

   public List<MainPageGroup> getMainPageGroups() throws Exception{
       List<MainPageGroup> mainPageGroups = new ArrayList<>();
       mainPageGroups = xiaoChengXuDao.getMainPageGroups();
       return mainPageGroups;
   }

   public List<MainPageGroupItem> getMainPageGroupItemByGroupId(Long id) throws Exception{
       List<MainPageGroupItem> mainPageGroupItems = new ArrayList<>();
       mainPageGroupItems = xiaoChengXuDao.getItemsByGroupId(id);
       return mainPageGroupItems;
   }

   public List<MainPageGroup> getAllGroups() throws Exception{
       List<MainPageGroup> mainPageGroups = new ArrayList<>();

       mainPageGroups = getMainPageGroups();
       for (int i=0;i<mainPageGroups.size();i++){
           List<MainPageGroupItem> mainPageGroupItems = getMainPageGroupItemByGroupId(mainPageGroups.get(i).getId());

           List<List<MainPageGroupItem>> items = processData(mainPageGroupItems);
           mainPageGroups.get(i).setMainPageGroupItemList(items);
       }
       return mainPageGroups;
   }

   List<List<MainPageGroupItem>> processData(List<MainPageGroupItem> originalMainPageGroupItems){
       List<List<MainPageGroupItem>> pageItems = new ArrayList<>();
       List<MainPageGroupItem> pairMainPageGroupItems = new ArrayList<>();

       int len = originalMainPageGroupItems.size();
       len = (int)(len+1)/2;
       len = len * 2;

       for (int i=0;i<len;i++){
           if ((i+1)%2 == 0){
               if (i<originalMainPageGroupItems.size()) pairMainPageGroupItems.add(originalMainPageGroupItems.get(i));
               pageItems.add(pairMainPageGroupItems);
               pairMainPageGroupItems = new ArrayList<>();
           }else {
               pairMainPageGroupItems.add(originalMainPageGroupItems.get(i));
           }
       }

        return pageItems;
   }

    @Override
    public String getOpenIdByCode(String code) throws Exception {
        String url = Constant.OPEN_ID_URL.replace("APPID",Constant.AppId).replace("SECRET",Constant.AppSecret).replace("JSCODE",code);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        String openId = null;

        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();

        if(entity != null){
            String result = EntityUtils.toString(entity,"UTF-8");
            JSONObject jsonObject = JSONObject.fromObject(result);
            openId = jsonObject.getString("openid");
        }

        return openId;
    }

    @Override
    public ZanedUnit getZanedClient(ZanedUnit zanedUnit) throws Exception {
        return xiaoChengXuDao.getZanedClient(zanedUnit);
    }

    @Override
    public FocusedUnit getFocusedClient(FocusedUnit focusedUnit) throws Exception {
        return xiaoChengXuDao.getFocusedClient(focusedUnit);
    }
}
