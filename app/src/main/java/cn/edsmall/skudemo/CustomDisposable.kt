package cn.edsmall.skudemo

import android.content.Context
import android.util.Log
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by LZH On 2019/3/19
 */
abstract class CustomDisposable<T> : DisposableSubscriber<T> {
    constructor(mContext: Context) {

    }

    public override fun onStart() {
        super.onStart()
        Log.e(TAG, "网络开始：  onStart: ")
    }

    override fun onNext(t: T) {
        Log.e(TAG, "有数据返回：onNext ")
    }

    override fun onComplete() {
        this.dispose()
        Log.e(TAG, "完成了：onComplete ")
    }

    override fun onError(e: Throwable) {
        Log.e(TAG, "竟然出错了: "+e.message.toString())

    }

    companion object {
        private val TAG = "Request Error"
    }
}
