package com.jy.moudles.region.entity;

import java.util.List;

public class RegionVO {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String label;

    /**
     * parentId
     */
    private String pid;

    /**
     * 子机构list
     */
    private List<RegionVO> children;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the childrens
     */
    public List<RegionVO> getChildren() {
        return children;
    }

    /**
     * @param children the childrens to set
     */
    public void setChildren(List<RegionVO> children) {
        this.children = children;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
