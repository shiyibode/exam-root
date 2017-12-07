package com.ynsh.common.core.persistence;

import com.ynsh.common.utils.StringUtils;
import org.apache.commons.lang3.CharUtils;

/**
 * 数据库排序器
 */
public class Sorter implements java.io.Serializable {
    private String property;

    private String direction;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = this.propertyToField(property);
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    private String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
