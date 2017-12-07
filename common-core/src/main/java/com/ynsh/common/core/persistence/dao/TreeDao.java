package com.ynsh.common.core.persistence.dao;

import com.ynsh.common.core.persistence.entity.TreeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * DAO支持类实现
 * @param <T>
 */
public interface TreeDao<T extends TreeEntity<T, PK>, PK extends Serializable> extends CrudDao<T, PK> {

	/**
	 * 找到所有子节点
	 * @param entity
	 * @return
	 */
	public List<T> findByParentId(T entity);

	/**
	 * 找到所有子节点
	 * @param entity
	 * @return
	 */
	public List<T> findByParentIdsLike(T entity);

	/**
	 * 更新所有父节点字段
	 * @param entity
	 * @return
	 */
	public int updateParentIds(T entity);
	
}