package cn.edsmall.network.bean;

import android.util.Log;
import android.widget.Toast;

import java.util.Map;

import cn.edsmall.network.BuildConfig;
import cn.edsmall.network.R;
import cn.edsmall.network.interceptor.ParamsInterceptor;

/**
 * 请求头的数据(提供给{@link ParamsInterceptor }获取数据,同时也提供给带调用这{@link #initReqParamMap})
 */
public class ReqParams {
    private static ReqParams ourInstance = null;
    private Map<String, Object> paramsMap;

    public static ReqParams getInstance() {
        if (ourInstance == null) {
            synchronized (ReqParams.class) {
                if (ourInstance == null) {
                    ourInstance = new ReqParams();
                }
            }
        }
        return ourInstance;
    }

    private ReqParams() {
    }


    /**
     * 提供给{@link ParamsInterceptor}
     * @return
     */
    public Map<String, Object> getParamsMap() {
        return paramsMap;
    }

    /**
     * 该方法提供给调用层初始化的时候调用（可在Application中调用）
     *
     * @param paramsMap 请求头的参数
     */
    public void initReqParamMap(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }
}
