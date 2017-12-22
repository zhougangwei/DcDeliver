package com.aihui.dcdeliver.commponent.stepview.bean;

/**
 * 日期：16/9/3 00:36
 * <p/>
 * 描述：
 */
public class StepBeansss
{
    public static final int STEP_UNDO = -1;//未完成  undo step
    public static final int STEP_CURRENT = 0;//正在进行 current step
    public static final int STEP_COMPLETED = 1;//已完成 completed step

    String name;
    int state;
    String time;


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public StepBeansss()
    {
    }

    public StepBeansss(String name, int state)
    {
        this.name = name;
        this.state = state;
    }

    public StepBeansss(String name, String time) {
        this.name = name;
        this.time = time;
    }
}
