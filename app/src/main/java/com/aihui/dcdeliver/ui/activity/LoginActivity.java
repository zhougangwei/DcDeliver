package com.aihui.dcdeliver.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.base.AppActivity;
import com.aihui.dcdeliver.base.Content;
import com.aihui.dcdeliver.bean.LoginBean;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.MyService;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.util.Inpututils;
import com.aihui.dcdeliver.util.PermissionUtils;
import com.aihui.dcdeliver.util.SPUtil;
import com.blankj.utilcode.utils.TimeUtils;
import com.tencent.android.tpush.XGPushConfig;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppActivity {


    @BindView(R.id.iv_logo)
    ImageView      mIvLogo;
    @BindView(R.id.tv_ysd)
    TextView       mTvYsd;
    @BindView(R.id.tv_user)
    TextView       mTvUser;
    @BindView(R.id.et_user)
    EditText       mEtUser;
    @BindView(R.id.tv_psw)
    TextView       mTvPsw;
    @BindView(R.id.et_psw)
    EditText       mEtPsw;
    @BindView(R.id.bt_login)
    Button         mBtLogin;
    @BindView(R.id.rel_content)
    RelativeLayout mRelContent;
    @BindView(R.id.rl_login_root)
    RelativeLayout mRlLoginRoot;

    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    private boolean isLogin = false;

    @Override
    protected void onStart() {
        super.onStart();
        String userId = SPUtil.getUserName(this);
        String passWord = SPUtil.getPassWord(this);
        if (!TextUtils.isEmpty(userId)) {
            mEtUser.setText(userId);
        }
        if (!TextUtils.isEmpty(passWord)) {
            mEtPsw.setText(passWord);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {

        keepLoginBtnNotOver(mRlLoginRoot, mRelContent);

        //触摸外部，键盘消失
        mRlLoginRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Inpututils.closeKeyboard(LoginActivity.this);
                return false;
            }
        });

     /*   XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.d("TPush", "注册成功，设备token为：" + data);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });*/

        requstPermisson();

        //先判断是不是过期进来的 过期进来的 不需要以下两部
        if(1==getIntent().getIntExtra("where",0)){
            return;
        }
        if (TimeUtils.getCurTimeString(mFormat).equals(SPUtil.getString(LoginActivity.this, Content.LOGIN_TIME, ""))) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            gotoLogin();
        }

    }

    private void requstPermisson() {
        PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }



    private void gotoLogin() {
        if (isLogin){
             return;
         }
        RetrofitClient.getRetrofit()
                .create(MyService.class)
                .login()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        isLogin = true;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LoginBean>(LoginActivity.this) {
                    @Override
                    public void onError(Throwable e) {
                        isLogin = false;
                        SPUtil.clear(LoginActivity.this);
                      super.onError(e);
                    }
                    @Override
                    public void onNext(LoginBean bean) {
                        isLogin = false;
                        LoginBean.BodyBean body = bean.getBody();
                        SPUtil.saveString(LoginActivity.this, "userName", body.getUser().getUserName());
                        SPUtil.saveString(LoginActivity.this, "deptName", body.getUser().getDeptName());
                        LoginBean.BodyBean.PermissionBean permission = body.getPermission();
                        boolean hasReceive = permission.isHasReceive();
                        boolean hasSave = permission.isHasSave();
                        String hasNextRecord = permission.isHasNextRecord();
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);


                        SPUtil.saveString(LoginActivity.this,Content.USER_ACCOUNT,body.getUser().getAccount());
                        SPUtil.saveBoolean(LoginActivity.this, Content.HAS_RECEIVE, hasReceive);
                        SPUtil.saveBoolean(LoginActivity.this, Content.HAS_SAVE, hasSave);
                        SPUtil.saveBoolean(LoginActivity.this, Content.HAS_SIGN, hasSave);
                        SPUtil.saveString(LoginActivity.this, Content.LOGIN_TIME, TimeUtils.getCurTimeString(mFormat));
                        SPUtil.saveString(LoginActivity.this, Content.HAS_NEXT_RECORD, hasNextRecord);
                        //关闭加载动画
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }


    //对比本地数据库 然后查询
    private void attemptLogin() {

        mEtUser.setError(null);
        mEtPsw.setError(null);
        // Store values at the time of the login attempt.
        String userId = mEtUser.getText().toString().trim();
        String password = mEtPsw.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid userId address.
        if (TextUtils.isEmpty(userId)) {
            SPUtil.saveString(this, "userName", userId);
            //mEtUser.setError(getString(R.string.error_field_required));
            focusView = mEtUser;
            cancel = true;
        } else if (!isUserValid(userId)) {
            //mEtUser.setError(getString(R.string.error_user));
            focusView = mEtUser;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            //mEtPsw.setError(getString(R.string.error_invalid_password));
            focusView = mEtPsw;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            //mEtPsw.setError(getString(R.string.action_sign_in_short));
            focusView = mEtPsw;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
          /*  */
            SPUtil.saveString(this, Content.PASSWORD, password);

            if (isLogin){
                return;
            }
            RetrofitClient.getRetrofit()
                    .create(MyService.class)
                    .login(userId, password, XGPushConfig.getToken(this))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            isLogin = true;
                            //开始加载动画
                            mAvi.setVisibility(View.VISIBLE);
                            mAvi.show();
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<LoginBean>(LoginActivity.this) {
                        @Override
                        public void onError(Throwable e) {
                            isLogin = false;
                            mAvi.hide();
                            SPUtil.clear(LoginActivity.this);
                            super.onError(e);
                        }
                        @Override
                        public void onNext(LoginBean bean) {
                            isLogin = false;
                            LoginBean.BodyBean body = bean.getBody();
                            SPUtil.saveString(LoginActivity.this, "userName", body.getUser().getUserName());
                            SPUtil.saveString(LoginActivity.this, "deptName", body.getUser().getDeptName());

                            LoginBean.BodyBean.PermissionBean permission = body.getPermission();

                            boolean hasReceive = permission.isHasReceive();
                            boolean hasSave = permission.isHasSave();
                            String hasNextRecord = permission.isHasNextRecord();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            SPUtil.saveString(LoginActivity.this,Content.USER_ACCOUNT,body.getUser().getAccount());
                            SPUtil.saveBoolean(LoginActivity.this, Content.HAS_RECEIVE, hasReceive);
                            SPUtil.saveBoolean(LoginActivity.this, Content.HAS_SAVE, hasSave);
                            SPUtil.saveBoolean(LoginActivity.this, Content.HAS_SIGN, hasSave);
                            SPUtil.saveString(LoginActivity.this, Content.LOGIN_TIME, TimeUtils.getCurTimeString(mFormat));
                            SPUtil.saveString(LoginActivity.this, Content.HAS_NEXT_RECORD, hasNextRecord);
                            //关闭加载动画
                            mAvi.hide();
                            startActivity(intent);
                            finish();


                    }
        });
    }

}

    //可以写入密码的规范  比如说 几位数 只能是数字等的正则
    private boolean isPasswordValid(String password) {

        return true;
    }

    //同上
    private boolean isUserValid(String userId) {
        return true;
    }


    /**
     * 保持登录按钮始终不会被覆盖
     *
     * @param root
     * @param subView
     */
    private void keepLoginBtnNotOver(final View root, final View subView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                // 若不可视区域高度大于200，则键盘显示,其实相当于键盘的高度
                if (rootInvisibleHeight > 200) {
                    // 显示键盘时
                    int srollHeight = rootInvisibleHeight - (root.getHeight() - subView.getHeight()) - Inpututils.getNavigationBarHeight(root.getContext());
                    if (srollHeight > 0) {
                        root.scrollTo(0, srollHeight);
                    }
                } else {
                    // 隐藏键盘时
                    root.scrollTo(0, 0);
                }
            }
        });
    }


    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        attemptLogin();
    }


}
