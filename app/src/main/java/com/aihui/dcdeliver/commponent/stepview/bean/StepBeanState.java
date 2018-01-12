package com.aihui.dcdeliver.commponent.stepview.bean;

/**
 * 日期：16/9/3 00:36
 * <p/>
 * 描述：
 */
public class StepBeanState implements StepBean {


    String name;
    int    state;
    String time;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public StepBeanState() {
    }

    public StepBeanState(String name, int state) {
        this.name = name;
        this.state = state;
    }

    public StepBeanState(String name, String time) {
        this.name = name;
        this.time = time;
    }
}
