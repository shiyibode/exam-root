package com.ynsh.common.core.service;

import com.ynsh.common.core.persistence.dao.TreeDao;
import com.ynsh.common.core.persistence.entity.TreeEntity;
import com.ynsh.common.core.persistence.entity.utils.TreeEntityUtils;
import com.ynsh.common.utils.Reflections;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Service基类
 */
@Transactional(readOnly = true)
public abstract class TreeService<D extends TreeDao<T, PK>, T extends TreeEntity<T, PK>, PK extends Serializable> extends CrudService<D, T, PK> {

    @Transactional(readOnly = false)
    public void save(T entity) {

        @SuppressWarnings("unchecked")
        Class<T> entityClass = Reflections.getClassGenricType(getClass(), 1);

        //如果是新增加节点并且未设置上级节点,则上级节点设为根节点
        if (entity.getIsNewRecord() && entity.getParentId() == null) {
            entity.setParentId(entity.getRootId());
        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = null;
        if (entity.getParentId() != null) {
            if (entity.getIsNewRecord()) {
                oldParentIds = null;
            } else {
                T oldEntity = super.get(entity.getId());
                oldParentIds = oldEntity.getParentIds();
            }

            //设置entity新的parentIds
            if (entity.getParentId().equals(entity.getRootId())) {
                entity.setParentIds(entity.getParentId().toString());
            } else {
                T parent = super.get(entity.getParentId());
                entity.setParentIds(parent.getParentIds() + "," + parent.getId().toString());
            }
        }

        // 保存或更新实体
        super.save(entity);

        //更新子节点的 parentIds
        if (oldParentIds != null && entity.getParentId() != null) {
            // 更新parentIds 子节点的 parentIds
            T o = null;
            try {
                o = entityClass.newInstance();
            } catch (Exception e) {
                throw new ServiceException(e);
            }
            //获取所有子节点,更新子节点的parentIds
            o.setParentIds(entity.getId().toString());
            List<T> list = dao.findByParentIdsLike(o);
            for (T e : list) {
                if (e.getParentIds() != null && oldParentIds != null) {
                    e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
                    preUpdateChild(entity, e);
                    dao.updateParentIds(e);
                }
            }
        }
    }

    public List<T> findAsTree(T t) {
        List<T> entities = dao.findList(t);
        return TreeEntityUtils.listToTree((List)entities);
    }


    /**
     * 预留接口，用户更新子节前调用
     *
     * @param childEntity
     */
    protected void preUpdateChild(T entity, T childEntity) {

    }

}
