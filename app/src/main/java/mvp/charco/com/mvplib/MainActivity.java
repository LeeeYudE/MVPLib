package mvp.charco.com.mvplib;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;

import mvp.charco.com.mvplib.network.HttpManager;
import mvp.charco.com.mvplib.network.callback.JsonCallback;
import mvp.charco.com.mvplib.network.model.Model;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    private void test() {
        HttpManager.getInstance().downFile("http://sw.bos.baidu.com/sw-search-sp/software/5410dc012ad00/WeGameMiniLoader_3.7.3.5189.exe",
                null, new FileCallback(Environment.getExternalStorageDirectory()+"/demo/","demo.exe") {
                    @Override
                    public void onSuccess(Response<File> response) {
                        Log.d("charco","文件下载成功"+response.body().getAbsolutePath());
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        Log.d("charco","进度"+progress.fraction);
                    }
                });
/*        HttpManager.getInstance().requsetJsonGet("http://adc.io/api/data/Android/10/1",
                null, new JsonCallback<Model>() {
                    @Override
                    public void onSuccess(Response<Model> response) {
                        Log.d("charco","请求数据成功"+response.isSuccessful());
                    }

                    @Override
                    public void onError(Response<Model> response) {
                        Log.d("charco","请求数据失败"+response.getRawResponse().code());
                        Log.d("charco","请求数据失败"+response.getRawResponse().message());
                    }
                });*/
/*        File file = new File(Environment.getExternalStorageDirectory()+"/demo.png");
        HttpManager.getInstance().upFile("https://sm.ms/api/upload", "smfile", file, null,
                new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("charco","上传图片成功"+response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.d("charco","上传图片失败");
                    }
                });*/
    }
}
