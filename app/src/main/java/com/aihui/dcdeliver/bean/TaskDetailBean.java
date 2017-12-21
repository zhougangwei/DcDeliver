package com.aihui.dcdeliver.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/20 18:17
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/20$
 * @ 更新描述  ${TODO}
 */

public class TaskDetailBean implements Serializable {

    /**
     * msg : success
     * body : {"extList":[{"colName":"病人姓名","colType":"string","id":1,"required":1,"taskId":3}],"placeList":[{"id":1,"placeId":12,"placeName":"门诊楼4-一楼","taskId":3}],"taskClass":{"createUser":null,"gmtCreate":null,"gmtModify":null,"hasPlace":null,"id":3,"isDel":0,"isLast":null,"modifyUser":null,"pid":1,"pids":"-1-3-","rank":1,"taskName":"CT检查"}}
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

    public static class BodyBean implements Serializable {
        /**
         * extList : [{"colName":"病人姓名","colType":"string","id":1,"required":1,"taskId":3}]
         * placeList : [{"id":1,"placeId":12,"placeName":"门诊楼4-一楼","taskId":3}]
         * taskClass : {"createUser":null,"gmtCreate":null,"gmtModify":null,"hasPlace":null,"id":3,"isDel":0,"isLast":null,"modifyUser":null,"pid":1,"pids":"-1-3-","rank":1,"taskName":"CT检查"}
         */

        private TaskClassBean taskClass;
        private List<ExtListBean>   extList;
        private List<PlaceListBean> placeList;

        public TaskClassBean getTaskClass() {
            return taskClass;
        }

        public void setTaskClass(TaskClassBean taskClass) {
            this.taskClass = taskClass;
        }

        public List<ExtListBean> getExtList() {
            return extList;
        }

        public void setExtList(List<ExtListBean> extList) {
            this.extList = extList;
        }

        public List<PlaceListBean> getPlaceList() {
            return placeList;
        }

        public void setPlaceList(List<PlaceListBean> placeList) {
            this.placeList = placeList;
        }

        @Override
        public String toString() {
            return "BodyBean{" +
                    "taskClass=" + taskClass +
                    ", extList=" + extList +
                    ", placeList=" + placeList +
                    '}';
        }

        public static class TaskClassBean implements Serializable  {
            /**
             * createUser : null
             * gmtCreate : null
             * gmtModify : null
             * hasPlace : null
             * id : 3
             * isDel : 0
             * isLast : null
             * modifyUser : null
             * pid : 1
             * pids : -1-3-
             * rank : 1
             * taskName : CT检查
             */

            private Object createUser;
            private Object gmtCreate;
            private Object gmtModify;
            private Object hasPlace;
            private int    id;
            private int    isDel;
            private Object isLast;
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

            public int getId() {
                return id;
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

            public Object getIsLast() {
                return isLast;
            }

            public void setIsLast(Object isLast) {
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
            public String toString() {
                return "TaskClassBean{" +
                        "createUser=" + createUser +
                        ", gmtCreate=" + gmtCreate +
                        ", gmtModify=" + gmtModify +
                        ", hasPlace=" + hasPlace +
                        ", id=" + id +
                        ", isDel=" + isDel +
                        ", isLast=" + isLast +
                        ", modifyUser=" + modifyUser +
                        ", pid=" + pid +
                        ", pids='" + pids + '\'' +
                        ", rank=" + rank +
                        ", taskName='" + taskName + '\'' +
                        '}';
            }
        }

        public static class ExtListBean implements Serializable{
            /**
             * colName : 病人姓名
             * colType : string
             * id : 1
             * required : 1
             * taskId : 3
             */

            private String colName;
            private String colType;
            private int    id;
            private int    required;
            private int    taskId;
            private String colValue;

            public String getColName() {
                return colName;
            }

            public void setColName(String colName) {
                this.colName = colName;
            }

            public String getColType() {
                return colType;
            }

            public void setColType(String colType) {
                this.colType = colType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getRequired() {
                return required;
            }

            public void setRequired(int required) {
                this.required = required;
            }

            public int getTaskId() {
                return taskId;
            }

            public void setTaskId(int taskId) {
                this.taskId = taskId;
            }



            public String getColValue() {
                return colValue;
            }

            public void setColValue(String colValue) {
                this.colValue = colValue;
            }

            @Override
            public String toString() {
                return "ExtListBean{" +
                        "colName='" + colName + '\'' +
                        ", colType='" + colType + '\'' +
                        ", id=" + id +
                        ", required=" + required +
                        ", taskId=" + taskId +
                        ", colValue='" + colValue + '\'' +
                        '}';
            }
        }

        public static class PlaceListBean implements  Serializable{
            /**
             * id : 1
             * placeId : 12
             * placeName : 门诊楼4-一楼
             * taskId : 3
             */

            private int id;
            private int    placeId;
            private String placeName;
            private int    taskId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPlaceId() {
                return placeId;
            }

            public void setPlaceId(int placeId) {
                this.placeId = placeId;
            }

            public String getPlaceName() {
                return placeName;
            }

            public void setPlaceName(String placeName) {
                this.placeName = placeName;
            }

            public int getTaskId() {
                return taskId;
            }

            public void setTaskId(int taskId) {
                this.taskId = taskId;
            }

            @Override
            public String toString() {
                return "PlaceListBean{" +
                        "id=" + id +
                        ", placeId=" + placeId +
                        ", placeName='" + placeName + '\'' +
                        ", taskId=" + taskId +
                        '}';
            }
        }

    }

    @Override
    public String toString() {
        return "TaskDetailBean{" +
                "msg='" + msg + '\'' +
                ", body=" + body +
                ", code=" + code +
                '}';
    }
}
