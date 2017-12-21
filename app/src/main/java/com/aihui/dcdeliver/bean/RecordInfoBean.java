package com.aihui.dcdeliver.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/21 16:17
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/21$
 * @ 更新描述  ${TODO}
 */

public class RecordInfoBean implements  Serializable{
    /**
     * msg : success
     * body : {"detailList":[],"extList":[{"colName":"病人姓名","colType":"string","colValue":"333","id":3,"recordId":3,"required":1,"taskId":3}],"taskRecord":{"adviceStart":null,"createUser":1,"createUserName":"admin","deadline":"2017-12-20 17:43:26","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-20 17:43:30","gmtModified":"2017-12-20 17:43:30","id":3,"modifiedUser":1,"predictTime":null,"receiveUser":null,"receiveUserName":"","recordFrom":1,"recordNum":"1712201743200004","recordType":null,"remark":"333","startPlaceId":20,"startPlaceName":"测试位置","status":1,"statusText":"申请中","taskClassId":3,"taskClassName":"3","toolId":null,"toolName":""},"taskRecordTime":null}
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

    public static class BodyBean implements Serializable{
        /**
         * detailList : []
         * extList : [{"colName":"病人姓名","colType":"string","colValue":"333","id":3,"recordId":3,"required":1,"taskId":3}]
         * taskRecord : {"adviceStart":null,"createUser":1,"createUserName":"admin","deadline":"2017-12-20 17:43:26","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-20 17:43:30","gmtModified":"2017-12-20 17:43:30","id":3,"modifiedUser":1,"predictTime":null,"receiveUser":null,"receiveUserName":"","recordFrom":1,"recordNum":"1712201743200004","recordType":null,"remark":"333","startPlaceId":20,"startPlaceName":"测试位置","status":1,"statusText":"申请中","taskClassId":3,"taskClassName":"3","toolId":null,"toolName":""}
         * taskRecordTime : null
         */

        private TaskRecordBean taskRecord;
        private Object            taskRecordTime;
        private List<?>           detailList;
        private List<ExtListBean> extList;

        public TaskRecordBean getTaskRecord() {
            return taskRecord;
        }

        public void setTaskRecord(TaskRecordBean taskRecord) {
            this.taskRecord = taskRecord;
        }

        public Object getTaskRecordTime() {
            return taskRecordTime;
        }

        public void setTaskRecordTime(Object taskRecordTime) {
            this.taskRecordTime = taskRecordTime;
        }

        public List<?> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<?> detailList) {
            this.detailList = detailList;
        }

        public List<ExtListBean> getExtList() {
            return extList;
        }

        public void setExtList(List<ExtListBean> extList) {
            this.extList = extList;
        }

        public static class TaskRecordBean implements Serializable {
            /**
             * adviceStart : null
             * createUser : 1
             * createUserName : admin
             * deadline : 2017-12-20 17:43:26
             * endPlaceId : 12
             * endPlaceName : 门诊楼4-一楼
             * gmtCreate : 2017-12-20 17:43:30
             * gmtModified : 2017-12-20 17:43:30
             * id : 3
             * modifiedUser : 1
             * predictTime : null
             * receiveUser : null
             * receiveUserName :
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

            private Object adviceStart;
            private int    createUser;
            private String createUserName;
            private String deadline;
            private int    endPlaceId;
            private String endPlaceName;
            private String gmtCreate;
            private String gmtModified;
            private int    id;
            private int    modifiedUser;
            private Object predictTime;
            private Object receiveUser;
            private String receiveUserName;
            private int    recordFrom;
            private String recordNum;
            private Object recordType;
            private String remark;
            private int    startPlaceId;
            private String startPlaceName;
            private int    status;
            private String statusText;
            private int    taskClassId;
            private String taskClassName;
            private Object toolId;
            private String toolName;

            public Object getAdviceStart() {
                return adviceStart;
            }

            public void setAdviceStart(Object adviceStart) {
                this.adviceStart = adviceStart;
            }

            public int getCreateUser() {
                return createUser;
            }

            public void setCreateUser(int createUser) {
                this.createUser = createUser;
            }

            public String getCreateUserName() {
                return createUserName;
            }

            public void setCreateUserName(String createUserName) {
                this.createUserName = createUserName;
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

            public String getGmtModified() {
                return gmtModified;
            }

            public void setGmtModified(String gmtModified) {
                this.gmtModified = gmtModified;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getModifiedUser() {
                return modifiedUser;
            }

            public void setModifiedUser(int modifiedUser) {
                this.modifiedUser = modifiedUser;
            }

            public Object getPredictTime() {
                return predictTime;
            }

            public void setPredictTime(Object predictTime) {
                this.predictTime = predictTime;
            }

            public Object getReceiveUser() {
                return receiveUser;
            }

            public void setReceiveUser(Object receiveUser) {
                this.receiveUser = receiveUser;
            }

            public String getReceiveUserName() {
                return receiveUserName;
            }

            public void setReceiveUserName(String receiveUserName) {
                this.receiveUserName = receiveUserName;
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

            public Object getRecordType() {
                return recordType;
            }

            public void setRecordType(Object recordType) {
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

            public Object getToolId() {
                return toolId;
            }

            public void setToolId(Object toolId) {
                this.toolId = toolId;
            }

            public String getToolName() {
                return toolName;
            }

            public void setToolName(String toolName) {
                this.toolName = toolName;
            }
        }

        public static class ExtListBean implements Serializable{
            /**
             * colName : 病人姓名
             * colType : string
             * colValue : 333
             * id : 3
             * recordId : 3
             * required : 1
             * taskId : 3
             */

            private String colName;
            private String colType;
            private String colValue;
            private int    id;
            private int    recordId;
            private int    required;
            private int    taskId;

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

            public String getColValue() {
                return colValue;
            }

            public void setColValue(String colValue) {
                this.colValue = colValue;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getRecordId() {
                return recordId;
            }

            public void setRecordId(int recordId) {
                this.recordId = recordId;
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
        }
    }
}
