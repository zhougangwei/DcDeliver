package com.aihui.dcdeliver.commponent.jdaddressselector;

import java.util.ArrayList;


public interface SelectedListener {

    /**
     * 回调接口，根据选择深度，按顺序返回选择的对象。
     *
     * @param selectAbles 返回的数据
     */
    void onAddressSelected(ArrayList<ISelectAble> selectAbles);

    /**
     * 需要关闭当前的dialog
     */
  void closeDiaLog();
}
