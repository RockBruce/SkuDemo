package cn.edsmall.skudemo.bean

/**Created by LZH On 2020/2/27*/
class SkuIndex {
    var index = -1
    var isEnable = false

    /**
     * @param index      当前sku位置
     * @param isEnable  使用可用的约束条件
     */
    constructor(index: Int, isEnable: Boolean) {
        this.index = index
        this.isEnable = isEnable
    }
}