package com.ynsh.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.ynsh.common.utils.Collections3;
import com.ynsh.common.persistence.entity.DataEntity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 用户基本信息 Entity, 对应 t_sys_user 表
 */
public class User extends DataEntity<User, Long> {
    private String code;    //登录名,柜员号
    private String loginPassword;   //登录密码
    private String auditPassword;  //复核密码
    private String name;	// 姓名
    private String email;	// 邮箱
    private String phone;	// 电话
    private String mobile;	// 手机
    private Integer type;// 用户类型
    private String photo;	// 头像
    private String idCard;  //身份证号
    private Date birthday;  //出生日期
    private String gender;  //性别
    private String address; //住址
    private Date entryDate; //入职时间
    private String post;    //职务
    private Boolean loginUseable; //是否可登录
    private Boolean auditUseable; //是否可复核

    private String loginIp;	// 最后登陆IP
    private Date loginTime;	// 最后登陆时间

    private String oldCode; //原登录名,柜员号
    private String newLoginPassword;	// 新登录密码
    private String oldLoginIp;	// 上次登陆IP
    private Date oldLoginTime;	// 上次登陆时间

    private Role role;	// 根据角色查询用户条件

    private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

    private List<Menu> menuList = Lists.newArrayList(); //拥有权限的菜单




    public User() {
        super();
    }

    public User(Long id) {
        super(id);
    }

    public User(Long id, String code) {
        super(id);
        this.code = code;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    @Length(min=1, max=100, message="登录名,柜员号长度必须介于 1 和 100 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
    public String getAuditPassword() {
        return auditPassword;
    }

    public void setAuditPassword(String auditPassword) {
        this.auditPassword = auditPassword;
    }

    @Length(min=1, max=100, message="姓名长度必须介于 1 和 100 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Email(message="邮箱格式不正确")
    @Length(min=0, max=200, message="邮箱长度必须介于 1 和 200 之间")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min=0, max=200, message="电话长度必须介于 1 和 200 之间")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min=0, max=200, message="手机号长度必须介于 1 和 200 之间")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Length(min=0, max=100, message="用户类型长度必须介于 1 和 100 之间")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Boolean getLoginUseable() {
        return loginUseable;
    }

    public void setLoginUseable(Boolean loginUseable) {
        this.loginUseable = loginUseable;
    }

    public Boolean getAuditUseable() {
        return auditUseable;
    }

    public void setAuditUseable(Boolean auditUseable) {
        this.auditUseable = auditUseable;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public String getNewLoginPassword() {
        return newLoginPassword;
    }

    public void setNewLoginPassword(String newLoginPassword) {
        this.newLoginPassword = newLoginPassword;
    }

    public String getOldLoginIp() {
        return oldLoginIp;
    }

    public void setOldLoginIp(String oldLoginIp) {
        this.oldLoginIp = oldLoginIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOldLoginTime() {
        return oldLoginTime;
    }

    public void setOldLoginTime(Date oldLoginTime) {
        this.oldLoginTime = oldLoginTime;
    }

    @JsonIgnore
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    //@JsonIgnore
    public List<Long> getRoleIdList() {
        List<Long> roleIdList = Lists.newArrayList();
        for (Role role : roleList) {
            roleIdList.add(role.getId());
        }
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        Logger logger = LoggerFactory.getLogger(User.class);
        logger.info("roleIdList: {}", roleIdList);
        roleList = Lists.newArrayList();
        for (Long roleId : roleIdList) {
            Role role = new Role();
            role.setId(roleId);
            roleList.add(role);
        }
    }

    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     */
    public String getRoleNames() {
        return Collections3.extractToString(roleList, "name", ",");
    }


    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }


    public boolean isAdmin(){
        return isAdmin(this.id);
    }

    public static boolean isAdmin(Long id){
        return id != null && id.equals(1L);
    }
}
