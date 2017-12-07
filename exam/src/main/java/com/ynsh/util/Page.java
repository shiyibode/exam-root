package com.ynsh.util;

/**
 * Created by syb on 2017/10/15.
 */
public class Page {

    private int currentPage;        //当前页
    private int pageSize;           //单页大小
    private int offset;             //位移数，使用它时不需要使用currentPage
    private String orderBy;         //分页之前的排序
    private Boolean pageFlag;       //是否需要分页

    public Page(){
        this.pageFlag = false; //默认不需要分页
        this.pageSize = -1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getPageFlag() {
        return pageFlag;
    }

    public void setPageFlag(Boolean pageFlag) {
        this.pageFlag = pageFlag;
    }
}
