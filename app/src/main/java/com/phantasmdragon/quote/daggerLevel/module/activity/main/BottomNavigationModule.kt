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
package com.phantasmdragon.quote.daggerLevel.module.activity.main

import android.content.res.Resources
import android.support.v4.app.FragmentManager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.adapterLevel.viewPager.FragmentViewPagerAdapter
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ActivityScope
import com.phantasmdragon.quote.presentationLayer.acitvity.MainActivity
import dagger.Module
import dagger.Provides

@Module
class BottomNavigationModule {

    @Provides
    @ActivityScope
    fun provideFragmentsColors(resources: Resources): IntArray = resources.getIntArray(R.array.tab_colors)

    @Provides
    fun provideBottomNavigatorAdapter(activity: MainActivity) = AHBottomNavigationAdapter(activity, R.menu.navigation)

    @Provides
    fun provideViewPagerAdapter(supportFragmentManager: FragmentManager) = FragmentViewPagerAdapter(supportFragmentManager)

}