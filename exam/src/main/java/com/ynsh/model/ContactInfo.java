package com.ynsh.model;

/**
 * Created by syb on 2017/9/19.
 */
public class ContactInfo extends BaseModel{

    private String type;            //类型，如手机、QQ、微信

    private Boolean isPhoneNumber;  //是电话号码

    private Boolean isQQNumber;     //是QQ号

    private Boolean isWeChatNumber; //是微信号

    private String value;           //号码值

    private String icon;            //图标，需和type对应

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getPhoneNumber() {
        return isPhoneNumber;
    }

    public void setPhoneNumber(Boolean phoneNumber) {
        isPhoneNumber = phoneNumber;
    }

    public Boolean getQQNumber() {
        return isQQNumber;
    }

    public void setQQNumber(Boolean QQNumber) {
        isQQNumber = QQNumber;
    }

    public Boolean getWeChatNumber() {
        return isWeChatNumber;
    }

    public void setWeChatNumber(Boolean weChatNumber) {
        isWeChatNumber = weChatNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
