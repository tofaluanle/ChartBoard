package cn.manjuu.chatboard;

import android.app.Application;

/**
 * @auther 宋疆疆
 * @date 2015/7/1.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.setContext(this);
    }

}
