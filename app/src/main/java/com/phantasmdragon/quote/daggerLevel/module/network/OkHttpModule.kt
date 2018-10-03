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
package com.phantasmdragon.quote.daggerLevel.module.network

import android.content.Context
import android.net.ConnectivityManager
import com.phantasmdragon.quote.callbackLayer.NetworkConnectionCallback
import com.phantasmdragon.quote.daggerLevel.annotation.qualifier.ApplicationContext
import com.phantasmdragon.quote.daggerLevel.annotation.qualifier.CacheSize
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ApplicationScope
import com.phantasmdragon.quote.networkLayer.NetworkConnection
import com.phantasmdragon.quote.networkLayer.interceptor.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient

@Module
class OkHttpModule {

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(cache: Cache,
                            networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient
            = OkHttpClient.Builder()
                          .cache(cache)
                          .addInterceptor(networkConnectionInterceptor)
                          .build()

    @Provides
    @ApplicationScope
    fun provideHttpCache(@ApplicationContext context: Context,
                         @CacheSize cacheSize: Long): Cache
            = Cache(context.cacheDir, cacheSize)

    @Provides
    @CacheSize
    @ApplicationScope
    fun provideCacheSize(): Long = 10 * 1024 * 1024 // ← 10 MB

    @Provides
    @ApplicationScope
    fun provideConnectingInterceptor(networkConnectionCallback: NetworkConnectionCallback)
            = NetworkConnectionInterceptor(networkConnectionCallback)

    @Provides
    @ApplicationScope
    fun provideNetworkConnectionCheck(connectivityManager: ConnectivityManager): NetworkConnectionCallback
            = NetworkConnection(connectivityManager)

}