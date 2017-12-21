package com.aihui.dcdeliver.bean;

import com.aihui.dcdeliver.commponent.jdaddressselector.ISelectAble;

import java.io.Serializable;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/21 10:31
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/21$
 * @ 更新描述  ${TODO}
 */

public class PlaceBean implements Serializable ,ISelectAble {
    /**
     * createUserid : 1
     * gmtCreate : 2017-06-30 16:25:35
     * gmtModified : 2017-06-30 16:25:35
     * id : 18
     * isDel : 0
     * isPairing : 0
     * modifiedUserid : 1
     * orgId : 1
     * pid : 0
     * pids : -18-
     * placeCode :
     * placeFullName : 科技楼
     * placeName : 科技楼
     * placePy : KJL
     * placeQrcode :
     * placeQtm :
     * placeRfid :
     * printCount : 0
     * rank : 1
     * remark :
     * sort : 1
     */

    private int createUserid;
    private String gmtCreate;
    private String gmtModified;
    private int    id;
    private int    isDel;
    private int    isPairing;
    private int    modifiedUserid;
    private int    orgId;
    private int    pid;
    private String pids;
    private String placeCode;
    private String placeFullName;
    private String placeName;
    private String placePy;
    private String placeQrcode;
    private String placeQtm;
    private String placeRfid;
    private int    printCount;
    private int    rank;
    private String remark;
    private int    sort;

    public int getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(int createUserid) {
        this.createUserid = createUserid;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String getName() {
        return placeName;
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getArg() {
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getIsPairing() {
        return isPairing;
    }

    public void setIsPairing(int isPairing) {
        this.isPairing = isPairing;
    }

    public int getModifiedUserid() {
        return modifiedUserid;
    }

    public void setModifiedUserid(int modifiedUserid) {
        this.modifiedUserid = modifiedUserid;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlaceFullName() {
        return placeFullName;
    }

    public void setPlaceFullName(String placeFullName) {
        this.placeFullName = placeFullName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlacePy() {
        return placePy;
    }

    public void setPlacePy(String placePy) {
        this.placePy = placePy;
    }

    public String getPlaceQrcode() {
        return placeQrcode;
    }

    public void setPlaceQrcode(String placeQrcode) {
        this.placeQrcode = placeQrcode;
    }

    public String getPlaceQtm() {
        return placeQtm;
    }

    public void setPlaceQtm(String placeQtm) {
        this.placeQtm = placeQtm;
    }

    public String getPlaceRfid() {
        return placeRfid;
    }

    public void setPlaceRfid(String placeRfid) {
        this.placeRfid = placeRfid;
    }

    public int getPrintCount() {
        return printCount;
    }

    public void setPrintCount(int printCount) {
        this.printCount = printCount;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
