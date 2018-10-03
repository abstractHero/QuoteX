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

import com.phantasmdragon.quote.daggerLevel.annotation.scope.ApplicationScope
import com.phantasmdragon.quote.networkLayer.api.QuoteApi
import com.phantasmdragon.quote.utilsLevel.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class RetrofitModule {

    @Provides
    @ApplicationScope
    fun provideApi(retrofit: Retrofit): QuoteApi = retrofit.create(QuoteApi::class.java)

    @Provides
    @ApplicationScope
    fun provideRetrofitInterface(okHttpClient: OkHttpClient, converterFactory: MoshiConverterFactory): Retrofit {

        return Retrofit.Builder()
                       .baseUrl(Constant.BASE_URL)
                       .client(okHttpClient)
                       .addConverterFactory(converterFactory)
                       .build()

    }

    @Provides
    @ApplicationScope
    fun provideConverter(): MoshiConverterFactory = MoshiConverterFactory.create()

}