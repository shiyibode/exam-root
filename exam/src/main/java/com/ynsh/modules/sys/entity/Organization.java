package com.ynsh.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynsh.common.persistence.entity.TreeEntity;
import com.ynsh.modules.sys.utils.UserUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjianjun on 16/3/16.
 */
public class Organization extends TreeEntity<Organization, Long> {
    private Area area;		// 归属区域
    private String code; 	// 机构编码
    private String iconCls; // 图标
    private String type; 	// 机构类型 (0:公司/企业; 100:网点分组; 200:部门分组; 101:本部; 102: 汇总; 103:营业部; 104:支行; 105:分理处; 106:信用卡中心; 201:管理部门; 202:下设中心)
    private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
    private String address; // 联系地址
    private String zipCode; // 邮政编码
    private String master; 	// 负责人
    private String phone; 	// 电话
    private String fax; 	// 传真
    private String email; 	// 邮箱
    private String useable;//是否可用
    private User primaryPerson;//主负责人
    private User deputyPerson;//副负责人

    public static final String TYPE_COMPANY = "0";                  //公司/企业
    public static final String TYPE_NODE_GROUP = "100";             //营业网点分组
    public static final String TYPE_DEPARTMENT_GROUP = "200";       //部门分组
    public static final String TYPE_NODE_MAIN = "101";              //本部
    public static final String TYPE_NODE_COLLECT = "102";           //汇总
    public static final String TYPE_SALES_DEPARTMENT = "103";       //营业部
    public static final String TYPE_NODE_BRANCH = "104";            //支行
    public static final String TYPE_NODE_SUB_BRANCH = "105";        //分理处
    public static final String TYPE_CREDIT_CARD_CENTER = "106";     //信用卡中心
    public static final String TYPE_DEPARTMENT = "201";             //管理部门
    public static final String TYPE_DEPARTMENT_SUB_CENTER = "201";  //下设中心
    public static final String TYPE_FINANCIAL_SALES_GROUP = "300";  //其它机构分组
    public static final String TYPE_FINANCIAL_SALES_ORGANIZATION = "301";  //理财代销机构

    private static final Map<String, String> TYPE_STR_MAP = new HashMap() {{
        put(TYPE_COMPANY, "公司/企业");
        put(TYPE_NODE_GROUP, "营业网点分组");
        put(TYPE_DEPARTMENT_GROUP, "部门分组");
        put(TYPE_NODE_MAIN, "本部");
        put(TYPE_NODE_COLLECT, "汇总");
        put(TYPE_SALES_DEPARTMENT, "营业部");
        put(TYPE_NODE_BRANCH, "支行");
        put(TYPE_NODE_SUB_BRANCH, "分理处");
        put(TYPE_CREDIT_CARD_CENTER, "信用卡中心");
        put(TYPE_DEPARTMENT, "管理部门");
        put(TYPE_DEPARTMENT_SUB_CENTER, "下设中心");
        put(TYPE_FINANCIAL_SALES_GROUP, "300");
        put(TYPE_FINANCIAL_SALES_ORGANIZATION, "301");
    }};

    /**
     * 当前用户
     */
    protected UserOrganization currentUserOrganization;


    /**
     * 默认构造方法
     */
    public Organization() {
        super();
    }

    /**
     * 构造方法
     * @param id
     */
    public Organization(Long id) {
        super(id);
    }


    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public Long getRootId() {
        return 0L;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    @NotNull
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Long getAreaId() {
        Long id = null;
        if (area != null){
            id = area.getId();
        }
        return (id != null) ? id : 0L;
    }

    public void setAreaId(Long areaId) {
        if (null == this.getArea()) {
            this.setArea(new Area());
        }
        this.getArea().setId(areaId);
    }

    @Length(min=0, max=100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Length(min=1, max=1)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getTypeString(String type) {
        String typeStr = TYPE_STR_MAP.get(type);
        if (typeStr != null) {
            return typeStr;
        }
        return "未定义";
    }

    @Length(min=1, max=1)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Length(min=0, max=1024)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(min=0, max=100)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Length(min=0, max=100)
    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Length(min=0, max=200)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min=0, max=200)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Length(min=0, max=200)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public User getPrimaryPerson() {
        return primaryPerson;
    }

    public void setPrimaryPerson(User primaryPerson) {
        this.primaryPerson = primaryPerson;
    }

    public User getDeputyPerson() {
        return deputyPerson;
    }

    public void setDeputyPerson(User deputyPerson) {
        this.deputyPerson = deputyPerson;
    }

    @JsonIgnore
    public UserOrganization getCurrentUserOrganization() {
        if(currentUserOrganization == null){
            currentUserOrganization = UserUtils.getUserOrganization();
        }
        return currentUserOrganization;
    }

    public void setCurrentUserOrganization(UserOrganization currentUserOrganization) {
        this.currentUserOrganization = currentUserOrganization;
    }
}
