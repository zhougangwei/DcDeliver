package com.aihui.dcdeliver.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/21 14:17
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/21$
 * @ 更新描述  ${TODO}
 */

public class RecordListBean implements Serializable {
    /**
     * msg : success
     * body : {"total":4,"list":[{"adviceStart":null,"createUser":1,"deadline":"2017-12-20 17:43:26","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-20 17:43:30","gmtModify":"2017-12-20 17:43:30","id":3,"modifyUser":1,"predictTime":null,"recordFrom":1,"recordNum":"1712201743200004","recordType":null,"remark":"333","startPlaceId":20,"startPlaceName":"测试位置","status":1,"statusText":"申请中","taskClassId":3,"taskClassName":"3","toolId":null,"toolName":""},{"adviceStart":null,"createUser":1,"deadline":"2017-12-20 17:43:41","endPlaceId":19,"endPlaceName":"住院楼","gmtCreate":"2017-12-20 17:43:45","gmtModify":"2017-12-20 17:43:45","id":4,"modifyUser":1,"predictTime":null,"recordFrom":1,"recordNum":"1712201743380005","recordType":null,"remark":"","startPlaceId":20,"startPlaceName":"测试位置","status":1,"statusText":"申请中","taskClassId":2,"taskClassName":"2","toolId":null,"toolName":""},{"adviceStart":null,"createUser":1,"deadline":"2017-12-20 17:50:47","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-20 17:50:51","gmtModify":"2017-12-20 17:50:51","id":5,"modifyUser":1,"predictTime":null,"recordFrom":1,"recordNum":"1712201750430001","recordType":null,"remark":"3333","startPlaceId":20,"startPlaceName":"测试位置","status":1,"statusText":"申请中","taskClassId":3,"taskClassName":"CT检查","toolId":null,"toolName":""},{"adviceStart":null,"createUser":1,"deadline":"2015-01-01 00:00:00","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-21 13:54:29","gmtModify":"2017-12-21 13:54:29","id":6,"modifyUser":1,"predictTime":null,"recordFrom":2,"recordNum":"123412","recordType":null,"remark":"123412","startPlaceId":19,"startPlaceName":"住院楼","status":1,"statusText":"申请中","taskClassId":3,"taskClassName":"CT检查","toolId":null,"toolName":""}]}
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
         * total : 4
         * list : [{"adviceStart":null,"createUser":1,"deadline":"2017-12-20 17:43:26","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-20 17:43:30","gmtModify":"2017-12-20 17:43:30","id":3,"modifyUser":1,"predictTime":null,"recordFrom":1,"recordNum":"1712201743200004","recordType":null,"remark":"333","startPlaceId":20,"startPlaceName":"测试位置","status":1,"statusText":"申请中","taskClassId":3,"taskClassName":"3","toolId":null,"toolName":""},{"adviceStart":null,"createUser":1,"deadline":"2017-12-20 17:43:41","endPlaceId":19,"endPlaceName":"住院楼","gmtCreate":"2017-12-20 17:43:45","gmtModify":"2017-12-20 17:43:45","id":4,"modifyUser":1,"predictTime":null,"recordFrom":1,"recordNum":"1712201743380005","recordType":null,"remark":"","startPlaceId":20,"startPlaceName":"测试位置","status":1,"statusText":"申请中","taskClassId":2,"taskClassName":"2","toolId":null,"toolName":""},{"adviceStart":null,"createUser":1,"deadline":"2017-12-20 17:50:47","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-20 17:50:51","gmtModify":"2017-12-20 17:50:51","id":5,"modifyUser":1,"predictTime":null,"recordFrom":1,"recordNum":"1712201750430001","recordType":null,"remark":"3333","startPlaceId":20,"startPlaceName":"测试位置","status":1,"statusText":"申请中","taskClassId":3,"taskClassName":"CT检查","toolId":null,"toolName":""},{"adviceStart":null,"createUser":1,"deadline":"2015-01-01 00:00:00","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-21 13:54:29","gmtModify":"2017-12-21 13:54:29","id":6,"modifyUser":1,"predictTime":null,"recordFrom":2,"recordNum":"123412","recordType":null,"remark":"123412","startPlaceId":19,"startPlaceName":"住院楼","status":1,"statusText":"申请中","taskClassId":3,"taskClassName":"CT检查","toolId":null,"toolName":""}]
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * adviceStart : null
             * createUser : 1
             * deadline : 2017-12-20 17:43:26
             * endPlaceId : 12
             * endPlaceName : 门诊楼4-一楼
             * gmtCreate : 2017-12-20 17:43:30
             * gmtModify : 2017-12-20 17:43:30
             * id : 3
             * modifyUser : 1
             * predictTime : null
             * recordFrom : 1
             * recordNum : 1712201743200004
             * recordType : null
             * remark : 333
             * startPlaceId : 20
             * startPlaceName : 测试位置
             * status : 1
             * statusText : 申请中
             * taskClassId : 3
             * taskClassName : 3
             * toolId : null
             * toolName :
             */

