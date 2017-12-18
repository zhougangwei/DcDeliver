package com.aihui.dcdeliver.bean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/14 13:58
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/14$
 * @ 更新描述  ${TODO}
 */

public class LoginBean {

    /**
     * msg : success
     * body : {"account":"admin","deptId":1,"deptName":"顶级机构","email":"","id":1,"orgId":1,"orgName":"顶级机构","phoneNum":"12345678910","status":1,"userName":"admin","userType":null}
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

    public static class BodyBean {
        /**
         * account : admin
         * deptId : 1
         * deptName : 顶级机构
         * email :
         * id : 1
         * orgId : 1
         * orgName : 顶级机构
         * phoneNum : 12345678910
         * status : 1
         * userName : admin
         * userType : null
         */

        private String account;
        private int    deptId;
        private String deptName;
        private String email;
        private int    id;
        private int    orgId;
        private String orgName;
        private String phoneNum;
        private int    status;
        private String userName;
        private Object userType;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
            this.userType = userType;
        }
    }
}
