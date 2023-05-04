package com.avocado.userserver.api.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ConsumerUpdateReq(
    val gender:String,
    val ageGroup:Int,
    val height:Int?,
    val weight:Int?,
    val mbtiId:Int?,
    val personalColorId:Int?,
) {
}