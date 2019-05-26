package com.jy.moudles.nvrStatus.entity;

import com.jy.common.entity.BaseEntity;

public class NvrStatus extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String sbbh;

    private String sbcsdm;

    private String gzzt;

    private String cckgzt;

    private String cpyj;

    private String sjpyms;

    private String kjzdx;

    private String kxkjdx;

    private String orgcode;

    private String orgidencode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSbbh() {
        return sbbh;
    }

    public void setSbbh(String sbbh) {
        this.sbbh = sbbh == null ? null : sbbh.trim();
    }

    public String getSbcsdm() {
        return sbcsdm;
    }

    public void setSbcsdm(String sbcsdm) {
        this.sbcsdm = sbcsdm == null ? null : sbcsdm.trim();
    }

    public String getGzzt() {
        return gzzt;
    }

    public void setGzzt(String gzzt) {
        this.gzzt = gzzt == null ? null : gzzt.trim();
    }

    public String getCckgzt() {
        return cckgzt;
    }

    public void setCckgzt(String cckgzt) {
        this.cckgzt = cckgzt == null ? null : cckgzt.trim();
    }

    public String getCpyj() {
        return cpyj;
    }

    public void setCpyj(String cpyj) {
        this.cpyj = cpyj == null ? null : cpyj.trim();
    }

    public String getSjpyms() {
        return sjpyms;
    }

    public void setSjpyms(String sjpyms) {
        this.sjpyms = sjpyms == null ? null : sjpyms.trim();
    }

    public String getKjzdx() {
        return kjzdx;
    }

    public void setKjzdx(String kjzdx) {
        this.kjzdx = kjzdx == null ? null : kjzdx.trim();
    }

    public String getKxkjdx() {
        return kxkjdx;
    }

    public void setKxkjdx(String kxkjdx) {
        this.kxkjdx = kxkjdx == null ? null : kxkjdx.trim();
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
}