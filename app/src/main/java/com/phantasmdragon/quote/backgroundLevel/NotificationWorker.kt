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
package com.phantasmdragon.quote.backgroundLevel

import android.content.Context
import android.content.res.Resources
import android.databinding.Observable
import androidx.work.Worker
import androidx.work.Worker.Result.FAILURE
import androidx.work.Worker.Result.SUCCESS
import androidx.work.WorkerParameters
import com.phantasmdragon.quote.daggerLevel.custom.AndroidWorkerInjection
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.networkLayer.repository.QuoteServiceRepository
import com.phantasmdragon.quote.utilsLevel.Constant
import com.phantasmdragon.quote.utilsLevel.NotificationHelper
import com.phantasmdragon.quote.utilsLevel.getCorrectedAuthor
import javax.inject.Inject

class NotificationWorker(context: Context,
                         workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    companion object {
        const val QUOTE_NOTIFICATION_ID = 42
    }

    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var quoteServiceRepository: QuoteServiceRepository
    @Inject lateinit var res: Resources

    private lateinit var queryResult: Result

    override fun doWork(): Result {
        AndroidWorkerInjection.inject(this)
        quoteServiceRepository.getQuote()

        return getOperationResult()
    }

    /**
     * It works, BUT IT'S JUST AN EXAMPLE.
     *
     * The work manager works in the different thread from the main thread of the app by default;
     * therefore, we MUST NOT create another thread for doing a job as I've done here
     * (there is just a callback, whereas, a job is being performed through the retrofit).
     * Because of that, I did an endless cycle in order to not return a result until the work is done.
     *
     * Also, it retrieves itself after the chosen period of time.
     * For more info see: https://stackoverflow.com/a/52605503/10342414.
     */
    private fun getOperationResult(): Result {

        val onProperyChangedListener = quoteServiceRepository.queryState.addOnPropertyChangedListener {
            if (it.get() == Constant.QueryState.DONE) {

                val quote = quoteServiceRepository.quote.apply {
                    this?.quoteAuthor = this?.getCorrectedAuthor(res)
                }

                sendNotification(quote)

                queryResult = SUCCESS
            } else {
                queryResult = FAILURE
            }
        }

        while (!this::queryResult.isInitialized) {}

        quoteServiceRepository.queryState.removeOnPropertyChangedCallback(onProperyChangedListener)

        return queryResult
    }

    private fun sendNotification(quote: Quote?) {
        notificationHelper.notify(QUOTE_NOTIFICATION_ID, notificationHelper.getQuoteNotification(quote))
    }

    /**
     * If you use the RxJava, you could instead of returning the callback, return a disposable;
     *
     * like that:
     *
     * .also {
     *      addOnPropertyChangedCallback(it)
     * }.let {
     *      Disposables.fromAction { removeOnPropertyChangedCallback(it) }
     * }
     *
     * This way you can store in can be stored together with other Disposables in CompositeDisposable
     * and the class using this does not need to know any details about how to unregister.
     */
    private inline fun <reified T: Observable> T.addOnPropertyChangedListener(crossinline callback: (T) -> Unit)
            = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            callback(sender as T)
        }
    }.also {
        addOnPropertyChangedCallback(it)
    }

}