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
package com.phantasmdragon.quote.presentationLayer.acitvity

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.adapterLevel.viewPager.FragmentViewPagerAdapter
import com.phantasmdragon.quote.callbackLayer.MarkAsDeletedCallback
import com.phantasmdragon.quote.callbackLayer.OnLikeListener
import com.phantasmdragon.quote.callbackLayer.viewModel.MainViewModel
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.presentationLayer.fragment.tab.FavouriteQuoteFragment
import com.phantasmdragon.quote.presentationLayer.fragment.tab.GetQuoteFragment
import com.phantasmdragon.quote.presentationLayer.fragment.tab.SettingsFragment
import com.phantasmdragon.quote.utilsLevel.BadgeIconUtils
import com.phantasmdragon.quote.utilsLevel.withViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity: DaggerAppCompatActivity(),
                    OnLikeListener,
                    MarkAsDeletedCallback {

    @Inject lateinit var favouriteQuoteFragment: FavouriteQuoteFragment
    @Inject lateinit var getQuoteFragment: GetQuoteFragment
    @Inject lateinit var settingsFragment: SettingsFragment

    @Inject lateinit var tabColors: IntArray
    @Inject lateinit var bottomNavAdapter: AHBottomNavigationAdapter
    @Inject lateinit var viewPagerAdapter: FragmentViewPagerAdapter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var badgeIconUtils: BadgeIconUtils

    private lateinit var mainViewModel: MainViewModel

    private val fragments by lazy {
        arrayOf(favouriteQuoteFragment,
                getQuoteFragment,
                settingsFragment)
    }

    override fun onLike(quote: Quote) {
        actMain_bottomNav.setNotification(AHNotification.justText("${++mainViewModel.badgeCount}"),
                                          0)

        mainViewModel.insert(quote)
    }

    override fun markAsDeleted(quoteId: Long?) {
        mainViewModel.markAsDeleted(quoteId, ++mainViewModel.undoBadgeCount)

        invalidateOptionsMenu()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PreferenceManager.setDefaultValues(this, R.xml.settings, false)

        setSupportActionBar(actMain_toolbar)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        initViewModel()
        initBottomNavigation()
    }

    private fun initViewModel() {
        withViewModel<MainViewModel>(viewModelFactory) {
            mainViewModel = this
        }
    }

    private fun initBottomNavigation() {
        bottomNavAdapter.setupWithBottomNavigation(actMain_bottomNav, tabColors)
        viewPagerAdapter.fragments.addAll(fragments)

        val currentItem = if (mainViewModel.currentPosition == -1) fragments.size.div(2) else mainViewModel.currentPosition

        actMain_viewPager.adapter = viewPagerAdapter
        actMain_viewPager.offscreenPageLimit = getOffScreenPageLimit()
        actMain_viewPager.setCurrentItem(currentItem, false)

        fitToolbarColor(currentItem)

        actMain_bottomNav.isColored = true
        actMain_bottomNav.currentItem = currentItem
        actMain_bottomNav.setOnTabSelectedListener(onTabSelectedListener)
    }

    private fun getOffScreenPageLimit() = fragments.lastIndex

    private fun fitToolbarColor(position: Int) {
        actMain_toolbar.setBackgroundColor(tabColors[position])
    }

    private val onTabSelectedListener = AHBottomNavigation.OnTabSelectedListener { position, _ ->
        mainViewModel.currentPosition = position

        if (position == 0) dropTabBadgeCount()

        actMain_viewPager.setCurrentItem(position, true)

        invalidateOptionsMenu()
        fitToolbarColor(position)

        true
    }

    private fun dropTabBadgeCount() {
        mainViewModel.badgeCount = 0
        actMain_bottomNav.setNotification("", 0)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (mainViewModel.currentPosition == 0) changeMenuVisibility(menu)
        else changeMenuVisibility(menu, false)

        return true
    }

    private fun changeMenuVisibility(menu: Menu?, show: Boolean = true) {
        menu?.getItem(0)?.isVisible = show
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tab_favorite, menu)

        menu?.findItem(R.id.menuFavorite_undo)?.apply {
            icon = badgeIconUtils.getDrawableWithBadge(mainViewModel.undoBadgeCount, R.drawable.ic_undo)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
        R.id.menuFavorite_undo -> {
            if (mainViewModel.undoBadgeCount > 0) retrieveQuote()

            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun retrieveQuote() {
        mainViewModel.unmarkDeleted(mainViewModel.undoBadgeCount--)

        invalidateOptionsMenu()
    }

    /**
     * All quotes marked as deleted are being deleted from the database when the app is shut down.
     */
    override fun onDestroy() {
        mainViewModel.deleteAllMarked()

        super.onDestroy()
    }

}