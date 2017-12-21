package com.aihui.dcdeliver.bean;

import com.aihui.dcdeliver.commponent.jdaddressselector.ISelectAble;
import com.aihui.dcdeliver.commponent.jdaddressselector.ISelectAbleList;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/20 13:46
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/20$
 * @ 更新描述  ${TODO}
 */

public class TaskTypeBeanList implements Serializable {


    /**
     * msg : success
     * body : {"0":[{"createUser":null,"gmtCreate":null,"gmtModify":null,"hasPlace":null,"id":1,"isDel":0,"isLast":0,"modifyUser":null,"pid":0,"pids":"-1-","rank":1,"taskName":"病人检查"},{"createUser":null,"gmtCreate":null,"gmtModify":null,"hasPlace":null,"id":2,"isDel":0,"isLast":0,"modifyUser":null,"pid":0,"pids":"-2-","rank":2,"taskName":"物资运送"}],"1":[{"createUser":null,"gmtCreate":null,"gmtModify":null,"hasPlace":null,"id":3,"isDel":0,"isLast":null,"modifyUser":null,"pid":1,"pids":"-1-3-","rank":1,"taskName":"CT检查"}]}
     * code : 200
     */

    private String msg;
    private BodyBean body;
    private int      code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class BodyBean implements Serializable,ISelectAbleList{
        @SerializedName("0")
        private List<Bean0> List0;
        @SerializedName("1")
        private List<Bean0> List1;
        @SerializedName("2")
        private List<Bean0> List2;

        @Override
        public List<Bean0> getList0() {
            return List0;
        }

        public void setList0(List<Bean0> List0) {
            this.List0 = List0;
        }
        @Override
        public List<Bean0> getList1() {
            return List1;
        }

        public void setList1(List<Bean0> List1) {
            this.List1 = List1;
        }
        @Override
        public List<Bean0> getList2() {
            return List2;
        }

        @Override
        public List<Bean0> getList(Integer integer) {
            if (integer==0){
                return List0;
            }else if (integer==1){
                return List1;
            }else if(integer==2){
                return List2;
            }
            return null;
        }

        public void setList2(List<Bean0> List2) {
            this.List2 = List2;
        }


        public static class Bean0 implements Serializable,ISelectAble{
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

            private String createUser;
            private String gmtCreate;
            private String gmtModify;
            private String hasPlace;
            private int    id;
            private int    isDel;
            private int    isLast;
            private String modifyUser;
            private int    pid;
            private String pids;
            private int    rank;
            private String taskName;

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public String getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public String getGmtModify() {
                return gmtModify;
            }

            public void setGmtModify(String gmtModify) {
                this.gmtModify = gmtModify;
            }

            public String getHasPlace() {
                return hasPlace;
            }

            public void setHasPlace(String hasPlace) {
                this.hasPlace = hasPlace;
            }

            @Override
            public String getName() {
                return taskName;
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

            public String getModifyUser() {
                return modifyUser;
            }

            public void setModifyUser(String modifyUser) {
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
        }

    }
}
