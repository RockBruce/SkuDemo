package cn.edsmall.network;

import android.util.Log;

import io.reactivex.subscribers.DisposableSubscriber;


public abstract class NetworkDisposable<T> extends DisposableSubscriber<T> {
    public static final String TAG = NetworkDisposable.class.getSimpleName();

    public NetworkDisposable() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (BuildConfig.LEO_DEBUG) {
            Log.e(TAG, "网络开始：onStart() ");
        }
    }

    @Override
    public void onNext(T t) {
        if (BuildConfig.LEO_DEBUG) {
            Log.e(TAG, "有数据返回：onNext() ");
        }
    }

    @Override
    public void onComplete() {
        if (BuildConfig.LEO_DEBUG) {
            Log.e(TAG, "完成了：onComplete()");
        }
    }

    @Override
    public void onError(Throwable e) {
        if (BuildConfig.LEO_DEBUG) {
            Log.e(TAG, "竟然出错了: " + e.getMessage());
            // 服务器收到了请求 参数错误/处理错误  500 501 502
            //服务没有收到请求 400 401 404 403
        }
    }
}
