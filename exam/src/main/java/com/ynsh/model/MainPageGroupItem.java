package com.ynsh.model;

/**
 * Created by syb on 2017/9/5.
 */
public class MainPageGroupItem extends BaseModel{

    private Long id;

    private Long groupId;

    private String text;

    private String textDescribe;

    private String icon;

    private String route;

    private Boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextDescribe() {
        return textDescribe;
    }

    public void setTextDescribe(String textDescribe) {
        this.textDescribe = textDescribe;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
