package com.ynsh.model;

import java.util.List;

/**
 * Created by syb on 2017/9/30.
 */
public class HotUnit extends BaseModel{

    private String categoryTitle;

    private List<Unit> units;

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }
}
