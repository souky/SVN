package com.jy.moudles.videoPlanStatus.entity;

import com.jy.common.entity.BaseEntity;

public class VideoPlanStatus extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 541557708662661798L;

	private String id;

    private String csbh;

    private String fgqk;	//考前覆盖情况

    private String orgcode;

    private String orgidencode;

    private String examplan;

    private String examsession;
    
    private String statustype;	//考后覆盖情况

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCsbh() {
        return csbh;
    }

    public void setCsbh(String csbh) {
        this.csbh = csbh == null ? null : csbh.trim();
    }

    public String getFgqk() {
        return fgqk;
    }

    public void setFgqk(String fgqk) {
        this.fgqk = fgqk == null ? null : fgqk.trim();
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode == null ? null : orgcode.trim();
    }

    public String getOrgidencode() {
        return orgidencode;
    }

    public void setOrgidencode(String orgidencode) {
        this.orgidencode = orgidencode == null ? null : orgidencode.trim();
    }

    public String getExamplan() {
        return examplan;
    }

    public void setExamplan(String examplan) {
        this.examplan = examplan == null ? null : examplan.trim();
    }

    public String getExamsession() {
        return examsession;
    }

    public void setExamsession(String examsession) {
        this.examsession = examsession == null ? null : examsession.trim();
    }

	public String getStatustype() {
		return statustype;
	}

	public void setStatustype(String statustype) {
		this.statustype = statustype == null ? null : statustype.trim();
	}
    
}