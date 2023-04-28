package com.avocado.userserver.api.service

import com.avocado.userserver.api.request.ProviderLoginReq
import com.avocado.userserver.api.response.ProviderLoginResp
import com.avocado.userserver.db.entity.Provider
import org.springframework.stereotype.Service

interface ProviderService {

    suspend fun login(req: ProviderLoginReq): ProviderLoginResp

}