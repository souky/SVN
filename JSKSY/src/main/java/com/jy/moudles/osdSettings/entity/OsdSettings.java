package com.jy.moudles.osdSettings.entity;

import com.jy.common.entity.BaseEntity;

public class OsdSettings extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -968867334954073495L;

	private String id;

    private String sbym;

    private String osd;

    private String sipdlm;

    private String sipdlmm;

    private String sipdz;

    private String examplan;

    private String examsession;

    private String orgcode;

    private String orgidencode;

    private String setstatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSbym() {
        return sbym;
    }

    public void setSbym(String sbym) {
        this.sbym = sbym == null ? null : sbym.trim();
    }

    public String getOsd() {
        return osd;
    }

    public void setOsd(String osd) {
        this.osd = osd == null ? null : osd.trim();
    }

    public String getSipdlm() {
        return sipdlm;
    }

    public void setSipdlm(String sipdlm) {
        this.sipdlm = sipdlm == null ? null : sipdlm.trim();
    }

    public String getSipdlmm() {
        return sipdlmm;
    }

    public void setSipdlmm(String sipdlmm) {
        this.sipdlmm = sipdlmm == null ? null : sipdlmm.trim();
    }

    public String getSipdz() {
        return sipdz;
    }

    public void setSipdz(String sipdz) {
        this.sipdz = sipdz == null ? null : sipdz.trim();
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

    public String getSetstatus() {
        return setstatus;
    }

    public void setSetstatus(String setstatus) {
        this.setstatus = setstatus == null ? null : setstatus.trim();
    }
}