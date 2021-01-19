package cn.edsmall.skudemo.bean

class AddAddressBaen {

    val all : All? = null
    val areas :MutableList<Areas> = mutableListOf()

    class All{
        val code :String =""
        val name :String =""
    }
    class Areas{
        val code :String =""
        var name :String =""
        val subAreas:MutableList<Areas> = mutableListOf()
    }
}