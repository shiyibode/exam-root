package com.ynsh.model;

import com.ynsh.util.Page;

/**
 * Created by syb on 2017/9/20.
 */
public class Client extends BaseModel{

    private String openId;      //客户唯一标识

    private String country;     //国家

    private String province;    //省份

    private String city;        //城市

    private Integer gender;     //性别

    private String nickName;    //昵称

    private Page page;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