            private String adviceStart;
            private int    createUser;
            private String deadline;
            private int    endPlaceId;
            private String endPlaceName;
            private String gmtCreate;
            private String gmtModify;
            private int    id;
            private int    modifyUser;
            private String predictTime;
            private int    recordFrom;
            private String recordNum;
            private String recordType;
            private String remark;
            private int    startPlaceId;
            private String startPlaceName;
            private int    status;
            private String statusText;
            private int    taskClassId;
            private String taskClassName;
            private String toolId;
            private String toolName;

            public String getAdviceStart() {
                return adviceStart;
            }

            public void setAdviceStart(String adviceStart) {
                this.adviceStart = adviceStart;
            }

            public int getCreateUser() {
                return createUser;
            }

            public void setCreateUser(int createUser) {
                this.createUser = createUser;
            }

            public String getDeadline() {
                return deadline;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }

            public int getEndPlaceId() {
                return endPlaceId;
            }

            public void setEndPlaceId(int endPlaceId) {
                this.endPlaceId = endPlaceId;
            }

            public String getEndPlaceName() {
                return endPlaceName;
            }

            public void setEndPlaceName(String endPlaceName) {
                this.endPlaceName = endPlaceName;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getModifyUser() {
                return modifyUser;
            }

            public void setModifyUser(int modifyUser) {
                this.modifyUser = modifyUser;
            }

            public String getPredictTime() {
                return predictTime;
            }

            public void setPredictTime(String predictTime) {
                this.predictTime = predictTime;
            }

            public int getRecordFrom() {
                return recordFrom;
            }

            public void setRecordFrom(int recordFrom) {
                this.recordFrom = recordFrom;
            }

            public String getRecordNum() {
                return recordNum;
            }

            public void setRecordNum(String recordNum) {
                this.recordNum = recordNum;
            }

            public String getRecordType() {
                return recordType;
            }

            public void setRecordType(String recordType) {
                this.recordType = recordType;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getStartPlaceId() {
                return startPlaceId;
            }

            public void setStartPlaceId(int startPlaceId) {
                this.startPlaceId = startPlaceId;
            }

            public String getStartPlaceName() {
                return startPlaceName;
            }

            public void setStartPlaceName(String startPlaceName) {
                this.startPlaceName = startPlaceName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStatusText() {
                return statusText;
            }

            public void setStatusText(String statusText) {
                this.statusText = statusText;
            }

            public int getTaskClassId() {
                return taskClassId;
            }

            public void setTaskClassId(int taskClassId) {
                this.taskClassId = taskClassId;
            }

            public String getTaskClassName() {
                return taskClassName;
            }

            public void setTaskClassName(String taskClassName) {
                this.taskClassName = taskClassName;
            }

            public String getToolId() {
                return toolId;
            }

            public void setToolId(String toolId) {
                this.toolId = toolId;
            }

            public String getToolName() {
                return toolName;
            }

            public void setToolName(String toolName) {
                this.toolName = toolName;
            }
        }
    }
}
