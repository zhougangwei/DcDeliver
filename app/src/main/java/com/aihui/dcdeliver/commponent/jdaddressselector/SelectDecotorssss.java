package com.aihui.dcdeliver.commponent.jdaddressselector;

import android.content.DialogInterface;

import com.aihui.dcdeliver.base.BaseActivity;
import com.aihui.dcdeliver.base.BaseView;
import com.aihui.dcdeliver.bean.SelectBean;
import com.aihui.dcdeliver.bean.TaskTypeBean;
import com.aihui.dcdeliver.ui.presenter.SelectPresenter;
import com.aihui.dcdeliver.util.GsonUtil;
import com.aihui.dcdeliver.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/6/23 9:43
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/6/23$
 * @ 更新描述  ${TODO}
 */

public class SelectDecotorssss implements SelectedListener, SelectPresenter {


    private BottomDialog mDeptDialog;           //底部弹窗 科室或者地理位子的
    private BaseActivity mBaseActivity;
    private SelectBean   mCity;

    private Map<String, Object> mDatas;

    private OnBackSelectListener mOnBackSelectListener;


    public interface OnBackSelectListener {
        void onBackSelect(ArrayList<ISelectAble> selectAbles);
    }


    public SelectDecotorssss(BaseView baseView, Map<String, Object> datas) {
        this.mBaseActivity = baseView.getBaseActivity();
        this.mDatas = datas;
    }

    @Override
    public void showDialog() {
        mDeptDialog = new BottomDialog(mBaseActivity);
        Selector selector = new Selector(mBaseActivity, 3);
        //输入自己的数据
        selector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentDeep, int preId, final DataReceiver receiver) {
                if (currentDeep == 0) {
                    Object o = mDatas.get("0");
                    List<TaskTypeBean> objects = GsonUtil.parseJsonToList(GsonUtil.parseObjectToJson(o), TaskTypeBean.class);
                    List<ISelectAble> objects1 = new ArrayList<>();
                    objects1.addAll(objects);
                    receiver.send(objects1);
                } else if (currentDeep == 1 || currentDeep == 2) {
                    Observable.just(preId)
                            .subscribeOn(Schedulers.io())
                            .map(new Func1<Integer, List<ISelectAble>>() {

                                @Override
                                public List<ISelectAble> call(Integer integer) {
                                    Object o = mDatas.get(integer + "");
                                    List<TaskTypeBean> objects = GsonUtil.parseJsonToList(GsonUtil.parseObjectToJson(o), TaskTypeBean.class);
                                    List<ISelectAble> objects1 = new ArrayList<>();
                                    objects1.addAll(objects);
                                    return objects1;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<List<ISelectAble>>() {
                                @Override
                                public void call(List<ISelectAble> counties) {
                                    receiver.send(counties);
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    //给一个空值 进去 触发提前结束
                                    receiver.send(new ArrayList<ISelectAble>());
                                }
                            });
                }
            }
        });
        selector.setSelectedListener(this);
        mDeptDialog.init(mBaseActivity, selector);
        mDeptDialog.setOnAddressSelectedListener(this);
        mDeptDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ToastUtil.showToast("111");
            }
        });
        mDeptDialog.show();
    }

    @Override
    public void onAddressSelected(ArrayList<ISelectAble> selectAbles) {
        if (mOnBackSelectListener != null) {
            mOnBackSelectListener.onBackSelect(selectAbles);
        }
    }

    public void setOnBackSelectListener(OnBackSelectListener onBackSelectListener) {
        this.mOnBackSelectListener = onBackSelectListener;
    }

    @Override
    public void closeDiaLog() {
        if (mDeptDialog != null) {
            mDeptDialog.dismiss();
        }
    }
}
