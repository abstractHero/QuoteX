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

import android.content.Context
import android.content.res.Resources
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import com.phantasmdragon.quote.daggerLevel.annotation.qualifier.ActivityContext
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ActivityScope
import com.phantasmdragon.quote.daggerLevel.module.viewModel.ViewModelModule
import com.phantasmdragon.quote.presentationLayer.acitvity.MainActivity
import com.phantasmdragon.quote.utilsLevel.BadgeIconUtils
import dagger.Module
import dagger.Provides

@Module(includes = [BottomNavigationModule::class,
                    ViewModelModule::class])
class MainActivityModule {

    @Provides
    @ActivityContext
    fun provideActivityContext(activity: MainActivity): Context = activity

    @Provides
    fun provideSupportFragmentManager(activity: MainActivity): FragmentManager = activity.supportFragmentManager

    @Provides
    @ActivityScope
    fun provideBadgeIconUtils(layoutInflater: LayoutInflater, resources: Resources) = BadgeIconUtils(layoutInflater, resources)

}
