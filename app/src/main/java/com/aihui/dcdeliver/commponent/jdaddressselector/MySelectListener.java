package com.aihui.dcdeliver.commponent.jdaddressselector;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/8/1 13:49
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/8/1$
 * @ 更新描述  ${TODO}
 */

public abstract class MySelectListener implements SelectedListener {
    String mDlwzName;
    String mDlwzIds;
    String mDeptName;
    String mDeptIds;

    String mAllDlwzIds;
    String mAllDeptIds;
    String mAllDeptName;
    String mAllDlwzName;

    @Override
    public void onAddressSelected(ArrayList<ISelectAble> selectAbles) {
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

            if ("dept".equals(arg)) {
                mDeptName = iSelectAble.getName();
                mDeptIds = iSelectAble.getId() + "";
            } else if ("location".equals(arg)) {
                mDlwzName = iSelectAble.getName();
                mDlwzIds = iSelectAble.getId() + "";
            }

        }

        if (!TextUtils.isEmpty(result)) {
            if ("dept".equals(arg)) {
                mAllDeptName = result.substring(0, result.length() - 1);

            } else if ("location".equals(arg)) {
                mAllDlwzName = result.substring(0, result.length() - 1);

            }
        }


        String ids = sbids.toString();
        if (ids.length() != 2) {              //说明只有()
            if ("dept".equals(arg)) {
                mAllDeptIds = ids;
            } else if ("location".equals(arg)) {
                mAllDlwzIds = ids;
            }
        }
    }







}

