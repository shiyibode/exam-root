package com.ynsh.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynsh.common.persistence.entity.DataEntity;
import com.ynsh.modules.sys.utils.UserUtils;

import java.util.Date;
import java.util.List;

/**
 * 用户 Entity 对应 t_sys_user_organization表
 */
public class UserOrganization extends DataEntity<UserOrganization, Long> {
    private User user;

    private Organization organization;

    private Date startDate;
    private Date endDate;
    private Integer status;

    //用于柜员号与姓名同时模糊查询
    private String userCodeOrName;
    private Integer notUserType;

    /**
     * 当前用户
     */
    protected UserOrganization currentUserOrganization;

    private Long oldId;
    private Integer operationType;

    public static final Integer STATUS_LEAVE = 0;   //调离
    public static final Integer STATUS_INOFFICE = 1;//在职
    public static final Integer STATUS_BANDH = 2;   //停职
    public static final Integer STATUS_RESIGN = 3;  //辞职
    public static final Integer STATUS_DISMISS = 4; //辞退
    public static final Integer STATUS_RETIRE = 5;  //退休
    public static final Integer STATUS_LANCHU = 6;  //揽储
    public static final Integer STATUS_HOLIDAY = 7; //休假

    public static final String[] STATUS_STR = {"调离", "在职", "停职", "辞职", "辞退", "退休", "揽储", "休假"};

    public UserOrganization() {
        super();
    }

    public UserOrganization(Long id) {
        super(id);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        if (null != this.getStatus()) {
            return STATUS_STR[this.getStatus()];
        }
        return "未知";
    }

    public String getUserCodeOrName() {
        return userCodeOrName;
    }

    public void setUserCodeOrName(String userCodeOrName) {
        this.userCodeOrName = userCodeOrName;
    }

    public Integer getNotUserType() {
        return notUserType;
    }

    public void setNotUserType(Integer notUserType) {
        this.notUserType = notUserType;
    }

    @JsonIgnore
    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Organization getOrganization() {
        if (organization == null) {
            organization = new Organization();
        }
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    //用户信息
    public Long getUserId() {
        return this.getUser().getId();
    }

    public void setUserId(Long userId) {
        this.getUser().setId(userId);
    }

    public String getUserCode() {
        return this.getUser().getCode();
    }

    public void setUserCode(String userCode) {
        this.getUser().setCode(userCode);
    }

    public String getUserLoginPassword() {
        return this.getUser().getLoginPassword();
    }

    public void setUserLoginPassword(String loginPassword) {
        this.getUser().setLoginPassword(loginPassword);
    }

    public String getUserAuditPassword() {
        return this.getUser().getAuditPassword();
    }

    public void setUserAuditPassword(String auditPassword) {
        this.getUser().setAuditPassword(auditPassword);
    }

    public String getUserName() {
        return this.getUser().getName();
    }

    public void setUserName(String userName) {
        this.getUser().setName(userName);
    }

    public String getUserEmail() {
        return this.getUser().getEmail();
    }

    public void setUserEmail(String email) {
        this.getUser().setEmail(email);
    }

    public String getUserPhone() {
        return this.getUser().getPhone();
    }

    public void setUserPhone(String phone) {
        this.getUser().setPhone(phone);
    }

    public String getUserMobile() {
        return this.getUser().getMobile();
    }

    public void setUserMobile(String mobile) {
        this.getUser().setMobile(mobile);
    }

    public Integer getUserType() {
        return this.getUser().getType();
    }

    public void setUserType(Integer type) {
        this.getUser().setType(type);
    }

    public String getUserPhoto() {
        return this.getUser().getPhoto();
    }

    public void setUserPhoto(String photo) {
        this.getUser().setPhoto(photo);
    }

    public String getUserIdCard() {
        return this.getUser().getIdCard();
    }

    public void setIdCard(String idCard) {
        this.getUser().setIdCard(idCard);
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getUserBirthday() {
        return this.getUser().getBirthday();
    }

    public void setUserBirthday(Date userBirthday) {
        this.getUser().setBirthday(userBirthday);
    }

    public String getUserGender() {
        return this.getUser().getGender();
    }

    public void setUserGender(String gender) {
        this.getUser().setGender(gender);
    }

    public String getUserAddress() {
        return this.getUser().getAddress();
    }

    public void setUserAddress(String userAddress) {
        this.getUser().setAddress(userAddress);
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getUserEntryDate() {
        return this.getUser().getEntryDate();
    }

    public void setUserEntryDate(Date entryDate) {
        this.getUser().setEntryDate(entryDate);
    }

    public String getUserPost() {
        return this.getUser().getPost();
    }

    public void setUserPost(String post) {
        this.getUser().setPost(post);
    }

    public Boolean getUserLoginUseable() {
        return this.getUser().getLoginUseable();
    }

    public void setUserLoginUseable(Boolean loginUseable) {
        this.getUser().setLoginUseable(loginUseable);
    }

    public Boolean getUserAuditUseable() {
        return this.getUser().getAuditUseable();
    }

    public void setUserAuditUseable(Boolean auditUseable) {
        this.getUser().setAuditUseable(auditUseable);
    }

    public String getUserLoginIp() {
        return this.getUser().getLoginIp();
    }

    public void setUserLoginIp(String loginIp) {
        this.getUser().setLoginIp(loginIp);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUserLoginTime() {
        return this.getUser().getLoginTime();
    }

    public void setUserLoginTime(Date loginTime) {
        this.getUser().setLoginTime(loginTime);
    }

    public String getUserNewLoginPassword() {
        return this.getUser().getNewLoginPassword();
    }

    public void setUserNewLoginPassword(String newLoginPassword) {
        this.getUser().setNewLoginPassword(newLoginPassword);
    }

    public List<Long> getUserRoleIdList() {
        return this.getUser().getRoleIdList();
    }

    public void setUserRoleIdList(List<Long> roleIdList) {
        this.getUser().setRoleIdList(roleIdList);
    }

    public List<Role> getUserRoleList() {
        return this.getUser().getRoleList();
    }

    public String getUserRoleNames() {
        return this.getUser().getRoleNames();
    }

    public List<Menu> getUserMenuList() {
        return this.getUser().getMenuList();
    }


    public User getUserCreateBy() {
        return this.getCreateBy();
    }

    public User getUserUpdateBy() {
        return this.getUpdateBy();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUserCreateTime() {
        return this.getUser().getCreateTime();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUserUpdateTime() {
        return this.getUser().getUpdateTime();
    }


    //机构信息
    public Long getOrganizationId() {
        return this.getOrganization().getId();
    }

    public void setOrganizationId(Long id) {
        this.getOrganization().setId(id);
    }

    public String getOrganizationCode() {
        return this.getOrganization().getCode();
    }

    public void setOrganizationCode(String organizationCode) {
        this.getOrganization().setCode(organizationCode);
    }

    public String getOrganizationName() {
        return this.getOrganization().getName();
    }

    public void setOrganizationName(String organizationName) {
        this.getOrganization().setName(organizationName);
    }


    ///////////////////
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
