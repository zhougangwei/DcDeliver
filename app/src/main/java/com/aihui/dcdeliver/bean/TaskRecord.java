package com.aihui.dcdeliver.bean;


import java.util.Date;

public class TaskRecord {

    private Integer id;

    /**
     * 记录单号
     */
    private String recordNum;

    /**
     * 记录状态
     */
    private Integer status;

    /**
     * 单子类型(临时、计划)
     */
    private Integer recordType;

    /**
     * 来源
     */
    private Integer recordFrom;

    /**
     * 任务类型ID
     */
    private Integer taskClass;

    /**
     * 任务类型名称
     */
    private String taskName;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 开始地点ID
     */
    private Integer startPlaceId;

    /**
     * 开始地点名称
     */
    private String startPlaceName;

    /**
     * 结束地点ID
     */
    private Integer endPlaceId;

    /**
     * 结束地点名称
     */
    private String endPlaceName;

    /**
     * 截至时间
     */
    private Date deadline;

    /**
     * 建议开始时间
     */
    private Date adviceStart;

    /**
     * 预估时间（小时）
     */
    private Double predictTime;

    /**
     * 运送工具ID
     */
    private Integer toolId;

    /**
     * 运送工具名称
     */
    private String toolName;

    private Date gmtCreate;

    private Integer createUser;

    private Date gmtModify;

    private Integer modifyUser;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取记录单号
     *
     * @return record_num - 记录单号
     */
    public String getRecordNum() {
        return recordNum;
    }

    /**
     * 设置记录单号
     *
     * @param recordNum 记录单号
     */
    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }

    /**
     * 获取记录状态
     *
     * @return status - 记录状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置记录状态
     *
     * @param status 记录状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取单子类型(临时、计划)
     *
     * @return record_type - 单子类型(临时、计划)
     */
    public Integer getRecordType() {
        return recordType;
    }

    /**
     * 设置单子类型(临时、计划)
     *
     * @param recordType 单子类型(临时、计划)
     */
    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    /**
     * 获取来源
     *
     * @return record_from - 来源
     */
    public Integer getRecordFrom() {
        return recordFrom;
    }

    /**
     * 设置来源
     *
     * @param recordFrom 来源
     */
    public void setRecordFrom(Integer recordFrom) {
        this.recordFrom = recordFrom;
    }

    /**
     * 获取任务类型ID
     *
     * @return task_class - 任务类型ID
     */
    public Integer getTaskClass() {
        return taskClass;
    }

    /**
     * 设置任务类型ID
     *
     * @param taskClass 任务类型ID
     */
    public void setTaskClass(Integer taskClass) {
        this.taskClass = taskClass;
    }

    /**
     * 获取任务类型名称
     *
     * @return task_name - 任务类型名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务类型名称
     *
     * @param taskName 任务类型名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取备注信息
     *
     * @return remark - 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     *
     * @param remark 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取开始地点ID
     *
     * @return start_place_id - 开始地点ID
     */
    public Integer getStartPlaceId() {
        return startPlaceId;
    }

    /**
     * 设置开始地点ID
     *
     * @param startPlaceId 开始地点ID
     */
    public void setStartPlaceId(Integer startPlaceId) {
        this.startPlaceId = startPlaceId;
    }

    /**
     * 获取开始地点名称
     *
     * @return start_place_name - 开始地点名称
     */
    public String getStartPlaceName() {
        return startPlaceName;
    }

    /**
     * 设置开始地点名称
     *
     * @param startPlaceName 开始地点名称
     */
    public void setStartPlaceName(String startPlaceName) {
        this.startPlaceName = startPlaceName;
    }

    /**
     * 获取结束地点ID
     *
     * @return end_place_id - 结束地点ID
     */
    public Integer getEndPlaceId() {
        return endPlaceId;
    }

    /**
     * 设置结束地点ID
     *
     * @param endPlaceId 结束地点ID
     */
    public void setEndPlaceId(Integer endPlaceId) {
        this.endPlaceId = endPlaceId;
    }

    /**
     * 获取结束地点名称
     *
     * @return end_place_name - 结束地点名称
     */
    public String getEndPlaceName() {
        return endPlaceName;
    }

    /**
     * 设置结束地点名称
     *
     * @param endPlaceName 结束地点名称
     */
    public void setEndPlaceName(String endPlaceName) {
        this.endPlaceName = endPlaceName;
    }

    /**
     * 获取截至时间
     *
     * @return deadline - 截至时间
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * 设置截至时间
     *
     * @param deadline 截至时间
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * 获取建议开始时间
     *
     * @return advice_start - 建议开始时间
     */
    public Date getAdviceStart() {
        return adviceStart;
    }

    /**
     * 设置建议开始时间
     *
     * @param adviceStart 建议开始时间
     */
    public void setAdviceStart(Date adviceStart) {
        this.adviceStart = adviceStart;
    }

    /**
     * 获取预估时间（小时）
     *
     * @return predict_time - 预估时间（小时）
     */
    public Double getPredictTime() {
        return predictTime;
    }

    /**
     * 设置预估时间（小时）
     *
     * @param predictTime 预估时间（小时）
     */
    public void setPredictTime(Double predictTime) {
        this.predictTime = predictTime;
    }

    /**
     * 获取运送工具ID
     *
     * @return tool_id - 运送工具ID
     */
    public Integer getToolId() {
        return toolId;
    }

    /**
     * 设置运送工具ID
     *
     * @param toolId 运送工具ID
     */
    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    /**
     * 获取运送工具名称
     *
     * @return tool_name - 运送工具名称
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * 设置运送工具名称
     *
     * @param toolName 运送工具名称
     */
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    /**
     * @return gmt_create
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return create_user
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * @return gmt_modify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * @param gmtModify
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * @return modify_user
     */
    public Integer getModifyUser() {
        return modifyUser;
    }

    /**
     * @param modifyUser
     */
    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }
}