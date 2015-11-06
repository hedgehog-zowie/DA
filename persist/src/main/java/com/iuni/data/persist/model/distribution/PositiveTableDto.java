package com.iuni.data.persist.model.distribution;

import com.iuni.data.persist.model.AbstractTableDto;

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class PositiveTableDto extends AbstractTableDto{

    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 出库时间
     */
    private Date outDate;
    /**
     * 筛单时间
     */
    private Date filteredDate;
    /**
     * 打单时间
     */
    private Date printedDate;
    /**
     * 配货中时间
     */
    private Date allocatingDate;
    /**
     * 已配货时间
     */
    private Date allocatedDate;
    /**
     * 待出库时间
     */
    private Date waitingOutDate;
    /**
     * 已出库时间
     */
    private Date outedDate;
    /**
     * 已筛单-已打单时长，单位：分
     */
    private Integer timeOfFilteredToPrinted;
    /**
     * 已打单-配货中时长
     */
    private Integer timeOfPrintedToAllocating;
    /**
     * 配货中-已配货时长
     */
    private Integer timeOfAllocatingToAllocated;
    /**
     * 已配货 -待出库时长
     */
    private Integer timeOfAllocatedToWaitingOut;
    /**
     * 待出库-已出库时长
     */
    private Integer timeOfWaitingOutToOuted;
    /**
     * 合计时长
     */
    private Integer time;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getFilteredDate() {
        return filteredDate;
    }

    public void setFilteredDate(Date filteredDate) {
        this.filteredDate = filteredDate;
    }

    public Date getPrintedDate() {
        return printedDate;
    }

    public void setPrintedDate(Date printedDate) {
        this.printedDate = printedDate;
    }

    public Date getAllocatingDate() {
        return allocatingDate;
    }

    public void setAllocatingDate(Date allocatingDate) {
        this.allocatingDate = allocatingDate;
    }

    public Date getAllocatedDate() {
        return allocatedDate;
    }

    public void setAllocatedDate(Date allocatedDate) {
        this.allocatedDate = allocatedDate;
    }

    public Date getWaitingOutDate() {
        return waitingOutDate;
    }

    public void setWaitingOutDate(Date waitingOutDate) {
        this.waitingOutDate = waitingOutDate;
    }

    public Date getOutedDate() {
        return outedDate;
    }

    public void setOutedDate(Date outedDate) {
        this.outedDate = outedDate;
    }

    public Integer getTimeOfFilteredToPrinted() {
        return timeOfFilteredToPrinted;
    }

    public void setTimeOfFilteredToPrinted(Integer timeOfFilteredToPrinted) {
        this.timeOfFilteredToPrinted = timeOfFilteredToPrinted;
    }

    public Integer getTimeOfPrintedToAllocating() {
        return timeOfPrintedToAllocating;
    }

    public void setTimeOfPrintedToAllocating(Integer timeOfPrintedToAllocating) {
        this.timeOfPrintedToAllocating = timeOfPrintedToAllocating;
    }

    public Integer getTimeOfAllocatingToAllocated() {
        return timeOfAllocatingToAllocated;
    }

    public void setTimeOfAllocatingToAllocated(Integer timeOfAllocatingToAllocated) {
        this.timeOfAllocatingToAllocated = timeOfAllocatingToAllocated;
    }

    public Integer getTimeOfAllocatedToWaitingOut() {
        return timeOfAllocatedToWaitingOut;
    }

    public void setTimeOfAllocatedToWaitingOut(Integer timeOfAllocatedToWaitingOut) {
        this.timeOfAllocatedToWaitingOut = timeOfAllocatedToWaitingOut;
    }

    public Integer getTimeOfWaitingOutToOuted() {
        return timeOfWaitingOutToOuted;
    }

    public void setTimeOfWaitingOutToOuted(Integer timeOfWaitingOutToOuted) {
        this.timeOfWaitingOutToOuted = timeOfWaitingOutToOuted;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public static Map<String, String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("订单号", "orderCode");
        tableHeader.put("出库日期", "outDate");
        tableHeader.put("已筛单时间", "filteredDate");
        tableHeader.put("已打单时间", "printedDate");
        tableHeader.put("配货中时间", "allocatingDate");
        tableHeader.put("已配货时间", "allocatedDate");
        tableHeader.put("待出库时间", "waitingOutDate");
        tableHeader.put("已出库时间", "outedDate");
        tableHeader.put("已筛单-已打单时长", "timeOfFilteredToPrinted");
        tableHeader.put("已打单-配货中时长", "timeOfPrintedToAllocating");
        tableHeader.put("配货中-已配货时长", "timeOfAllocatingToAllocated");
        tableHeader.put("已配货 -待出库时长", "timeOfAllocatedToWaitingOut");
        tableHeader.put("待出库-已出库时长", "timeOfWaitingOutToOuted");
        tableHeader.put("合计时长", "time");
        return tableHeader;
    }

    public static List<Map<String, Object>> generateTableData(List<PositiveTableDto> positiveTableDtoList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for(PositiveTableDto positiveTableDto: positiveTableDtoList){
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("orderCode", positiveTableDto.getOrderCode());
            rowData.put("outDate", positiveTableDto.getOutDate());
            rowData.put("filteredDate", positiveTableDto.getFilteredDate());
            rowData.put("printedDate", positiveTableDto.getPrintedDate());
            rowData.put("allocatingDate", positiveTableDto.getAllocatingDate());
            rowData.put("allocatedDate", positiveTableDto.getAllocatedDate());
            rowData.put("waitingOutDate", positiveTableDto.getWaitingOutDate());
            rowData.put("outedDate", positiveTableDto.getOutedDate());
            rowData.put("timeOfFilteredToPrinted", positiveTableDto.getTimeOfFilteredToPrinted());
            rowData.put("timeOfPrintedToAllocating", positiveTableDto.getTimeOfPrintedToAllocating());
            rowData.put("timeOfAllocatingToAllocated", positiveTableDto.getTimeOfAllocatingToAllocated());
            rowData.put("timeOfAllocatedToWaitingOut", positiveTableDto.getTimeOfAllocatedToWaitingOut());
            rowData.put("timeOfWaitingOutToOuted", positiveTableDto.getTimeOfWaitingOutToOuted());
            tableData.add(rowData);
        }
        return tableData;
    }

}
