package net.oschina.app;

import java.io.File;

import net.oschina.app.ui.MainActivity;
import net.oschina.app.ui.TestActivity;
import net.oschina.app.util.TDevice;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.http.KJAsyncTask;
import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.PreferenceHelper;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 应用启动界面
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年12月22日 上午11:51:56
 * 
 */
public class AppStart extends Activity {


    @InjectView(R.id.app_start_view)
    LinearLayout starLayout;

    private KJBitmap kjb;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        // SystemTool.gc(this); //针对性能好的手机使用，加快应用相应速度

        final View view = View.inflate(this, R.layout.app_start, null);
        setContentView(view);
        ButterKnife.inject(this, view);

        String filePath = AppConfig.DEFAULT_SAVE_IMAGE_PATH
                + AppContext.get("welcomepic_name","default");
        File file  = new File(filePath);

        if(file.exists()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
            Drawable drawable= new BitmapDrawable(bmp);
            view.setBackground(drawable);
        }else{
            view.setBackgroundResource(R.drawable.welcome);
        }


        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(800);
        view.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationStart(Animation animation) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int cacheVersion = PreferenceHelper.readInt(this, "first_install",
                "first_install", -1);
        int currentVersion = TDevice.getVersionCode();
        if (cacheVersion < currentVersion) {
            PreferenceHelper.write(this, "first_install", "first_install",
                    currentVersion);
            cleanImageCache();
        }
    }

    private void cleanImageCache() {
        final File folder = FileUtils.getSaveFolder("OSChina/imagecache");
        KJAsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (File file : folder.listFiles()) {
                    file.delete();
                }
            }
        });
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent uploadLog = new Intent(this, LogUploadService.class);
        startService(uploadLog);
        Intent welcome = new Intent(this, WelcomePicService.class);
        startService(welcome);
        Intent intent = new Intent(this, MainActivity.class);
//        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
        finish();
    }
}
