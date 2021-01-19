package cn.edsmall.skudemo.bean

class SkuSingleton {
    companion object {
        val instance = SkuSingleton()
    }

    /**
     * sku 全部组合
     * key=(某个sku下skuSpecs中的全部id的hashCode累加)
     * value=记录当前的sku是否可用   val skuIndex = SkuIndex(index, sku.stock >= sku.moq&&sku.saleable==1)
     */
    val allSku = mutableMapOf<String, SkuIndex>()

    /**
     * 装载当前选中的规格
     * map -> key=specJson[position].id,    value=specJson[position].specParams[position].id
     * LinkedMap0f 放进去的对象顺序不会发生变化.
     */
    val skuSelectMap = linkedMapOf<String, Any>()
}