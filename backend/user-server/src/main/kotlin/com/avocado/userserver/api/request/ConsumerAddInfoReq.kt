package com.avocado.userserver.api.request

data class ConsumerAddInfoReq(
    val gender:String,
    val age:Int,
    val height:Int,
    val weight:Int,
    val mbtiId:Int,
    val personalColorId:Int,
) {

}
