package com.ynsh.modules.sys.service;

import com.ynsh.common.core.service.TreeService;
import com.ynsh.modules.sys.dao.OrganizationDao;
import com.ynsh.modules.sys.entity.Organization;
import com.ynsh.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机构Service
 */
@Service
@Transactional(readOnly = true)
public class OrganizationService extends TreeService<OrganizationDao, Organization, Long> {

    @Override
    @Transactional(readOnly = false)
    public void save(Organization entity) {
        super.save(entity);
        UserUtils.removeCache(UserUtils.CACHE_ORGANIZATION_LIST);
        UserUtils.removeCache(UserUtils.CACHE_ORGANIZATION_TREE);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(List<Organization> entities) {
        super.save(entities);
        UserUtils.removeCache(UserUtils.CACHE_ORGANIZATION_LIST);
        UserUtils.removeCache(UserUtils.CACHE_ORGANIZATION_TREE);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Organization entity) {
        super.delete(entity);
        UserUtils.removeCache(UserUtils.CACHE_ORGANIZATION_LIST);
        UserUtils.removeCache(UserUtils.CACHE_ORGANIZATION_TREE);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(List<Organization> entities) {
        super.delete(entities);
        UserUtils.removeCache(UserUtils.CACHE_ORGANIZATION_LIST);
        UserUtils.removeCache(UserUtils.CACHE_ORGANIZATION_TREE);
    }

    @Override
    public List<Organization> findAsTree(Organization organization) {
        List<Organization> organizationTree = UserUtils.getOrganizationTree();
        return organizationTree;
    }
}
