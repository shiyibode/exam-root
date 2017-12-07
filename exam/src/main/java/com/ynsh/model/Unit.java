package com.ynsh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ynsh.util.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by syb on 2017/9/19.
 */
public class Unit extends BaseModel{

    private String title;               //标题，展现在头部

    private String name;                //名称，公司名称或个人名字

    private Integer identityType;       //证件类型

    private String identityNumber;      //证件号码

    private String coverImage;          //封面图标

    private String birthPlace;          //籍贯，出生地

    private String authenticationStatus;//个人已认证、公司已认证、未认证

    private Integer serviceYears;       //从业年限

    private String serviceArea;         //可服务的地区

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registTime;            //注册时间

    private String serviceContentText;  //服务内容，文字描述

    private String serviceContentVideo; //介绍服务的视频地址

    private String serviceContentVideoPoster; //视频封面

    private String introduction;        //介绍

    private Boolean haveIntroduction;   //是否有介绍

    private Boolean focused;            //当前用户是否已关注该unit

    private Boolean zaned;              //当前用户是否已为该unit点赞

    private List<ContactInfo> contactInfo;//联系信息

    private String mainPhoneNumber;      //主联系信息

    private List<String> tags;          //标签内容

    private String stringTags;          //未分割的标签，分割成list后赋值给tags

    private Integer lookOverTimes;      //被浏览次数

    private Integer focusedTimes;       //被关注次数

    private Integer zanedTimes;         //被点赞次数

    private Boolean haveAddress;        //是否有固定位置，个人非常可能没有位置

    private Location location;          //所在位置，当haveAddress为true时展现

    private Long location_id;           //所在位置id

    private Boolean available;          //是否启用

    private List<Image> serviceRelated; //关联图片

    private Page page;                  //分页信息

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public Integer getServiceYears() {
        return serviceYears;
    }

    public void setServiceYears(Integer serviceYears) {
        this.serviceYears = serviceYears;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public String getServiceContentText() {
        return serviceContentText;
    }

    public void setServiceContentText(String serviceContentText) {
        this.serviceContentText = serviceContentText;
    }

    public String getServiceContentVideo() {
        return serviceContentVideo;
    }

    public void setServiceContentVideo(String serviceContentVideo) {
        this.serviceContentVideo = serviceContentVideo;
    }

    public String getServiceContentVideoPoster() {
        return serviceContentVideoPoster;
    }

    public void setServiceContentVideoPoster(String serviceContentVideoPoster) {
        this.serviceContentVideoPoster = serviceContentVideoPoster;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean getHaveIntroduction() {
        return haveIntroduction;
    }

    public void setHaveIntroduction(Boolean haveIntroduction) {
        this.haveIntroduction = haveIntroduction;
    }

    public Boolean getFocused() {
        return focused;
    }

    public void setFocused(Boolean focused) {
        this.focused = focused;
    }

    public Boolean getZaned() {
        return zaned;
    }

    public void setZaned(Boolean zaned) {
        this.zaned = zaned;
    }

    public List<ContactInfo> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(List<ContactInfo> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getMainPhoneNumber() {
        return mainPhoneNumber;
    }

    public void setMainPhoneNumber(String mainPhoneNumber) {
        this.mainPhoneNumber = mainPhoneNumber;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getStringTags() {
        return stringTags;
    }

    public void setStringTags(String stringTags) {
        String[] ArrayStringTags = stringTags.split(",");
        this.stringTags = stringTags.replace(",","/");
        if (this.tags == null) this.tags = new ArrayList<String>();
        for (int i=0;i<ArrayStringTags.length;i++){
            this.tags.add(ArrayStringTags[i]);
        }
    }

    public Integer getLookOverTimes() {
        return lookOverTimes;
    }

    public void setLookOverTimes(Integer lookOverTimes) {
        this.lookOverTimes = lookOverTimes;
    }

    public Integer getFocusedTimes() {
        return focusedTimes;
    }

    public void setFocusedTimes(Integer focusedTimes) {
        this.focusedTimes = focusedTimes;
    }

    public Integer getZanedTimes() {
        return zanedTimes;
    }

    public void setZanedTimes(Integer zanedTimes) {
        this.zanedTimes = zanedTimes;
    }

    public Boolean getHaveAddress() {
        return haveAddress;
    }

    public void setHaveAddress(Boolean haveAddress) {
        this.haveAddress = haveAddress;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Long location_id) {
        this.location_id = location_id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Image> getServiceRelated() {
        return serviceRelated;
    }

    public void setServiceRelated(List<Image> serviceRelated) {
        this.serviceRelated = serviceRelated;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public static Integer UNIT_TYPE_PERSON = 1;     //个人
    public static Integer UNIT_TYPE_SHOP = 2;       //个体
    public static Integer UNIT_TYPE_COMPANY = 3;    //公司
    public static Integer UNIT_TYPE_XINGZHENG = 4;  //行政单位
    public static Integer UNIT_TYPE_SHIYE = 5;      //事业单位
}
