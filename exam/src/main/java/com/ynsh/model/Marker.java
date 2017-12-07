package com.ynsh.model;

/**
 * Created by syb on 2017/9/19.
 * 地图上的标记类
 */
public class Marker extends BaseModel{

    private Long locationId;    //所属的地址id

    private String iconPath;    //图标位置

    private Float longitude;    //图标所在经度

    private Float latitude;     //图标所在纬度

    private Float width;        //图标的宽

    private Float height;       //图标的高

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
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

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }
}
