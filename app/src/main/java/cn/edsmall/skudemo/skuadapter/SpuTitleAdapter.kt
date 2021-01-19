package cn.edsmall.skudemo.skuadapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.edsmall.skudemo.R
import cn.edsmall.skudemo.bean.ProductDetail
import cn.edsmall.skudemo.bean.SkuIndex
import cn.edsmall.skudemo.bean.SkuSingleton
import cn.edsmall.skudemo.weight.SpecificationsDialog
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.item_spu_items.view.*

/**
 * 显示规格组名称
 *
 */
class SpuTitleAdapter(
    mContext: Context,
    private val specList: List<ProductDetail.SpecJsonBean>,
    private val skuList: List<ProductDetail.SkuJsonBean>
) :
    BaseRVAdapter(mContext) {
    var listener: SpecificationsDialog.OnSelectSpuClickListener? = null
    var adapterList = mutableListOf<BaseRVAdapter>()

    init {
        SkuSingleton.instance.allSku.clear()
        getAllSku()
//        setSpuSelect()
    }

    /**
     * 获取全部sku
     *  key 为当前sku的skuSpecs下全部规格的id的hashCode累加
     *  {
     *  "salePrice": 50.00,
     *  "isSale": 0,
     *  "saleable": 1,
     *  "model": "5506--8",
     *  "title": "纳米多北欧简约吊灯5506--8",
     *  "stock": 2,
     *  "skuId": "1335893097908797440",
     *  "productPrice": 1699.0,
     *  "moq": 1
     *  "skuSpecs": [
     *  {
     *  "id": "2D1B93024B4C81F7438D4A16DAACD762",   //规格ID
     *  "name": "哑白+砂黑",                        //规格名称
     *  "groupId": "1335892298138910720",          //规格组ID
     *  "groupName": "颜色"                        //规格组名称
     *  },
     *  {
     *  "id": "7448BCBA978BF1C873A2D84F93323999",
     *  "name": "LED",
     *  "groupId": "1335892298256351232",
     *  "groupName": "光源数量"
     *  },
     *  {
     *  "id": "9348C0D83E22AD134B6EE4FDB3AD7687",
     *  "name": "D:1000,H:500",
     *  "groupId": "1335892298247962624",
     *  "groupName": "规格"
     *  }
     *  ]
     *  }
     */
    private fun getAllSku() {
        skuList.forEachIndexed { index, sku ->
            var key = 0
            sku.skuSpecs?.forEach { subSku ->
                key += subSku.id.hashCode()
            }
            val skuIndex = SkuIndex(index, sku.stock >= sku.moq && sku.saleable == 1)
            SkuSingleton.instance.allSku[key.toString()] = skuIndex
        }
    }

    /**设置当前产品选中的spu*/
    private fun setSpuSelect(gId: String) {
        // 设置选中
        SkuSingleton.instance.skuSelectMap.forEach { map ->
            specList.forEach { spu ->
                if (gId == spu.id) {
                    spu.specParams?.forEach { subSpu ->
                        if (subSpu.id == map.value) {
//                            subSpu.isSelect = 1
                            if (subSpu.isSelect == 1) {
                                subSpu.isSelect = 0
                            } else {
                                subSpu.isSelect = 1
                            }
                        } else {
                            subSpu.isSelect = 0
                        }
                    }
                }
//                else {
//                    spu.specParams?.forEach { subSpu ->
//                        if (subSpu.id == map.value) {
//                            subSpu.isSelect = 0
//                        }
//                    }
//                }
            }
        }
    }

    override fun itemCount(): Int = specList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseViewHolder) {
            val specBean = specList[position]
            holder.itemView.tv_title.text = specBean.name
            //specBean.id 规格组id
            val adapter = SpuAdapter(mContext, specBean.specParams!!, specBean.id)
            val manager = FlexboxLayoutManager(mContext)

            holder.itemView.rv_spu_item.layoutManager = manager
            holder.itemView.rv_spu_item.adapter = adapter
            adapterList.add(adapter)

            adapter.setOnItemClickListener {
                val p = holder.itemView.rv_spu_item.getChildAdapterPosition(it)
                val isSelect = adapter.specParams[p].isSelect
                adapter.specParams.forEach { item ->
                    item.isSelect = 0
                }
                if (isSelect == 1) {
                    adapter.specParams[p].isSelect = 0
                    SkuSingleton.instance.skuSelectMap.remove(adapter.groupId)
                } else {
                    adapter.specParams[p].isSelect = 1
                    SkuSingleton.instance.skuSelectMap[adapter.groupId] = adapter.specParams[p].id
                }
                adapterList.forEach { ada ->
                    ada.notifyDataSetChanged()
                }

                listener?.onSelect()
            }
        }
    }

    override fun createVH(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_spu_items,
                parent,
                false
            )
        )
    }
}