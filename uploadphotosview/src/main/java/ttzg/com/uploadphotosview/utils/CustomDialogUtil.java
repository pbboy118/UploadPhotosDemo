package ttzg.com.uploadphotosview.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ttzg.com.uploadphotosview.R;

/**
 * @author: xin.wu
 * @create time: 2015/12/21 10:54
 * @TODO: 自定义dialog
 */
public class CustomDialogUtil {
    public AlertDialog getmCustomAlertDlg() {
        return mCustomAlertDlg;
    }

    public void setmCustomAlertDlg(AlertDialog mCustomAlertDlg) {
        this.mCustomAlertDlg = mCustomAlertDlg;
    }

    private AlertDialog mCustomAlertDlg;

    public Dialog getmCustomDlg() {
        return mCustomDlg;
    }

    /**
     * @param flag
     */
    public void setCancelable(boolean flag) {
        if (mCustomDlg != null) {
            mCustomDlg.setCancelable(flag);
        }
    }

    public void setmCustomDlg(Dialog mCustomDlg) {
        this.mCustomDlg = mCustomDlg;
    }

    private Dialog mCustomDlg;

    public AlertDialog showDialog(Context activity, View contentView, String contentMsg) {
        return showDialog(activity, contentView, contentMsg, "确定", "取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public AlertDialog showDialog(Context activity, View contentView, String contentMsg, String subtext, String canceltext) {
        return showDialog(activity, contentView, contentMsg, subtext, canceltext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public AlertDialog showDialog(Context activity, View contentView, String contentMsg, View.OnClickListener sublistener, View.OnClickListener cancellistner) {
        return showDialog(activity, contentView, contentMsg, "确定", "取消", sublistener, cancellistner);
    }

    public AlertDialog showDialog(Context activity, View contentView, String contentMsg, View.OnClickListener sublistener) {
        return showDialog(activity, contentView, contentMsg, "确定", "取消", sublistener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public AlertDialog showDialog(Context activity, View contentView, String contentMsg, View.OnClickListener sublistener,
                                  String msgOk, String msgCancel) {
        return showDialog(activity, contentView, contentMsg, msgOk, msgCancel, sublistener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public AlertDialog showDialog(Context activity, View contentView, String titleMsg, String content, String btnSubMsg, String btnCanMsg,
                                  final View.OnClickListener subListener,
                                  final View.OnClickListener cancelListener) {

        mCustomAlertDlg = new AlertDialog.Builder(activity).create();
        View dlg_defaultview = View.inflate(activity, R.layout.dialog_default, null);
        FrameLayout dlg_content = (FrameLayout) dlg_defaultview.findViewById(R.id.dlg_content);
        LinearLayout btn_bar = (LinearLayout) dlg_defaultview.findViewById(R.id.btn_bar);
        if (contentView != null) {
            dlg_content.addView(contentView);
        }

        mCustomAlertDlg.setView(dlg_defaultview);
        TextView sub_title = (TextView) dlg_defaultview.findViewById(R.id.sub_title);
        if (!Utils.isNull(titleMsg)) {
            sub_title.setText(titleMsg);
            sub_title.setVisibility(View.VISIBLE);
        } else {
            sub_title.setVisibility(View.GONE);
        }
        TextView tv_content = (TextView) dlg_defaultview.findViewById(R.id.tv_content);
        tv_content.setText(content);

        if (!Utils.isNull(btnSubMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setText(btnSubMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setOnClickListener(subListener);
        } else {
            btn_bar.setVisibility(View.GONE);
        }

        if (!Utils.isNull(btnCanMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setText(btnCanMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setOnClickListener(cancelListener);
        } else {
            if (!Utils.isNull(btnSubMsg)) {
                (dlg_defaultview.findViewById(R.id.btn_cancel)).setVisibility(View.GONE);
            } else {
                btn_bar.setVisibility(View.GONE);
            }
        }
        return mCustomAlertDlg;
    }


    public AlertDialog showDialog(Context activity, View contentView, String titleMsg, String content, String btnSubMsg, String btnCanMsg,
                                  final View.OnClickListener subListener,
                                  final View.OnClickListener cancelListener, boolean flag) {

        mCustomAlertDlg = new AlertDialog.Builder(activity).create();
        mCustomAlertDlg.setCancelable(flag);
        View dlg_defaultview = View.inflate(activity, R.layout.dialog_default, null);
        FrameLayout dlg_content = (FrameLayout) dlg_defaultview.findViewById(R.id.dlg_content);
        LinearLayout btn_bar = (LinearLayout) dlg_defaultview.findViewById(R.id.btn_bar);
        if (contentView != null) {
            dlg_content.addView(contentView);
        }

        TextView tv_content = (TextView) dlg_defaultview.findViewById(R.id.tv_content);
        tv_content.setText(content);

        mCustomAlertDlg.setView(dlg_defaultview);
        TextView sub_title = (TextView) dlg_defaultview.findViewById(R.id.sub_title);
        if (!Utils.isNull(titleMsg)) {
            sub_title.setText(titleMsg);
            sub_title.setVisibility(View.VISIBLE);
        } else {
            sub_title.setVisibility(View.GONE);
        }

        if (!Utils.isNull(btnSubMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setText(btnSubMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setOnClickListener(subListener);
        } else {
            btn_bar.setVisibility(View.GONE);
        }

        if (!Utils.isNull(btnCanMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setText(btnCanMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setOnClickListener(cancelListener);
        } else {
            if (!Utils.isNull(btnSubMsg)) {
                (dlg_defaultview.findViewById(R.id.btn_cancel)).setVisibility(View.GONE);
            } else {
                btn_bar.setVisibility(View.GONE);
            }
        }
        return mCustomAlertDlg;
    }

    /**
     * @param activity
     * @param btnSubMsg      左按钮文本
     * @param btnCanMsg      右按钮文本
     * @param subListener    左按钮事件
     * @param cancelListener 右按钮事件
     * @return Dialog
     */
    public AlertDialog showDialog(Context activity, View contentView, String content, String btnSubMsg, String btnCanMsg,
                                  final View.OnClickListener subListener,
                                  final View.OnClickListener cancelListener) {

        mCustomAlertDlg = new AlertDialog.Builder(activity).create();
        mCustomAlertDlg.setCancelable(false);
        View dlg_defaultview = View.inflate(activity, R.layout.dialog_default, null);
        FrameLayout dlg_content = (FrameLayout) dlg_defaultview.findViewById(R.id.dlg_content);
        LinearLayout btn_bar = (LinearLayout) dlg_defaultview.findViewById(R.id.btn_bar);
        if (contentView != null) {
            dlg_content.addView(contentView);
        }
        mCustomAlertDlg.setView(dlg_defaultview);

        TextView sub_title = (TextView) dlg_defaultview.findViewById(R.id.sub_title);
        sub_title.setVisibility(View.VISIBLE);
        sub_title.setText("提示");

        TextView tv_content = (TextView) dlg_defaultview.findViewById(R.id.tv_content);
        tv_content.setText(content);

        if (!Utils.isNull(btnSubMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setText(btnSubMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setOnClickListener(subListener);
        } else {
            btn_bar.setVisibility(View.GONE);
        }

        if (!Utils.isNull(btnCanMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setText(btnCanMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setOnClickListener(cancelListener);
        } else {
            if (!Utils.isNull(btnSubMsg)) {
                (dlg_defaultview.findViewById(R.id.btn_cancel)).setVisibility(View.GONE);
            } else {
                btn_bar.setVisibility(View.GONE);
            }
        }
        return mCustomAlertDlg;
    }

    public AlertDialog showImgNoTitleDialog(Context activity, View contentView, String content, String btnSubMsg, String btnCanMsg,
                                            final View.OnClickListener subListener,
                                            final View.OnClickListener cancelListener) {

        mCustomAlertDlg = new AlertDialog.Builder(activity).create();
        mCustomAlertDlg.setCancelable(false);
        View dlg_defaultview = View.inflate(activity, R.layout.dialog_default, null);
        FrameLayout dlg_content = (FrameLayout) dlg_defaultview.findViewById(R.id.dlg_content);
        LinearLayout btn_bar = (LinearLayout) dlg_defaultview.findViewById(R.id.btn_bar);
        if (contentView != null) {
            dlg_content.addView(contentView);
        }
        mCustomAlertDlg.setView(dlg_defaultview);

        TextView sub_title = (TextView) dlg_defaultview.findViewById(R.id.sub_title);
        sub_title.setVisibility(View.GONE);
        //sub_title.setText("提示");

        TextView tv_content = (TextView) dlg_defaultview.findViewById(R.id.tv_content);
        tv_content.setText(content);

        if (!Utils.isNull(btnSubMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setText(btnSubMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_sub)).setOnClickListener(subListener);
        } else {
            btn_bar.setVisibility(View.GONE);
        }

        if (!Utils.isNull(btnCanMsg)) {
            btn_bar.setVisibility(View.VISIBLE);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setText(btnCanMsg);
            ((Button) dlg_defaultview.findViewById(R.id.btn_cancel)).setOnClickListener(cancelListener);
        } else {
            if (!Utils.isNull(btnSubMsg)) {
                (dlg_defaultview.findViewById(R.id.btn_cancel)).setVisibility(View.GONE);
            } else {
                btn_bar.setVisibility(View.GONE);
            }
        }
        return mCustomAlertDlg;
    }

    public AlertDialog showDialog(Context activity, View contentView, String titleMsg, String btnSubMsg, String btnCanMsg,
                                  final View.OnClickListener subListener,
                                  final View.OnClickListener cancelListener, boolean cancelAble) {

        showDialog(activity, contentView, titleMsg, btnSubMsg, btnCanMsg, subListener, cancelListener);
        mCustomAlertDlg.setCancelable(cancelAble);
        return mCustomAlertDlg;
    }


    public void dismiss() {
        if (mCustomAlertDlg != null && mCustomAlertDlg.isShowing()) {
            mCustomAlertDlg.dismiss();
        }
        if (mCustomDlg != null && mCustomDlg.isShowing()) {
            mCustomDlg.dismiss();
        }
    }

    public void show() {
        if (mCustomAlertDlg != null && !mCustomAlertDlg.isShowing()) {
            mCustomAlertDlg.show();
        }
        if (mCustomDlg != null && !mCustomDlg.isShowing()) {
            mCustomDlg.show();
        }
    }

}
