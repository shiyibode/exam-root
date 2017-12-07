package com.ynsh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by syb on 2017/9/5.
 */
public class MainPageGroup extends BaseModel{

    private Long id;

    private String text;

    private String icon;

    private List<List<MainPageGroupItem>> mainPageGroupItemList;

    private boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<List<MainPageGroupItem>> getMainPageGroupItemList() {
        return mainPageGroupItemList;
    }

    public void setMainPageGroupItemList(List<List<MainPageGroupItem>> mainPageGroupItemList) {
        this.mainPageGroupItemList = mainPageGroupItemList;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
