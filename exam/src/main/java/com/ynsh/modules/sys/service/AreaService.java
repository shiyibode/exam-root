package com.ynsh.modules.sys.service;

import com.ynsh.common.core.persistence.entity.utils.TreeEntityUtils;
import com.ynsh.common.core.service.TreeService;
import com.ynsh.modules.sys.dao.AreaDao;
import com.ynsh.modules.sys.entity.Area;
import com.ynsh.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域Service
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area, Long> {

    @Override
	public List<Area> findAsTree(Area area) {
		List<Area> areaList = UserUtils.getAreaList();
		return TreeEntityUtils.listToTree((List)areaList);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	@Override
    @Transactional(readOnly = false)
	public void save(List<Area> entities) {
		super.save(entities);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	@Override
    @Transactional(readOnly = false)
	public void delete(List<Area> entities) {
		super.delete(entities);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
}
