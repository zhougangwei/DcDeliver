package com.aihui.dcdeliver.util;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.aihui.dcdeliver.R;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/26 19:29
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/26$
 * @ 更新描述  ${TODO}
 */

public class AlertUtil {

    public interface onBackResult {
        void backResult();
    }

    private AlertDialog mReceiveDialog;

   public void showDialog(Activity mActivity,  String content, final onBackResult onBackResult) {
        View inflate = View.inflate(mActivity, R.layout.dialog_sure, null);
        View tvQuit = inflate.findViewById(R.id.tv_quit);
        View tvSave = inflate.findViewById(R.id.tv_save);
        TextView tvContent = inflate.findViewById(R.id.tv_content);
        tvContent.setText(content);

        tvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReceiveDialog.dismiss();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReceiveDialog.dismiss();
                onBackResult.backResult();
            }
        });
        mReceiveDialog = new AlertDialog.Builder(mActivity)
                .setView(inflate)
                .create();

        mReceiveDialog.show();
    }
}
