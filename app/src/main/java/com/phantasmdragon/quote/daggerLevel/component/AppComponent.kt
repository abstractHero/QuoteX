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
package com.phantasmdragon.quote.daggerLevel.component

import com.phantasmdragon.quote.QuoteApplication
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ApplicationScope
import com.phantasmdragon.quote.daggerLevel.module.AppModule
import com.phantasmdragon.quote.daggerLevel.module.activity.ActivityBuilder
import com.phantasmdragon.quote.daggerLevel.module.worker.AndroidWorkerInjectionModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = [AndroidSupportInjectionModule::class,
                      AppModule::class,
                      ActivityBuilder::class,
                      AndroidWorkerInjectionModule::class])
interface AppComponent : AndroidInjector<QuoteApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<QuoteApplication>()

}