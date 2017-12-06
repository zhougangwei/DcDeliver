package deliver.aihui.com.dcdeliver.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import deliver.aihui.com.dcdeliver.R;
import deliver.aihui.com.dcdeliver.base.AppActivity;
import deliver.aihui.com.dcdeliver.base.BaseFragment;
import deliver.aihui.com.dcdeliver.base.Content;
import deliver.aihui.com.dcdeliver.util.SPUtil;
import deliver.aihui.com.dcdeliver.util.ToastUtil;

public class LoginActivity extends AppActivity {


    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_ysd)
    TextView  mTvYsd;
    @BindView(R.id.tv_user)
    TextView  mTvUser;
    @BindView(R.id.et_user)
    EditText  mEtUser;
    @BindView(R.id.tv_psw)
    TextView  mTvPsw;
    @BindView(R.id.et_psw)
    EditText  mEtPsw;
    @BindView(R.id.bt_login)
    Button    mBtLogin;
    private UserLoginTask mAuthTask = null;
    private boolean       isLogin   = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String userId = SPUtil.getUserAccount(this);
        String passWord = SPUtil.getPassWord(this);
        String hospitalName = SPUtil.getHospitalName(this);
        boolean isRememberPw = SPUtil.getBoolean(this, Content.ISREMEMBERPW, false);

        if (isRememberPw) {
            if (!TextUtils.isEmpty(passWord)) {
                mEtPsw.setText(passWord);
            }
        }
        if (!TextUtils.isEmpty(userId)) {
            mEtUser.setText(userId);
        }
        if (!TextUtils.isEmpty(passWord)) {
            mEtPsw.setText(passWord);
        }



    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected void initView() {

        isLogin = SPUtil.getBoolean(this, "isLogin", false);

        //如果是已经登录的 就直接进去了
        if (isLogin) {
            startActivity(new Intent(LoginActivity.this
                    , MainActivity.class
            ));
            finish();
        }

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

            mAuthTask = new UserLoginTask(userId, password);
            mAuthTask.execute((Void) null);
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
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String userId;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            userId = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }

            return false;


        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                ToastUtil.showToast("登录成功");
                finish();
                startActivity(new Intent(LoginActivity.this
                        , MainActivity.class
                ));
            } else {
                // mEtPsw.setError(getString(R.string.error_incorrect_password));
                ToastUtil.showToast("密码错误");
                mEtPsw.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }

}
