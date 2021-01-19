package cn.edsmall.skudemo.skuadapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cn.edsmall.skudemo.R
import cn.edsmall.skudemo.bean.ProductDetail
import cn.edsmall.skudemo.bean.SkuSingleton
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_spu_value.view.*

class SpuAdapter(
    mContext: Context,
    val specParams: List<ProductDetail.SpecJsonBean.SpecParamsBean>,
    val groupId: String
) :
    BaseRVAdapter(mContext) {

    override fun itemCount(): Int {
        return specParams.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is BaseViewHolder) {
            val item = specParams[p1]
            when {
                item.isSelect == 1 -> {// 当前选中
                    p0.itemView.tv_spu.isEnabled = true
                    p0.itemView.tv_spu.setBackgroundResource(R.mipmap.ic_sku_select)
                   p0.itemView.tv_spu.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.text_black_color
                        )
                    )
                }
                item.isSelect == 0 -> {  // 可选未选中
//                    if (SkuSingleton.instance.getState(item.id, groupId)) {
//                        p0.itemView.tv_spu.isEnabled = true
//                        p0.itemView.tv_spu.setTextColor(
//                            ContextCompat.getColor(
//                                mContext,
//                                R.color.text_black_color
//                            )
//                        )
//                        p0.itemView.tv_spu.setBackgroundResource(R.drawable.btn_sku_default)
//                    } else {// 不可选
//                        p0.itemView.tv_spu.setTextColor(
//                            ContextCompat.getColor(
//                                mContext,
//                                R.color.colorPopup
//                            )
//                        )
//                        p0.itemView.tv_spu.setBackgroundResource(R.drawable.btn_sku_default)
//                        p0.itemView.tv_spu.isEnabled = false
//                    }
                }
            }
            p0.itemView.tv_spu.text = item.name
        }
    }

    override fun createVH(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_spu_value,
                parent,
                false
            )
        )
    }
}