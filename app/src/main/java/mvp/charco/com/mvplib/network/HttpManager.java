package mvp.charco.com.mvplib.network;

import android.app.Application;
import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import mvp.charco.com.mvplib.network.callback.DialogCallback;
import mvp.charco.com.mvplib.network.callback.JsonCallback;
import okhttp3.OkHttpClient;

/**
 * Created by HIAPAD on 2018/1/24.
 */

public class HttpManager {

    private String BASE_ADDRESS;
    private HttpHeaders headers = new HttpHeaders();
    private HttpParams params = new HttpParams();

    private HttpManager(){}

    public static HttpManager getInstance(){
        return HttpManagerHolder.HTTP_MANAGER;
    }

    public HttpManager addHeader(String key,String value){
        headers.put(key,value);
        return getInstance();
    }

    public HttpManager addParam(String key,String value){
        params.put(key,value);
        return getInstance();
    }

    public HttpManager init(Application context){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("charco");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        //超时时间设置，默认10秒
        builder.readTimeout(10000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(10000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //https相关设置
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(context)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);
        return getInstance();
    }

    public HttpManager setAddress(String address){
        this.BASE_ADDRESS = address;
        return HttpManagerHolder.HTTP_MANAGER;
    }

    /**
     * 请求字符串
     */
    public HttpManager requsetStringGet(String url,StringCallback stringCallback, Map<String, String> params) {
        OkGo.<String>get(checkUrl(url))
                .tag(url)
                .params(params)
                .execute(stringCallback);
        return HttpManagerHolder.HTTP_MANAGER;
    }

    /**
     * 请求json对象
     */
    public <T> HttpManager requsetJsonGet(String url,JsonCallback<T> jsonCallback, Map<String, String> params) {
        OkGo.<T>get(checkUrl(url))
                .tag(url)
                .params(params)
                .execute(jsonCallback);
        return HttpManagerHolder.HTTP_MANAGER;
    }

    /**
     * 请求json对象
     */
    public <T> HttpManager requsetJsonPost(String url, JsonCallback<T> jsonCallback, Map<String, String> params) {
        OkGo.<T>post(checkUrl(url))
                .tag(url)
                .params(params)
                .execute(jsonCallback);
        return HttpManagerHolder.HTTP_MANAGER;
    }

    /**
     * 请求bitmap
     */
    public HttpManager requsetBitmap(String url, BitmapCallback bitmapCallback, Map<String, String> params){
        OkGo.<Bitmap>get(checkUrl(url))
                .tag(url)
                .params(params)
                .execute(bitmapCallback);
        return getInstance();
    }

    /**
     * 上传json
     */
    public <T>HttpManager upJson(String url,JSONObject jsonObject,JsonCallback<T> jsonCallback, Map<String, String> params){
        OkGo.<T>post(checkUrl(url))
                .tag(this)
                .params(params)
                .upJson(jsonObject)
                .execute(jsonCallback);
        return getInstance();
    }

    /**
     * 上传长文本
     */
    public <T>HttpManager upString(String url,String content,JsonCallback<T> jsonCallback, Map<String, String> params){
        OkGo.<T>post(checkUrl(url))
                .tag(this)
                .upString(content)
                .execute(jsonCallback);
        return getInstance();
    }

    /**
     * 上传byte数据
     */
    public <T>HttpManager upBytes(String url,byte[] bytes,JsonCallback<T> jsonCallback, Map<String, String> params){
        OkGo.<T>post(checkUrl(url))
                .tag(this)
                .params(params)
                .upBytes(bytes)
                .execute(jsonCallback);
        return getInstance();
    }

    /**
     * 上传文件
     */
    public <T>HttpManager upFile(String url, File file, DialogCallback<T> dialogCallback, Map<String, String> params){
        OkGo.<T>post(checkUrl(url))
                .tag(this)
                .params(params)
                .upFile(file)
                .execute(dialogCallback);
        return getInstance();
    }

    /**
     * 下载文件
     格式化进度
     NumberFormat numberFormat = NumberFormat.getPercentInstance();
     numberFormat.setMinimumFractionDigits(2);
     numberFormat.format(progress.fraction)
     */
    public HttpManager downFile(String url, FileCallback fileCallback ,  Map<String, String> params){
        OkGo.<File>get(checkUrl(url))//
                .tag(this)//
                .params(params)
                .execute(fileCallback);
        return getInstance();
    }

    public void cancelTag(Objects objects){
        OkGo.getInstance().cancelTag(objects);
    }

    private String checkUrl(String url){
        if (!url.startsWith("http")){
            return BASE_ADDRESS+url;
        }else{
            return url;
        }
    }

    private static class  HttpManagerHolder {

        private static  HttpManager HTTP_MANAGER = new HttpManager();
    }

}
