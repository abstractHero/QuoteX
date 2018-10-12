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

import android.app.SearchManager
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.phantasmdragon.quote.R
import com.phantasmdragon.quote.adapterLevel.viewPager.FragmentViewPagerAdapter
import com.phantasmdragon.quote.callbackLayer.MarkAsDeletedCallback
import com.phantasmdragon.quote.callbackLayer.OnLikeListener
import com.phantasmdragon.quote.callbackLayer.viewModel.MainViewModel
import com.phantasmdragon.quote.dataLayer.json.Quote
import com.phantasmdragon.quote.utilsLevel.BadgeIconUtils
import com.phantasmdragon.quote.utilsLevel.withViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity :
    DaggerAppCompatActivity(),
    OnLikeListener,
    MarkAsDeletedCallback {

    @Inject lateinit var tabColors: IntArray
    @Inject lateinit var bottomNavAdapter: AHBottomNavigationAdapter
    @Inject lateinit var viewPagerAdapter: FragmentViewPagerAdapter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var badgeIconUtils: BadgeIconUtils
    @Inject lateinit var searchManager: SearchManager

    private lateinit var mainViewModel: MainViewModel
    private var disposable: Disposable? = null

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

        val currentItem = if (mainViewModel.currentPosition == -1) viewPagerAdapter.getMiddleItem() else mainViewModel.currentPosition

        actMain_viewPager.adapter = viewPagerAdapter
        actMain_viewPager.offscreenPageLimit = getOffScreenPageLimit()
        actMain_viewPager.setCurrentItem(currentItem, false)

        fitToolbarColor(currentItem)

        actMain_bottomNav.isColored = true
        actMain_bottomNav.currentItem = currentItem
        actMain_bottomNav.setOnTabSelectedListener(onTabSelectedListener)
    }

    private fun getOffScreenPageLimit() = viewPagerAdapter.count - 1

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
        changeMenuVisibility(menu, mainViewModel.currentPosition == 0)

        return true
    }

    private fun changeMenuVisibility(menu: Menu?, show: Boolean) {
        menu?.apply {
            getItem(0)?.isVisible = show
            getItem(1)?.isVisible = show
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tab_favorite, menu)

        val searchViewItem = menu?.findItem(R.id.menuFavorite_search)
        val searchView = (searchViewItem?.actionView as SearchView).apply {
            maxWidth = Int.MAX_VALUE    // Need to position the delete icon to the right side when landscape.
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        bindSearchViewListener(searchView)
        restoreSearchViewState(searchView, searchViewItem)

        menu.findItem(R.id.menuFavorite_undo)?.apply {
            icon = badgeIconUtils.getDrawableWithBadge(mainViewModel.undoBadgeCount, R.drawable.ic_undo)
        }

        return true
    }

    private fun bindSearchViewListener(searchView: SearchView) {
        disposable = RxSearchView.queryTextChanges(searchView)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mainViewModel.searchQuery = it
                    viewPagerAdapter.favoriteQuoteFragment.submitSearchQuery(it)
                }
    }

    private fun restoreSearchViewState(searchView: SearchView, searchViewItem: MenuItem?) {
        mainViewModel.searchQuery.also {
            if (it.isNotEmpty()) {
                searchViewItem?.expandActionView()
                searchView.apply {
                    setQuery(it, false)
                    clearFocus()
                }
            }
        }
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

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    companion object {
        const val KEY_UNDO_BADGE = "key_undo_badge"
    }

}
