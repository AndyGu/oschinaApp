package net.oschina.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.oschina.app.api.remote.VGTimeApi;
import net.oschina.app.bean.WelcomeBean;
import net.oschina.app.cache.DiskLruCacheUtil;

import org.apache.http.Header;
import org.kymjs.kjframe.KJBitmap;

public class WelcomePicService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String  clientType = "1"; // android

        VGTimeApi.checkWelcomePic(clientType, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                KJBitmap kjb = new KJBitmap();
                Log.e("onSuccess",new String(arg2));
                WelcomeBean bean= JSON.parseObject(new String(arg2), WelcomeBean.class);
                if(bean.getStatus() != 0){
//                    DiskLruCacheUtil.saveObject(WelcomePicService.this, arg2, "welcome");
                    final String filePath = AppConfig.DEFAULT_SAVE_IMAGE_PATH
                            + "welcomePic.jpg";
                    kjb.saveImage(WelcomePicService.this, bean.getData().getImg(), filePath);
                }

                WelcomePicService.this.stopSelf();
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                WelcomePicService.this.stopSelf();
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

}