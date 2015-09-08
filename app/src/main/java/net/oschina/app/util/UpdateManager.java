package net.oschina.app.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.oschina.app.AppContext;
import net.oschina.app.api.remote.VGTimeApi;
import net.oschina.app.bean.Update;
import net.oschina.app.bean.UpdateBean;
import net.oschina.app.bean.WelcomeBean;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * 更新管理类
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年11月18日 下午4:21:00
 */

public class UpdateManager {

//    private Update mUpdate;
    private UpdateBean mUpdateBean;

    private Context mContext;

    private boolean isShow = false;

    private ProgressDialog _waitDialog;

    private int curVersionCode;
    private String curVersionName;

    private AsyncHttpResponseHandler mCheckUpdateHandle = new AsyncHttpResponseHandler() {

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            hideCheckDialog();
            if (isShow) {
                showFaileDialog();
            }
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            hideCheckDialog();
//            mUpdateBean = XmlUtils.toBean(Update.class,
//                    new ByteArrayInputStream(arg2));

            mUpdateBean= JSON.parseObject(new String(arg2), UpdateBean.class);

            onFinshCheck();
        }
    };

    public UpdateManager(Context context, boolean isShow) {
        this.mContext = context;
        this.isShow = isShow;

        curVersionCode = TDevice.getVersionCode(AppContext
                .getInstance().getPackageName());

        curVersionName = TDevice.getVersionName();
    }

    public boolean haveNew() {
        if (this.mUpdateBean == null) {
            return false;
        }
        boolean haveNew = false;

//        应该在此比较VersionCode 接口需调整
//        if (curVersionCode < mUpdateBean.getData().getVerName()) {
//            haveNew = true;
//        }
        if(mUpdateBean.getData().getComp() == 1){
            haveNew = true;
        }
        return haveNew;
    }

    public void checkUpdate() {
        if (isShow) {
            showCheckDialog();
        }
        VGTimeApi.checkUpdate(String.valueOf(curVersionCode), mCheckUpdateHandle);
    }

    private void onFinshCheck() {
        if (haveNew()) {
            showUpdateInfo();
        } else {
            if (isShow) {
                showLatestDialog();
            }
        }
    }

    private void showCheckDialog() {
        if (_waitDialog == null) {
            _waitDialog = DialogHelp.getWaitDialog((Activity) mContext, "正在获取新版本信息...");
        }
        _waitDialog.show();
    }

    private void hideCheckDialog() {
        if (_waitDialog != null) {
            _waitDialog.dismiss();
        }
    }

    private void showUpdateInfo() {
        if (mUpdateBean == null) {
            return;
        }
//        接口中应加入升级日志
//        AlertDialog.Builder dialog = DialogHelp.getConfirmDialog(mContext, mUpdate.getUpdate().getAndroid().getUpdateLog(), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                UIHelper.openDownLoadService(mContext, mUpdate.getUpdate().getAndroid().getDownloadUrl(), mUpdate.getUpdate().getAndroid().getVersionName());
//            }
//        });

        AlertDialog.Builder dialog = DialogHelp.getConfirmDialog(mContext, mUpdateBean.getData().getContent(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                接口中的下载链接不可用
//                UIHelper.openDownLoadService(mContext, mUpdateBean.getData().getUrl(), mUpdateBean.getData().getVerName());
            }
        });
        dialog.setTitle("发现新版本");
        dialog.show();
    }

    private void showLatestDialog() {
        DialogHelp.getMessageDialog(mContext, "已经是新版本了").show();
    }

    private void showFaileDialog() {
        DialogHelp.getMessageDialog(mContext, "网络异常，无法获取新版本信息").show();
    }
}
