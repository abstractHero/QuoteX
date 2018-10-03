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
package com.phantasmdragon.quote.daggerLevel.module.fragment.common

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import com.phantasmdragon.quote.adapterLevel.recyclerView.viewHolder.ViewHolderFactory
import com.phantasmdragon.quote.callbackLayer.handler.ClickHandler
import com.phantasmdragon.quote.daggerLevel.annotation.qualifier.ActivityContext
import com.phantasmdragon.quote.daggerLevel.annotation.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class CommonRecyclerModule {

    @Provides
    @FragmentScope
    fun provideViewHolderFactory(layoutInflater: LayoutInflater) = ViewHolderFactory(layoutInflater)

    @Provides
    fun provideClickHandler(@ActivityContext context: Context, resources: Resources) = ClickHandler(context, resources)

}