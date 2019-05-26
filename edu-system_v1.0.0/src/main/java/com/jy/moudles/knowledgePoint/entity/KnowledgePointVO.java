package com.jy.moudles.knowledgePoint.entity;

import java.util.List;

public class KnowledgePointVO {

    private String id;

    /**
     * 知识点编码
     */
    private String knowledgeCode;

    /**
     * 知识点内容
     */
    private String knowledgeContent;

    /**
     * 知识点子节点
     */
    private List<KnowledgePointVO> kpVOChildList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKnowledgeCode() {
        return knowledgeCode;
    }

    public void setKnowledgeCode(String knowledgeCode) {
        this.knowledgeCode = knowledgeCode;
    }

    public String getKnowledgeContent() {
        return knowledgeContent;
    }

    public void setKnowledgeContent(String knowledgeContent) {
        this.knowledgeContent = knowledgeContent;
    }

    public List<KnowledgePointVO> getKpVOChildList() {
        return kpVOChildList;
    }

    public void setKpVOChildList(List<KnowledgePointVO> kpVOChildList) {
        this.kpVOChildList = kpVOChildList;
    }
}
