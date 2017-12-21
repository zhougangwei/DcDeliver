package com.aihui.dcdeliver.commponent.jdaddressselector;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/8/1 14:05
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/8/1$
 * @ 更新描述  ${TODO}
 */

public class SelectAbleDataHelper {


    public ArrayList<ISelectAble> selectAbles;

    String mShortName;
    String mShortIds;
    String mLongIds;
    String mLongName;


    //数据转化分析
    public SelectAbleDataHelper() {
    }

    public void solveDatas(ArrayList<ISelectAble> selectAbles) {
        String result = "";
        StringBuilder sbids = new StringBuilder();
        String arg = "";
        sbids.append("(");

        for (int i = 0; i < selectAbles.size(); i++) {

            ISelectAble selectAble = selectAbles.get(i);
            if (i != selectAbles.size() - 1) {
                arg = selectAble.getArg();
                String name = selectAble.getName();
                int id = selectAble.getId();
                result += name + "-";
                sbids.append(id).append(",");
            } else {
                arg = selectAble.getArg();
                String name = selectAble.getName();
                int id = selectAble.getId();
                result += name;
                sbids.append(id);
            }
        }
        sbids.append(")");
        if (selectAbles != null && selectAbles.size() > 0) {
            ISelectAble iSelectAble = selectAbles.get(selectAbles.size() - 1);
            mShortName = iSelectAble.getName();
            mShortIds = iSelectAble.getId() + "";


        }

        if (!TextUtils.isEmpty(result)) {

            mLongName = result.substring(0, result.length());

        }
        String ids = sbids.toString();
        if (ids.length() != 2) {              //说明只有()
            mLongIds = ids;
        }


    }

    public ArrayList<ISelectAble> getSelectAbles() {
        return selectAbles;
    }





    public String getShortName() {
        return mShortName;
    }


    public String getShortIds() {
        return mShortIds;
    }




    public String getLongIds() {
        return mLongIds;
    }


    public String getLongName() {
        return mLongName;
    }



}
