package cn.edsmall.skudemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.edsmall.network.disposable.NetworkDisposable
import cn.edsmall.network.bean.ReqParams
import cn.edsmall.network.bean.RespMsg
import cn.edsmall.network.rx.RetrofitBuilder
import cn.edsmall.network.rx.RetrofitManager
import cn.edsmall.network.utils.Md5Util
import cn.edsmall.network.utils.SubscriberUtils
import cn.edsmall.skudemo.bean.AddAddressBaen
import cn.edsmall.skudemo.bean.ProductDetail
import cn.edsmall.skudemo.weight.SpecificationsDialog
import com.google.gson.Gson
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private lateinit var reqDispose: NetworkDisposable<RespMsg<AddAddressBaen>>
    private lateinit var productDetail: ProductDetail
    private var stringBuilder: StringBuilder? = null
    private lateinit var mGson: Gson
    private val parasMap: MutableMap<String, Any> = mutableMapOf()
    private var mSpeDialog: SpecificationsDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RetrofitBuilder.getInstance().setBaseUrl("https://channelmachine-pre.edsmall.com")

        // TODO refresh param
        val timestamp = System.currentTimeMillis() / 1000
        parasMap["token"] = ""
        parasMap.put("appkey", "f7ab9296f156213c00a55a0e5e74c34a")
        parasMap.put("appsecret", Md5Util.md5("56b108c7073d475099872e3803733272$timestamp"))
        parasMap.put("timestamp", timestamp)
        parasMap.put("platform", "android")
        ReqParams.getInstance().initReqParamMap(parasMap)
        initData()
        loadData2()
        initListener()
    }

    private fun loadData2() {
        val defaultClient = RetrofitManager().getDefaultClient(UserService::class.java)
        reqDispose = object : NetworkDisposable<RespMsg<AddAddressBaen>>() {
            override fun onNext(t: RespMsg<AddAddressBaen>?) {
                super.onNext(t)

            }
        }
        defaultClient.queryArea()
            .compose(RetrofitManager().applySchedulers(reqDispose))
        Log.e("MainActivity",reqDispose.isDisposed.toString())
    }

    private fun loadData() {
        RetrofitManager().getDefaultClient(UserService::class.java)
            .queryArea()
            .compose(RetrofitManager().applySchedulers(object :
                NetworkDisposable<RespMsg<AddAddressBaen>>() {
                override fun onNext(t: RespMsg<AddAddressBaen>?) {
                    super.onNext(t)

                }
            }))


    }


    private fun initData() {
        mGson = Gson()
        try {
            val input = this.assets.open("product")
            val streamReader = InputStreamReader(input)
            val reader = BufferedReader(streamReader)
            var line: String = ""
            stringBuilder = StringBuilder()
            while (reader.readLine().also { line = it } != null) {
                stringBuilder!!.append(line)
            }
            reader.close()
            reader.close()
            input?.close()
        } catch (e: Exception) {

        }
        productDetail = mGson.fromJson(stringBuilder.toString(), ProductDetail::class.java)!!
    }

    private fun initListener() {
        tv_show_spec.setOnClickListener {
//            loadData()
            showSpecDialog(productDetail)
        }
    }

    /**
     * 显示规格弹窗
     */
    private fun showSpecDialog(productDetail: ProductDetail?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        SubscriberUtils.getInstance().cancel()
    }
}