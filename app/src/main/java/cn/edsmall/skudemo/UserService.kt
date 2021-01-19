package cn.edsmall.skudemo

import cn.edsmall.network.bean.RespMsg
import cn.edsmall.skudemo.bean.AddAddressBaen
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**Created by LZH On 2019/7/11*/
interface UserService {

    @GET("/api/area/addrSource")
    fun queryArea(): Flowable<RespMsg<AddAddressBaen>>


}