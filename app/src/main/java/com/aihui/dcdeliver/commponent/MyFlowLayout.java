package com.aihui.dcdeliver.commponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by orchid
 * on 16-11-2.
 */

public class MyFlowLayout extends ViewGroup {

    private int useWidth;

    private int MaxHeight;

    private int HorizonytalSpace = 5;

    private int VerticalSpaace = 5;

    private Line mLine;

    private int MaxLine = 100;

    private ArrayList<Line> LineList = new ArrayList<Line>();
//    private  int

    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //获取控件的具体尺寸
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();//获取空间的宽度
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();//获取控件的高度
        //获取控件的测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//宽度的测量模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//高度的测量模式

        //开始遍历所有的子控件
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //获取子控件的尺寸，与测量模式
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width,widthMode == MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:widthMode);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,heightMode == MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:heightMode);
            //测量子控件
            childView.measure(childWidthMeasureSpec,childHeightMeasureSpec);

            int childWidth = childView.getMeasuredWidth();//子控件的宽度
            int childHeight = childView.getMeasuredHeight();//子控件的高度

            useWidth += childWidth;

            if(mLine == null){
                mLine = new Line();
            }

            if(useWidth < width){
                //未超过最大限度，可以添加到当前行
                    mLine.addView(childView);
                    useWidth += HorizonytalSpace;

                if(useWidth >= width){
                    if(!newLine()){
                        break;//创建失败，结束for循环
                    }
                }
            }else {
                //2.但前行没有控件，必须加入到当前行，然后换行
                if(mLine.getLineCount() == 0){
                    //添加到当前行，然后换行
                    mLine.addView(childView);
                    LineList.add(mLine);
                    if(!newLine()){
                        break;
                    }

                }else {
                    //超过最大高度，
                    //1.当前行有控件，需要新建一行
                    if(!newLine()){
                        break;
                    }
                    mLine.addView(childView);
                    useWidth += HorizonytalSpace + childWidth;
                }
            }
            if (mLine != null && mLine.getLineCount() > 0
                    && !LineList.contains(mLine)) {
                // 由于前面采用判断长度是否超过最大宽度来决定是否换行，则最后一行可能因为还没达到最大宽度，所以需要验证后加入集合中
                LineList.add(mLine);
            }

        }
        //为控件设置宽度，高度
        int Totalwidth = MeasureSpec.getSize(widthMeasureSpec);
        int TotalHeight = 0;
        for (int i = 0; i <LineList.size() ; i++) {
            TotalHeight += LineList.get(i).MaxHeight;
        }
        TotalHeight += (LineList.size() - 1)*VerticalSpaace + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(Totalwidth,TotalHeight);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历行集合(LineList)，
        int Top = getPaddingTop();
        int Left = getPaddingLeft();
        for (int i = 0; i < LineList.size(); i++) {
            Line line = LineList.get(i);

            line.layoutView(Left,Top);

            Top += line.MaxHeight + VerticalSpaace;
        }
    }

    private boolean newLine(){
        //判断是否超过最大行数
        if(LineList.size() < MaxLine){
            //将上一行添加到，LineList中
            LineList.add(mLine);
            mLine = new Line();//创建一个新的行
            //新的一行，使用的数据为0
            useWidth = 0;
            MaxHeight = 0;
            return true;//创建成功返回true
        }
        return false;
    }

    //创建一个类，用来处理每一行的数据
    class Line{

        private int mLineWidth = 0;

        private int MaxHeight = 0;

        private ArrayList<View> viewlist = new ArrayList<View>();

        public void addView(View view){

            viewlist.add(view);
            mLineWidth += view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();
            MaxHeight = MaxHeight < childHeight?childHeight:MaxHeight;

        }

        public int getLineCount(){
            return viewlist.size();
        }

        public void layoutView(int l,int t){
            //对此行的数据进行布局
            int Left = l;
            int Top = t;

            int childCount = viewlist.size();
            int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() -(childCount-1) * HorizonytalSpace;

            //计算剩余宽度
            int surplusWidth  = width - mLineWidth;
            if(surplusWidth > 0){
                //计算每个布局的添加量
                int widthOffSet = (int) (surplusWidth * 1.0f/viewlist.size() + 0.5f);

                for (int i = 0; i < viewlist.size(); i++) {

                    View view = viewlist.get(i);
                    int childWidth = view.getMeasuredWidth();
                    int childHeight = view.getMeasuredHeight();

                    childWidth += widthOffSet;//重新分配控件的高度

                    int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY);
                    int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY);

                    view.measure(childWidthMeasureSpec,childHeightMeasureSpec);
                    //分配布局控件时的偏移量
                    int TopOffSet = (MaxHeight - childHeight) / 2;
                    TopOffSet = TopOffSet>0?TopOffSet:0;//如果TopOffSet（竖直方向的偏移量）小于0，则设置为0;
                    view.layout(Left,Top+ TopOffSet,Left +childWidth,Top + TopOffSet + childHeight);

                    Left += HorizonytalSpace + childWidth;
                }
            }else{
            }
        }
    }

    public void setHorizontalSpacing(int horizonytalSpace) {
        HorizonytalSpace = horizonytalSpace;
    }

    public void setVerticalSpacing(int verticalSpaace) {
        VerticalSpaace = verticalSpaace;
    }
}