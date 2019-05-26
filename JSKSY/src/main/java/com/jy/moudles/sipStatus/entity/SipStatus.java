package com.jy.moudles.sipStatus.entity;

import com.jy.common.entity.BaseEntity;

public class SipStatus extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String sbbh;

    private String sbcsdm;

    private String gzzt;

    private String sipzczt;

    private String sjpyms;

    private String sjlls;

    private String bjlls;

    private String sjll;

    private String bjll;

    private String wlllup;

    private String wllldown;

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

    public String getSipzczt() {
        return sipzczt;
    }

    public void setSipzczt(String sipzczt) {
        this.sipzczt = sipzczt == null ? null : sipzczt.trim();
    }

    public String getSjpyms() {
        return sjpyms;
    }

    public void setSjpyms(String sjpyms) {
        this.sjpyms = sjpyms == null ? null : sjpyms.trim();
    }

    public String getSjlls() {
        return sjlls;
    }

    public void setSjlls(String sjlls) {
        this.sjlls = sjlls == null ? null : sjlls.trim();
    }

    public String getBjlls() {
        return bjlls;
    }

    public void setBjlls(String bjlls) {
        this.bjlls = bjlls == null ? null : bjlls.trim();
    }

    public String getSjll() {
        return sjll;
    }

    public void setSjll(String sjll) {
        this.sjll = sjll == null ? null : sjll.trim();
    }

    public String getBjll() {
        return bjll;
    }

    public void setBjll(String bjll) {
        this.bjll = bjll == null ? null : bjll.trim();
    }

    public String getWlllup() {
        return wlllup;
    }

    public void setWlllup(String wlllup) {
        this.wlllup = wlllup == null ? null : wlllup.trim();
    }

    public String getWllldown() {
        return wllldown;
    }

    public void setWllldown(String wllldown) {
        this.wllldown = wllldown == null ? null : wllldown.trim();
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