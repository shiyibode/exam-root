package com.ynsh.common.core.persistence.entity.utils;

import com.ynsh.common.core.persistence.entity.TreeEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjianjun on 2016/10/23.
 */
public class TreeEntityUtils {
    public static List<TreeEntity> listToTree(List<TreeEntity> treeEntityList) {
        List<TreeEntity> roots = findRoots(treeEntityList);
        List<TreeEntity> notRoots = (List<TreeEntity>) CollectionUtils.subtract(treeEntityList, roots);
        for (TreeEntity root : roots) {
            root.setChildren(findChildren(root, notRoots));
        }
        return roots;
    }

    private static List<TreeEntity> findRoots(List<TreeEntity> treeEntityList) {
        List<TreeEntity> results = new ArrayList<>();
        for (TreeEntity treeEntity : treeEntityList) {
            boolean isRoot = true;
            for (TreeEntity comparedOne : treeEntityList) {
                if (treeEntity.getParentId().equals(comparedOne.getId())) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                results.add(treeEntity);
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private static List<TreeEntity> findChildren(TreeEntity root, List<TreeEntity> treeEntityList) {
        List<TreeEntity> children = new ArrayList<>();

        for (TreeEntity comparedOne : treeEntityList) {
            if (comparedOne.getParentId().equals(root.getId())) {
                children.add(comparedOne);
            }
        }
        List<TreeEntity> notChildren = (List<TreeEntity>) CollectionUtils.subtract(treeEntityList, children);
        for (TreeEntity child : children) {
            //递归找孩子
            List<TreeEntity> tmpChildren = findChildren(child, notChildren);
            child.setChildren(tmpChildren);
        }
        return children;
    }

    //private List<TreeEntity> getLeafChildren(List<TreeEntity> resultList, List<TreeEntity> children){
    //    for(TreeEntity treeEntity : children){
    //        if(treeEntity.isLeaf()){
    //            resultList.add(treeEntity);
    //        }else{
    //            getLeafChildren(resultList, treeEntity.getChildren());
    //        }
    //    }
    //    return resultList;
    //}
}
