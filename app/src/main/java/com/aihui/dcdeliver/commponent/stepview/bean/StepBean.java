package com.aihui.dcdeliver.commponent.stepview.bean;

/**
 * 日期：16/9/3 00:36
 * <p/>
 * 描述：
 */
public interface  StepBean
{
    public static final int STEP_UNDO = 1;//未完成  undo step
    public static final int STEP_CURRENT = 3;//正在进行 current step
    public static final int STEP_COMPLETED = 4;//已完成 completed step


    public String getName();



    public int getState();



    public String getTime();



}
