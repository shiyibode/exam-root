package com.ynsh.common.core.service;

import com.ynsh.common.core.persistence.Page;
import com.ynsh.common.core.persistence.dao.CrudDao;
import com.ynsh.common.core.persistence.entity.DataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Service基类
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T, PK>, T extends DataEntity<T, PK>, PK extends Serializable> extends BaseService {

	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param primaryKey
	 * @return
	 */
	public T get(PK primaryKey) {
		return dao.get(primaryKey);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}

	/**
	 * 批量保存数据（插入或更新）
	 * @param entities
	 */
	@Transactional(readOnly = false)
	public void save(List<T> entities) {
		for (T t : entities) {
			this.save(t);
		}
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

	/**
	 * 批量删除数据
	 * @param entities
	 */
	@Transactional(readOnly = false)
	public void delete(List<T> entities) {
		for (T t : entities) {
			this.delete(t);
		}
	}
}
