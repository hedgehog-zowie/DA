package com.iuni.data.persist.domain.config;

import javax.persistence.*;
import java.util.*;

/**
 * The persistent class for the IUNI_DA_BURIED_GROUP database table.
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Entity
@Table(name = "IUNI_DA_BURIED_GROUP")
public class BuriedGroup extends AbstractConfig implements Comparable<BuriedGroup>{

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "buriedGroup", fetch = FetchType.EAGER)
    private List<BuriedRelation> buriedRelations;

    @Transient
    private Long[] buriedPoints;

    @Transient
    private Long[] sortedBuriedPoints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BuriedRelation> getBuriedRelations() {
        return buriedRelations;
    }

    public Long[] getBuriedPoints() {
        return buriedPoints;
    }

    public void setBuriedPoints(Long[] buriedPoints) {
        this.buriedPoints = buriedPoints;
    }

    public Long[] getSortedBuriedPoints() {
        return sortedBuriedPoints;
    }

    public void setSortedBuriedPoints(Long[] sortedBuriedPoints) {
        this.sortedBuriedPoints = sortedBuriedPoints;
    }

    public void setBuriedRelations(List<BuriedRelation> buriedRelations) {
        this.buriedRelations = buriedRelations;
    }

    public static Map<String,String> generateTableHeader() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("埋点组名称", "name");
        tableHeader.put("埋点个数", "size");
        tableHeader.put("添加时间", "createDate");
        tableHeader.put("备注", "desc");
        return tableHeader;
    }

    public static List<Map<String,Object>> generateTableData(List<BuriedGroup> buriedGroupList) {
        List<Map<String, Object>> tableData = new ArrayList<>();
        for (BuriedGroup buriedPoint : buriedGroupList) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("name", buriedPoint.getName());
            rowData.put("size", buriedPoint.getBuriedRelations().size());
            rowData.put("createDate", String.valueOf(buriedPoint.getCreateDate()));
            rowData.put("desc", buriedPoint.getDesc());
            tableData.add(rowData);
        }
        return tableData;
    }

    @Override
    public int compareTo(BuriedGroup o) {
        return this.getName().compareTo(o.getName());
    }
}
