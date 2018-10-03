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
package com.phantasmdragon.quote.daggerLevel.module.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.phantasmdragon.quote.callbackLayer.viewModel.FavouriteQuoteViewModel
import com.phantasmdragon.quote.callbackLayer.viewModel.GetQuoteViewModel
import com.phantasmdragon.quote.callbackLayer.viewModel.MainViewModel
import com.phantasmdragon.quote.daggerLevel.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun postMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetQuoteViewModel::class)
    internal abstract fun postGetQuoteViewModel(viewModel: GetQuoteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteQuoteViewModel::class)
    internal abstract fun postFavouriteQuoteViewModel(viewModel: FavouriteQuoteViewModel): ViewModel

}