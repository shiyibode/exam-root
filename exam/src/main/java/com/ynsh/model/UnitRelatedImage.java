package com.ynsh.model;

/**
 * Created by syb on 2017/9/19.
 */
public class UnitRelatedImage extends Image{

    private long unitId;    //所属的单个个体的id，如属于一个人，或属于一个店铺

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }
}
