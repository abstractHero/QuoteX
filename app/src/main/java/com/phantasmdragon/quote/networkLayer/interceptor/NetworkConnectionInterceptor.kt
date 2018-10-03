/*
 * Copyright 2018 abstractHero
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phantasmdragon.quote.networkLayer.interceptor

import com.phantasmdragon.quote.callbackLayer.NetworkConnectionCallback
import com.phantasmdragon.quote.networkLayer.exception.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(private val networkConnectionCallback: NetworkConnectionCallback)
    : Interceptor {

    @Throws(NoNetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val isConnected = networkConnectionCallback.isConnected()

        return if (isConnected) chain.proceed(chain.request())
               else throw NoNetworkException("There is no internet connection. Connect to the internet and try again.")
    }
}