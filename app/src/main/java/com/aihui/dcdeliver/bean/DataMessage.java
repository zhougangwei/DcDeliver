package com.aihui.dcdeliver.bean;

public class DataMessage{
        //总数
        int total;
        //是否报错
        boolean isErr;

        //当前数目
        int currentNum;

        public DataMessage(int total, boolean isErr) {
            this.total = total;
            this.isErr = isErr;
        }

        public DataMessage() {
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public boolean isErr() {
            return isErr;
        }

        public void setErr(boolean err) {
            isErr = err;
        }


    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }
}