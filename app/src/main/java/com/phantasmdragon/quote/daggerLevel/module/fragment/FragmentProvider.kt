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
package com.phantasmdragon.quote.daggerLevel.module.fragment

import com.phantasmdragon.quote.daggerLevel.annotation.scope.FragmentScope
import com.phantasmdragon.quote.daggerLevel.module.fragment.common.CommonRecyclerModule
import com.phantasmdragon.quote.daggerLevel.module.fragment.favourite.FavouriteFragmentModule
import com.phantasmdragon.quote.daggerLevel.module.fragment.getQuote.GetQuoteModule
import com.phantasmdragon.quote.presentationLayer.fragment.tab.FavouriteQuoteFragment
import com.phantasmdragon.quote.presentationLayer.fragment.tab.GetQuoteFragment
import com.phantasmdragon.quote.presentationLayer.fragment.tab.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {

    @FragmentScope
    @ContributesAndroidInjector(modules = [FavouriteFragmentModule::class,
                                           CommonRecyclerModule::class])
    internal abstract fun provideFavouriteFragment(): FavouriteQuoteFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [GetQuoteModule::class,
                                           CommonRecyclerModule::class])
    internal abstract fun provideGetQuoteFragment(): GetQuoteFragment

    @ContributesAndroidInjector
    internal abstract fun provideSettingsFragment(): SettingsFragment

}