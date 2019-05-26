package com.jy.moudles.cameraStatus.entity;

import com.jy.common.entity.BaseEntity;

public class CameraStatus extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -470388467317666487L;

	private String id;

    private String sbbh;

    private String sbcsdm;

    private String gzzt;

    private String sjpyms;

    private String spzlzd;

    private String zfbl;

    private String ffbl;

    private String zml;

    private String fml;

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

    public String getSjpyms() {
        return sjpyms;
    }

    public void setSjpyms(String sjpyms) {
        this.sjpyms = sjpyms == null ? null : sjpyms.trim();
    }

    public String getSpzlzd() {
        return spzlzd;
    }

    public void setSpzlzd(String spzlzd) {
        this.spzlzd = spzlzd == null ? null : spzlzd.trim();
    }

    public String getZfbl() {
        return zfbl;
    }

    public void setZfbl(String zfbl) {
        this.zfbl = zfbl == null ? null : zfbl.trim();
    }

    public String getFfbl() {
        return ffbl;
    }

    public void setFfbl(String ffbl) {
        this.ffbl = ffbl == null ? null : ffbl.trim();
    }

    public String getZml() {
        return zml;
    }

    public void setZml(String zml) {
        this.zml = zml == null ? null : zml.trim();
    }

    public String getFml() {
        return fml;
    }

    public void setFml(String fml) {
        this.fml = fml == null ? null : fml.trim();
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