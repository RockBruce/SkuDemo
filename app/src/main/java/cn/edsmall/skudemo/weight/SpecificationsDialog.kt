package cn.edsmall.skudemo.weight

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edsmall.skudemo.R
import cn.edsmall.skudemo.bean.ProductDetail
import cn.edsmall.skudemo.bean.SkuSingleton
import cn.edsmall.skudemo.skuadapter.SpuTitleAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_add_cart.*
import kotlinx.android.synthetic.main.dialog_add_cart.tv_add_cart
import kotlinx.android.synthetic.main.dialog_add_cart.tv_product_name

class SpecificationsDialog(
    private var mContext: Context,
    private val mProductDetail: ProductDetail,
    private val supportFragmentManager: FragmentManager
) : BaseDialog(mContext, R.layout.dialog_add_cart),
    View.OnClickListener {
    private var goodsBean: ProductDetail.SkuJsonBean? = null
    private var itemManager: LinearLayoutManager = LinearLayoutManager(mContext)
    private var mTitleAdapter: SpuTitleAdapter? = null
    private lateinit var recyclerView: RecyclerView
    var onSelectPec: OnSelectPecListener? = null
    var onAddCart: OnAddCartListener? = null
    var moqNum: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.rv_params)!!
        mTitleAdapter = SpuTitleAdapter(mContext, mProductDetail.specJson, mProductDetail.skuJson)
        recyclerView.layoutManager = itemManager
        recyclerView.adapter = mTitleAdapter

        mTitleAdapter?.listener = object : OnSelectSpuClickListener {
            override fun onSelect() {
                //点击选择suk商品
                if (SkuSingleton.instance.skuSelectMap.size != mTitleAdapter?.adapterList?.size) {
                    setDefault()
                }
//                goodsBean = findSku(SkuSingleton.instance.getSelectedVids())
                if (goodsBean != null) {
                    onSelectPec!!.onSelectPec(goodsBean!!)
                    showSelectProduct(goodsBean)
                }
            }
        }
        setDefault()
    }



    /**
     * list: 是勾选中的spu的vid，遍历skuSpecJson里面的skuSpecJson的vid
     */
    private fun findSku(list: MutableSet<String>): ProductDetail.SkuJsonBean? {
        val set = mutableSetOf<String>()
        set.addAll(list)
        mProductDetail.skuJson.forEach {
            it.skuSpecs!!.forEach { item ->
                set.add(item.id)
            }
            if (set.size == list.size) {
                return it
            }
            set.clear()
            set.addAll(list)
        }
        return null
    }

    /**
     * 显示没选中商品时的信息
     */
    private fun setDefault() {
        moqNum = null
        goodsBean=null
        tv_product_name.text = mProductDetail.subTitle
        tv_sku_stock.text = "${mProductDetail.stock}"
        tv_product_money.text = "¥${mProductDetail?.salePrice}"
        val productPriceStr = "¥" + mProductDetail?.productPrice.toString()
        tv_sku_product_price.text =productPriceStr
        tv_product_model.text = "型号编码: "
        var sock = 0

    }

    /**
     * 选完规格后的显示商品sku信息
     */
    private fun showSelectProduct(goodsBean: ProductDetail.SkuJsonBean?) {
        moqNum = goodsBean?.moq.toString().toInt()
        tv_product_money.text = "¥${goodsBean?.salePrice}"
        val productPriceStr = "¥" + goodsBean?.productPrice.toString()
        tv_sku_product_price.text =productPriceStr
        tv_product_model.text = "型号编码: ${goodsBean?.model}"
        Glide.with(iv_product_pic).load(goodsBean?.mainImg).into(iv_product_pic)
        tv_sku_stock.text = "${goodsBean?.stock}"
        tv_cart_sum_num.text = moqNum.toString()
    }

    private fun initListener() {
        tv_add_cart.setOnClickListener(this)
        iv_close.setOnClickListener(this)
        iv_reduce.setOnClickListener(this)
        iv_add.setOnClickListener(this)
        tv_cart_sum_num.setOnClickListener(this)
    }

    @SuppressLint("ShowToast")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_add_cart -> {
                if (SkuSingleton.instance.skuSelectMap.size != mTitleAdapter?.adapterList?.size) {
                    val toast = Toast.makeText(mContext, "请选择规格", Toast.LENGTH_SHORT)
                    toast.show()
                    return
                }
                //加入采购单

                    if (goodsBean?.stock!! <= 0) {
                        val toast = Toast.makeText(mContext, "库存不足", Toast.LENGTH_SHORT)
                        toast.show()
                    } else {
                        onAddCart!!.onAddCart(tv_cart_sum_num.text.toString().toInt())
                    }

            }
            R.id.iv_close -> {
                dismiss()
            }
            R.id.iv_reduce -> {

                val num = tv_cart_sum_num.text.toString().toInt()
                if (num < 2) {
                    return
                } else {
                    if (moqNum != null && num <= moqNum!!) {
                        val toast = Toast.makeText(mContext, "购买的数量不能少于起订量", Toast.LENGTH_SHORT)
                        toast.show()
                    } else {
                        tv_cart_sum_num.text = (num - 1).toString()
                    }

                }
            }
            R.id.iv_add -> {
                val buyNumber = tv_cart_sum_num.text.toString().toInt()
                if (goodsBean != null) {
                    if (buyNumber >= goodsBean?.stock!!) {
                        val toast = Toast.makeText(mContext, "库存不足", Toast.LENGTH_SHORT)
                        toast.show()
                        return
                    }

                }
                val num = tv_cart_sum_num.text.toString().toInt()
                tv_cart_sum_num.setText((num + 1).toString())


            }
            R.id.tv_cart_sum_num -> {

            }
        }
    }

    /*private fun productDetailNumDialog() {

        numDialog = CommonDialog.init().setLayoutId(R.layout.product_detail_num_dialog)
        numDialog.setConvertListener(object : DialogConvertListener(), View.OnClickListener {
            override fun onClick(v: View?) {
                when (v!!.id) {
                    R.id.detail_num_dialog_determine_tv -> {
                        if (goodsBean != null) {
                            if (edtCount!!.text.toString().toInt() >= goodsBean?.stock!!) {
                                tv_cart_sum_num.text = goodsBean?.stock!!.toString()
                                val toast = Toast.makeText(mContext, "该商品起最大库存为${goodsBean?.stock!!}", Toast.LENGTH_SHORT)
                                toast.show()
                            }else{
                                if (edtCount!!.text.toString().toInt() <=goodsBean!!.moq){
                                    tv_cart_sum_num.text =goodsBean!!.moq.toString()
                                    val toast = Toast.makeText(mContext, "该商品起订量为${moqNum}", Toast.LENGTH_SHORT)
                                    toast.show()
                                }else{
                                    tv_cart_sum_num.text = (edtCount!!.text).toString()
                                }
                            }
                        } else {
                            tv_cart_sum_num.text = (edtCount!!.text).toString()
                        }

                        numDialog.dismissDialog()
                    }
                    R.id.detail_num_dialog_cancel_tv -> {
                        numDialog.dismissDialog()
                    }
                    R.id.detail_num_dialog_close_iv -> {
                        numDialog.dismissDialog()
                    }
                }
            }

            override fun convertView(holer: DialogViewHolder, dialog: MyBaseDialog) {
                edtCount = holer.convertView.findViewById(R.id.detail_num_dialog_et)
                edtCount!!.setText(tv_cart_sum_num.text.toString())
                holer.convertView.findViewById<TextView>(R.id.detail_num_dialog_determine_tv).text =
                    "确定"
                holer.convertView.findViewById<TextView>(R.id.detail_num_dialog_cancel_tv).text =
                    "取消"
                holer.convertView.findViewById<ImageView>(R.id.detail_num_dialog_close_iv)
                holer.setOnClickListener(R.id.detail_num_dialog_determine_tv, this)
                holer.setOnClickListener(R.id.detail_num_dialog_cancel_tv, this)
                holer.setOnClickListener(R.id.detail_num_dialog_close_iv, this)
            }

        }).showDialog(supportFragmentManager, "dialog").setShowBottom(false)
            .setWidth(((cn.channelmachine.cm.utils.SysUtils.getScreenWidth(mContext) * 0.7).toInt()))
            .setHeight(((cn.channelmachine.cm.utils.SysUtils.getScreenHeight(mContext) * 0.7).toInt()))

    }
*/
    interface OnSelectSpuClickListener {
        fun onSelect()
    }

    interface OnSelectPecListener {
        fun onSelectPec(goods: ProductDetail.SkuJsonBean)
        fun onCartNumber(catNum: Int)
    }

    interface OnAddCartListener {
        fun onAddCart(catNum: Int)
    }
}