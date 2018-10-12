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
package com.phantasmdragon.quote.utilsLevel

import android.arch.lifecycle.*
import android.content.SharedPreferences
import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.dataLayer.json.Quote

inline fun <reified T : ViewDataBinding> LayoutInflater.inflateBinding(
    layoutId: Int,
    viewGroup: ViewGroup?,
    attachToRoot: Boolean = false
): T = DataBindingUtil.inflate(this, layoutId, viewGroup, attachToRoot)

/**
 * There are four absolutely identical methods down below,
 * except two of them are used inside a fragment; whereas, the rest two are for activities.
 *
 * I haven't come up with a solution to get all of them together yet :(
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.withViewModel(viewModelFactory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val viewModel = getViewModel<T>(viewModelFactory)
    viewModel.body()
    return viewModel
}

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> AppCompatActivity.withViewModel(viewModelFactory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val viewModel = getViewModel<T>(viewModelFactory)
    viewModel.body()
    return viewModel
}

fun <T : Any?, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

/**
 * The function turns the quote's fields into the view that is appropriate for sharing.
 */
fun Quote?.toShareView() =
    """"${this?.quoteText}"
        |
        |© ${this?.quoteAuthor}""".trimMargin()

fun Quote.getCorrectedAuthor(resources: Resources) = if (this.quoteAuthor.isNullOrEmpty()) resources.getString(R.string.default_author_name) else this.quoteAuthor?.trim()

/**
 * It's just a short way to get a boolean value without checks.
 */
fun <T : SharedPreferences?> T.getSafeBoolean(key: String?) = this?.getBoolean(key, false) ?: false

/**
 * Almost identical function from android-ktx extensions.
 * See: https://github.com/android/android-ktx/blob/89ee2e1cde1e1b0226ed944b9abd55cee0f9b9d4/src/main/java/androidx/core/content/SharedPreferences.kt
 */
inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    action(editor)
    editor.apply()
}