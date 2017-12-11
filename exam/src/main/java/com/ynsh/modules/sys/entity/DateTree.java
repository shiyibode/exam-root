package com.ynsh.modules.sys.entity;

import com.ynsh.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjianjun on 2016/10/24.
 */
public class DateTree implements java.io.Serializable {
    private String name;

    private String type;

    private Long count;

    private List<DateTree> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<DateTree> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<DateTree> children) {
        this.children = children;
    }

    public Boolean isLeaf() {
        return this.getChildren().size() == 0 ? true : false;
    }

    public Boolean getExpanded() {
        return this.getChildren().size() > 0;
    }

    public static DateTree listToTree(List<DateTree> dateTreeList) {
        DateTree dateTree = findRoot(dateTreeList);
        if (dateTree != null) {
            findChildren(dateTree, dateTreeList, "Y");
        }

        return dateTree;
    }

    private static DateTree findRoot(List<DateTree> dateTreeList) {
        for (DateTree dateTree : dateTreeList) {
            if (dateTree.getType().equals("root")) {
                return dateTree;
            }
        }
        return null;
    }

    private static void findChildren(DateTree root, List<DateTree> dateTreeList, String type) {
        for (DateTree dateTree : dateTreeList) {
            if (dateTree.getType().equals(type)) {
                if (type.equals("Y")) {
                    root.getChildren().add(dateTree);
                    findChildren(dateTree, dateTreeList, "YM");
                } else if (type.equals("YM")) {
                    if (StringUtils.contains(dateTree.getName(), root.getName())) {
                        root.getChildren().add(dateTree);
                        findChildren(dateTree, dateTreeList, "YMD");
                    }
                } else if (type.equals("YMD")) {
                    if (StringUtils.contains(dateTree.getName(), root.getName())) {
                        root.getChildren().add(dateTree);
                    }
                }
            }
        }

    }
}
