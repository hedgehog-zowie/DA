package com.iuni.data.webapp.common;

import org.springframework.data.domain.Page;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PageVO {

    /**
     * 当前页
     */
    private Integer currentPage = 1;

    /**
     * 每页显示的条数
     */
    private Integer pageSize = 20;

    /**
     * 总页数
     */
    private Integer totalPage = 0;

    /**
     * 总记录数
     */
    private Long totalRecord = (long) 0;

    public PageVO() {

    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 处理当前页
     *
     * @param currentPage
     * @return void
     */
    public void setCurrentPage(Integer currentPage) {
        if (currentPage > totalPage && totalPage != 0) {
            this.currentPage = totalPage;
        } else if (!(currentPage > 0)) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        if (currentPage > totalPage)
            this.currentPage = totalPage;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }

    /**
     * 根据当前页、总记录数、页大小获得Page
     *
     * @param currentPage
     * @param totalRecord
     * @param pageSize
     * @return Page
     */
    public static PageVO genPage(Integer currentPage, Long totalRecord, Integer totalPage, Integer pageSize) {
        PageVO page = new PageVO();
        // 填充总记录数
        page.setTotalRecord(totalRecord);
        // 填充每页显示的条数
        page.setPageSize(pageSize);
        // 计算总页数
        page.setTotalPage(totalPage);
        // 填充当前页
        page.setCurrentPage(currentPage);

        return page;
    }

    public void setPage(Page resultPage){
        this.setTotalPage(resultPage.getTotalPages());
        this.setTotalRecord(resultPage.getTotalElements());
    }

    public static PageVO createPage(Integer currentPage, Integer pageSize){
        PageVO page = new PageVO();
        if(currentPage != null)
            page.setCurrentPage(currentPage);
        if(pageSize != null)
            page.setPageSize(pageSize);
        return page;
    }

}