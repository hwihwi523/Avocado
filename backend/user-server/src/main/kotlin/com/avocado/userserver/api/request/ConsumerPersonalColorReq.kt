package com.avocado.userserver.api.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ConsumerPersonalColorReq(
    val personalColorId:Int?,
) {

}
