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
package com.phantasmdragon.quote.presentationLayer.fragment.tab

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.preference.Preference
import android.text.TextUtils
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.backgroundLevel.NotificationWorker
import com.phantasmdragon.quote.daggerLevel.annotation.scope.ActivityScope
import com.phantasmdragon.quote.utilsLevel.Constant
import com.phantasmdragon.quote.utilsLevel.getSafeBoolean
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
class SettingsFragment @Inject constructor()
    : BaseSettingsFragment(),
      SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject lateinit var sharedPreferences: SharedPreferences

    private val workManager: WorkManager by lazy {
        WorkManager.getInstance()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        setRingtoneSummary()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            getString(R.string.settings_key_alert) -> retrievePushService(sharedPreferences.getSafeBoolean(key))
            getString(R.string.settings_key_interval) -> {
                workManager.cancelAllWork()
                retrieveWorkManager()
            }
        }
    }

    private fun retrievePushService(turnedOn: Boolean) {
        if (turnedOn) {
            retrieveWorkManager()
        } else {
            workManager.cancelAllWork()
        }
    }

    private fun retrieveWorkManager() {
        val periodicWork = PeriodicWorkRequest.Builder(NotificationWorker::class.java,
                                                       getChosenInterval(),
                                                       TimeUnit.MILLISECONDS)
                                              .build()

        workManager.enqueue(periodicWork)
    }

    private fun getChosenInterval() = sharedPreferences.getString(getString(R.string.settings_key_interval), "").toLong()

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            getString(R.string.settings_key_sound) -> onChoseRingtone()
        }

        return super.onPreferenceTreeClick(preference)
    }

    private fun onChoseRingtone() {
        val path = sharedPreferences.getString(getString(R.string.settings_key_sound), Constant.NOTIFICATION_PATH)

        val uri = if (!TextUtils.isEmpty(path)) Uri.parse(path) else null

        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)
            putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
        }

        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        val uri = data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI) as Uri

        setRingtoneSummary(uri)

        sharedPreferences.edit().apply {
            putString(getString(R.string.settings_key_sound), uri.toString())
            apply()
        }
    }

    private fun setRingtoneSummary(ringtoneUri: Uri? = null) {
        val ringtone = RingtoneManager.getRingtone(context, ringtoneUri ?: getRingtoneUri())
        val ringtoneName = ringtone.getTitle(context)

        findPreference(getString(R.string.settings_key_sound)).apply {
            summary = ringtoneName
        }
    }

    private fun getRingtoneUri() = Uri.parse(sharedPreferences.getString(getString(R.string.settings_key_sound), ""))

}