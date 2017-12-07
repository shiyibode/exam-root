package com.ynsh.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * Created by syb on 2017/9/19.
 */
public class Location extends BaseModel{

    private Long unitId;            //所属个体的id，如店铺、个人

    private String address;         //地址描述，用文字表述的地址

    private Float longitude;        //中心经度

    private Float latitude;         //中心纬度

    private Integer scale;          //缩放层级，值为5-18

    private List<Marker> markers;   //标记，多数情况下只有1个

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }
}
