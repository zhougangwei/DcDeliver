package com.aihui.dcdeliver.ui.fragment;


import com.aihui.dcdeliver.base.BaseView;
import com.aihui.dcdeliver.base.RxBusPresenter;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.util.LogUtil;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/3/20 16:47
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/3/20$
 * @ 更新描述  ${TODO}
 */

public class HomeImpl implements RxBusPresenter {
    private CompositeSubscription mCompositeSubscription;
    private BaseView mView;

    public HomeImpl(BaseView view){
        mView = view;
    }
    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        unregisterRxBus();
    }


    @Override
    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {
        Subscription subscription = mRxBus.doSubscribe(eventType, action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                LogUtil.e(throwable.toString());
            }
        });
        //用于注销
        RxBus.getInstance().addSubscription(this, subscription);
    }
    @Override
    public void unregisterRxBus() {
        mRxBus.unSubscribe(this);
    }
}
