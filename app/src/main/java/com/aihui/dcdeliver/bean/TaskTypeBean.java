package com.aihui.dcdeliver.bean;

import com.aihui.dcdeliver.commponent.jdaddressselector.ISelectAble;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/20 13:46
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/20$
 * @ 更新描述  ${TODO}
 */

public class TaskTypeBean implements ISelectAble {
    /**
     * createUser : null
     * gmtCreate : null
     * gmtModify : null
     * hasPlace : null
     * id : 1
     * isDel : 0
     * isLast : 0
     * modifyUser : null
     * pid : 0
     * pids : -1-
     * rank : 1
     * taskName : 病人检查
     */

    private Object createUser;
    private Object gmtCreate;
    private Object gmtModify;
    private Object hasPlace;
    private int    id;
    private int    isDel;
    private int    isLast;
    private Object modifyUser;
    private int    pid;
    private String pids;
    private int    rank;
    private String taskName;

    public Object getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Object createUser) {
        this.createUser = createUser;
    }

    public Object getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Object gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Object getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Object gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Object getHasPlace() {
        return hasPlace;
    }

    public void setHasPlace(Object hasPlace) {
        this.hasPlace = hasPlace;
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

    public int getIsLast() {
        return isLast;
    }

    public void setIsLast(int isLast) {
        this.isLast = isLast;
    }

    public Object getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Object modifyUser) {
        this.modifyUser = modifyUser;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String getName() {
        return this.taskName;
    }

}
