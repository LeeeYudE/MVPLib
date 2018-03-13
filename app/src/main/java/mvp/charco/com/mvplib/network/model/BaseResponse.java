package mvp.charco.com.mvplib.network.model;

import java.io.Serializable;

/**
 * Created by HIAPAD on 2018/1/24.
 */

public class BaseResponse<T> implements Serializable {

    public String flag;
    public String message;
    public String result;
    public T data;

    @Override
    public String toString() {

        return "BaseResponse{" +
                "flag='" + flag + '\'' +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                ", data=" + data +
                '}';
    }
}
