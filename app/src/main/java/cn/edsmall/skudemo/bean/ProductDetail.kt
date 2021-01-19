package cn.edsmall.skudemo.bean

import java.io.Serializable

class ProductDetail {
    //代理状态 0-未代理 1-已代理
    var proxyStatus: Int = 0

    //上下架：0-下架 1-上架
    var isXls: Int = 0
    var supplierId: String = ""
    var deliveryTime: Int = -1
    var labelTitle: String = ""
    var title: String = ""
    var type: Int = 0
    var specJson: List<SpecJsonBean> = mutableListOf()
    var skuJson: List<SkuJsonBean> = mutableListOf()

    //销售权限 0-禁售 1-可售
    var saleAuth: Int = 0
    var subTitle: String = ""
    var model: String = ""
    var stock: Int = 0
    var salePrice: Any? = null
    var brandId: String = ""
    var spuId: String = ""
    var productPrice: Any? = null

    // 收藏Id
    var collectStatus: Int = 0

    class SkuJsonBean : Serializable {
        /**
         * skuId : skuId
         * skuSpecs : [{"id":"规格ID","name":"规格名称","groupId":"规格组ID","groupName":"规格组名称"}]
         * mainImg : sku主图
         * model : sku型号
         * supplyPrice : 批发价
         * purchasePrice : 零售价
         * stock : 库存数量
         * moq : 起订量
         * selected : 1
         */
        var mainImg: String = ""
        var skuSpecs: List<SkuSpecsBean>? = null
        var salePrice: Any? = null
        var isSale: Int = 0
        var saleable: Int = -1
        var model: String = ""
        var title: String = ""
        var skuId: String = ""
        var stock: Int = 0
        var productPrice: Any? = null
        var moq: Int = 0

        class SkuSpecsBean : Serializable {
            /**
             * id : 规格ID
             * name : 规格名称
             * groupId : 规格组ID
             * groupName : 规格组名称
             */
            var id: String = ""
            var name: String = ""
            var groupId: String = ""
            var groupName: String = ""
        }
    }

    class SpecJsonBean : Serializable {
        /**
         * id : 规格组ID
         * name : 规格组名称
         * specParams : [{"id":"规格ID","name":"规格名称"}]
         */

        var id: String = ""
        var name: String = ""
        var specParams: MutableList<SpecParamsBean>? = null

        class SpecParamsBean : Serializable {
            /**
             * id : 规格ID
             * name : 规格名称
             */

            var id: String = ""
            var name: String = ""
            var isSelect: Int = 0   // 0可选未选中  1选中
        }
    }

}