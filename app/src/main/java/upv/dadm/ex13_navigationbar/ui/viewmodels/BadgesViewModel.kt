/*
 * Copyright (c) 2022-2023 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package upv.dadm.ex13_navigationbar.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Holds information about badges.
 */
class BadgesViewModel : ViewModel() {

    // Backing property for visibility of the small badge
    // It is deactivated by default
    private val _smallBadge = MutableLiveData(false)

    // Visibility of the small badge
    val smallBadge: LiveData<Boolean>
        get() = _smallBadge

    // Backing property for number of the large badge
    // It is not visible (0) by default
    private val _largeBadge = MutableLiveData(0)

    // Number of the large badge
    val largeBadge: LiveData<Int>
        get() = _largeBadge

    /**
     * Shows the small badge.
     */
    fun showSmallBadge() {
        _smallBadge.value = true
    }

    /**
     * Hides the small badge.
     */
    fun hideSmallBadge() {
        _smallBadge.value = false
    }

    /**
     * Increases the current number of the large badge, which will be visible.
     */
    fun increaseLargeBadge() {
        _largeBadge.value = _largeBadge.value?.inc()
    }

    /**
     * Sets the current number of the large badge to 0 (not visible).
     */
    fun clearLargeBadge() {
        _largeBadge.value = 0
    }
}