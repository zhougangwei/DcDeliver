package com.aihui.dcdeliver.bean;

import com.aihui.dcdeliver.commponent.stepview.bean.StepBean;

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
     * body : {"detailList":[{"detailTime":"2017-12-21 16:22:00","id":1,"isDel":0,"placeCode":"","placeId":null,"placeName":"","recordId":5,"type":1,"userId":1,"userName":"admin"}],"extList":[{"colName":"病人姓名","colType":"string","colValue":"333","id":4,"recordId":5,"required":1,"taskId":3}],"taskRecord":{"adviceStart":null,"createUser":1,"createUserName":"admin","deadline":"2017-12-20 17:50:47","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-20 17:50:51","gmtModified":"2017-12-20 17:50:51","id":5,"modifiedUser":1,"predictTime":null,"receiveUser":1,"receiveUserName":"admin","recordFrom":1,"recordNum":"1712201750430001","recordType":null,"remark":"3333","startPlaceId":20,"startPlaceName":"测试位置","status":2,"statusText":"已接单","taskClassId":3,"taskClassName":"CT检查","toolId":null,"toolName":""},"taskRecordTime":{"endTime":null,"receiveTime":"2017-12-21 16:22:00","receiveUser":1,"receiveUserName":"admin","recordId":5,"startTime":null}}
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

    @Override
    public String toString() {
        return "RecordInfoBean{" +
                "msg='" + msg + '\'' +
                ", body=" + body +
                ", code=" + code +
                '}';
    }

    public static class BodyBean implements Serializable{
        /**
         * detailList : [{"detailTime":"2017-12-21 16:22:00","id":1,"isDel":0,"placeCode":"","placeId":null,"placeName":"","recordId":5,"type":1,"userId":1,"userName":"admin"}]
         * extList : [{"colName":"病人姓名","colType":"string","colValue":"333","id":4,"recordId":5,"required":1,"taskId":3}]
         * taskRecord : {"adviceStart":null,"createUser":1,"createUserName":"admin","deadline":"2017-12-20 17:50:47","endPlaceId":12,"endPlaceName":"门诊楼4-一楼","gmtCreate":"2017-12-20 17:50:51","gmtModified":"2017-12-20 17:50:51","id":5,"modifiedUser":1,"predictTime":null,"receiveUser":1,"receiveUserName":"admin","recordFrom":1,"recordNum":"1712201750430001","recordType":null,"remark":"3333","startPlaceId":20,"startPlaceName":"测试位置","status":2,"statusText":"已接单","taskClassId":3,"taskClassName":"CT检查","toolId":null,"toolName":""}
         * taskRecordTime : {"endTime":null,"receiveTime":"2017-12-21 16:22:00","receiveUser":1,"receiveUserName":"admin","recordId":5,"startTime":null}
         */

        private TaskRecordBean taskRecord;
        private TaskRecordTimeBean   taskRecordTime;
        private List<DetailListBean> detailList;
        private List<ExtListBean>    extList;

        public TaskRecordBean getTaskRecord() {
            return taskRecord;
        }

        public void setTaskRecord(TaskRecordBean taskRecord) {
            this.taskRecord = taskRecord;
        }

        public TaskRecordTimeBean getTaskRecordTime() {
            return taskRecordTime;
        }

        public void setTaskRecordTime(TaskRecordTimeBean taskRecordTime) {
            this.taskRecordTime = taskRecordTime;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public List<ExtListBean> getExtList() {
            return extList;
        }

        public void setExtList(List<ExtListBean> extList) {
            this.extList = extList;
        }

        public static class TaskRecordBean implements Serializable,StepBean{
            @Override
            public String toString() {
                return "TaskRecordBean{" +
                        "adviceStart='" + adviceStart + '\'' +
                        ", createUser=" + createUser +
                        ", createUserName='" + createUserName + '\'' +
                        ", deadline='" + deadline + '\'' +
                        ", endPlaceId=" + endPlaceId +
                        ", endPlaceName='" + endPlaceName + '\'' +
                        ", gmtCreate='" + gmtCreate + '\'' +
                        ", gmtModified='" + gmtModified + '\'' +
                        ", id=" + id +
                        ", modifiedUser=" + modifiedUser +
                        ", predictTime='" + predictTime + '\'' +
                        ", receiveUser=" + receiveUser +
                        ", receiveUserName='" + receiveUserName + '\'' +
                        ", recordFrom=" + recordFrom +
                        ", recordNum='" + recordNum + '\'' +
                        ", recordType='" + recordType + '\'' +
                        ", remark='" + remark + '\'' +
                        ", startPlaceId=" + startPlaceId +
                        ", startPlaceName='" + startPlaceName + '\'' +
                        ", status=" + status +
                        ", statusText='" + statusText + '\'' +
                        ", taskClassId=" + taskClassId +
                        ", taskClassName='" + taskClassName + '\'' +
                        ", toolId='" + toolId + '\'' +
                        ", toolName='" + toolName + '\'' +
                        '}';
            }

            /**
             * adviceStart : null
             * createUser : 1
             * createUserName : admin
             * deadline : 2017-12-20 17:50:47
             * endPlaceId : 12
             * endPlaceName : 门诊楼4-一楼
             * gmtCreate : 2017-12-20 17:50:51
             * gmtModified : 2017-12-20 17:50:51
             * id : 5
             * modifiedUser : 1
             * predictTime : null
             * receiveUser : 1
             * receiveUserName : admin
             * recordFrom : 1
             * recordNum : 1712201750430001
             * recordType : null
             * remark : 3333
             * startPlaceId : 20
             * startPlaceName : 测试位置
             * status : 2
             * statusText : 已接单
             * taskClassId : 3
             * taskClassName : CT检查
             * toolId : null
             * toolName :
             */



            private String adviceStart;
            private int    createUser;
            private String createUserName;
            private String deadline;
            private int    endPlaceId;
            private String endPlaceName;
            private String gmtCreate;
            private String gmtModified;
            private int    id;
            private int    modifiedUser;
            private String predictTime;
            private int    receiveUser;
            private String receiveUserName;
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

            public String getPredictTime() {
                return predictTime;
            }

            public void setPredictTime(String predictTime) {
                this.predictTime = predictTime;
            }

            public int getReceiveUser() {
                return receiveUser;
            }

            public void setReceiveUser(int receiveUser) {
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

            @Override
            public String getName() {
                return "运送单发布成功";
            }

            @Override
            public int getState() {
                return 1;
            }

            @Override
            public String getTime() {
                return gmtCreate;
            }
        }

        public static class TaskRecordTimeBean implements Serializable {
            /**
             * endTime : null
             * receiveTime : 2017-12-21 16:22:00
             * receiveUser : 1
             * receiveUserName : admin
             * recordId : 5
             * startTime : null
             */

            private String endTime;
            private String receiveTime;
            private int    receiveUser;
            private String receiveUserName;
            private int    recordId;
            private String startTime;

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getReceiveTime() {
                return receiveTime;
            }

            public void setReceiveTime(String receiveTime) {
                this.receiveTime = receiveTime;
            }

            public int getReceiveUser() {
                return receiveUser;
            }

            public void setReceiveUser(int receiveUser) {
                this.receiveUser = receiveUser;
            }

            public String getReceiveUserName() {
                return receiveUserName;
            }

            public void setReceiveUserName(String receiveUserName) {
                this.receiveUserName = receiveUserName;
            }

            public int getRecordId() {
                return recordId;
            }

            public void setRecordId(int recordId) {
                this.recordId = recordId;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
        }

        public static class DetailListBean implements Serializable , StepBean {
            /**
             * detailTime : 2017-12-21 16:22:00
             * id : 1
             * isDel : 0
             * placeCode :
             * placeId : null
             * placeName :
             * recordId : 5
             * type : 1
             * userId : 1
             * userName : admin
             */

            private String detailTime;
            private int    id;
            private int    isDel;
            private String placeCode;
            private String placeId;
            private String placeName;
            private int    recordId;
            private int    type;
            private int    userId;
            private String userName;

            public String getDetailTime() {
                return detailTime;
            }

            public void setDetailTime(String detailTime) {
                this.detailTime = detailTime;
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

            public String getPlaceCode() {
                return placeCode;
            }

            public void setPlaceCode(String placeCode) {
                this.placeCode = placeCode;
            }

            public String getPlaceId() {
                return placeId;
            }

            public void setPlaceId(String placeId) {
                this.placeId = placeId;
            }

            public String getPlaceName() {
                return placeName;
            }

            public void setPlaceName(String placeName) {
                this.placeName = placeName;
            }

            public int getRecordId() {
                return recordId;
            }

            public void setRecordId(int recordId) {
                this.recordId = recordId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }


            @Override
            public String toString() {
                return "DetailListBean{" +
                        "detailTime='" + detailTime + '\'' +
                        ", id=" + id +
                        ", isDel=" + isDel +
                        ", placeCode='" + placeCode + '\'' +
                        ", placeId='" + placeId + '\'' +
                        ", placeName='" + placeName + '\'' +
                        ", recordId=" + recordId +
                        ", type=" + type +
                        ", userId=" + userId +
                        ", userName='" + userName + '\'' +
                        '}';
            }

            @Override
            public String getName() {
                return this.placeName;
            }

            @Override
            public int getState() {
                return this.type;
            }

            @Override
            public String getTime() {
                return this.detailTime;
            }
        }

        public static class ExtListBean implements Serializable {
            /**
             * colName : 病人姓名
             * colType : string
             * colValue : 333
             * id : 4
             * recordId : 5
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

            @Override
            public String toString() {
                return "ExtListBean{" +
                        "colName='" + colName + '\'' +
                        ", colType='" + colType + '\'' +
                        ", colValue='" + colValue + '\'' +
                        ", id=" + id +
                        ", recordId=" + recordId +
                        ", required=" + required +
                        ", taskId=" + taskId +
                        '}';
            }
        }
    }
}
