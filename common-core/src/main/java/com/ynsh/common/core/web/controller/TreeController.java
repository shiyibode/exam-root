package com.ynsh.common.core.web.controller;

import com.ynsh.common.core.persistence.entity.BaseEntity;
import com.ynsh.common.core.persistence.entity.TreeEntity;
import com.ynsh.common.core.service.TreeService;
import com.ynsh.common.core.web.client.RequestJson;
import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.utils.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * Created by chenjianjun on 16/8/4.
 */
public abstract class TreeController<S extends TreeService, T extends TreeEntity, PK extends Serializable> extends CrudController<S, T, PK> {

    private static final Logger logger = LoggerFactory.getLogger(TreeController.class);

    @Override
    public @ResponseBody Object get(HttpServletRequest request, @RequestBody RequestJson<T, PK> requestJson) {
        ResponseJson responseJson = new ResponseJson();

        long total = 0L;
        PK id = requestJson.getId();
        if (null != id && Long.valueOf(id.toString()).longValue() > 0) {
            BaseEntity model = service.get(id);
            responseJson.setData(model);
        } else {
            T entity = requestJson.getFilter();
            if (null == entity) {
                Class<T> entityClass = Reflections.getClassGenricType(getClass(), 1);
                try {
                    entity = entityClass.getConstructor().newInstance();
                } catch (Exception ex) {}
            }
            List<BaseEntity> tree = service.findAsTree(entity);
            responseJson.setChildren(tree);
            total = tree.size();
        }
        responseJson.setSuccess(true);
        responseJson.setMsg("获取数据成功!");
        responseJson.setTotal(total);

        return responseJson;
    }
}
