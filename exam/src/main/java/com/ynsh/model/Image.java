package com.ynsh.model;

/**
 * Created by syb on 2017/9/19.
 */
public class Image extends BaseModel{

    private String description;    //图片描述

    private String imagePath;   //图片路径

    private Float width;        //像素宽度

    private Float height;       //像素高度

    private Integer type;       //类型  1-网络  2-本地

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
