package com.aihui.dcdeliver.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/21 12:04
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/21$
 * @ 更新描述  ${TODO}
 */

public class SaveBean implements Serializable{
    /**
     * extList : [{"colName":"病人姓名","colType":"string","colValue":"333","id":1,"required":1,"taskId":3}]
     * taskRecord : {"deadline":"2017-12-20 17:50:47","endPlaceId":"12","endPlaceName":"门诊楼4-一楼","recordFrom":1,"recordNum":"1712201750430001","remark":"3333","startPlaceId":"20","startPlaceName":"测试位置","taskClassId":"3","taskClassName":"CT检查"}
     */

    private TaskRecordBean taskRecord;
    private List<TaskDetailBean.BodyBean.ExtListBean> extList;

    public TaskRecordBean getTaskRecord() {
        return taskRecord;
    }

    public void setTaskRecord(TaskRecordBean taskRecord) {
        this.taskRecord = taskRecord;
    }

    public List<TaskDetailBean.BodyBean.ExtListBean> getExtList() {
        return extList;
    }

    public void setExtList(List<TaskDetailBean.BodyBean.ExtListBean> extList) {
        this.extList = extList;
    }

    public static class TaskRecordBean implements Serializable {
        /**
         * deadline : 2017-12-20 17:50:47
         * endPlaceId : 12
         * endPlaceName : 门诊楼4-一楼
         * recordFrom : 1
         * recordNum : 1712201750430001
         * remark : 3333
         * startPlaceId : 20
         * startPlaceName : 测试位置
         * taskClassId : 3
         * taskClassName : CT检查
         */

        private String deadline;
        private String endPlaceId;
        private String endPlaceName;
        private int    recordFrom;
        private String recordNum;
        private String remark;
        private String startPlaceId;
        private String startPlaceName;
        private String taskClassId;
        private String taskClassName;

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getEndPlaceId() {
            return endPlaceId;
        }

        public void setEndPlaceId(String endPlaceId) {
            this.endPlaceId = endPlaceId;
        }

        public String getEndPlaceName() {
            return endPlaceName;
        }

        public void setEndPlaceName(String endPlaceName) {
            this.endPlaceName = endPlaceName;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getStartPlaceId() {
            return startPlaceId;
        }

        public void setStartPlaceId(String startPlaceId) {
            this.startPlaceId = startPlaceId;
        }

        public String getStartPlaceName() {
            return startPlaceName;
        }

        public void setStartPlaceName(String startPlaceName) {
            this.startPlaceName = startPlaceName;
        }

        public String getTaskClassId() {
            return taskClassId;
        }

        public void setTaskClassId(String taskClassId) {
            this.taskClassId = taskClassId;
        }

        public String getTaskClassName() {
            return taskClassName;
        }

        public void setTaskClassName(String taskClassName) {
            this.taskClassName = taskClassName;
        }
    }


}
