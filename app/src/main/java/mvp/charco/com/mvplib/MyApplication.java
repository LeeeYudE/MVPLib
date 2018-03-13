package mvp.charco.com.mvplib;

import android.app.Application;

import mvp.charco.com.mvplib.network.HttpManager;

/**
 * Created by HIAPAD on 2018/1/24.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HttpManager.getInstance().init(this)
                .setAddress("https://api.douban.com/");

    }
}
