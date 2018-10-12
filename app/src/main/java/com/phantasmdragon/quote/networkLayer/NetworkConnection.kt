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
package com.phantasmdragon.quote.networkLayer

import android.net.ConnectivityManager
import com.phantasmdragon.quote.callbackLayer.NetworkConnectionCallback
import javax.inject.Inject

class NetworkConnection @Inject constructor(private val connectivityManager: ConnectivityManager) :
    NetworkConnectionCallback {

    override fun isConnected(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

}