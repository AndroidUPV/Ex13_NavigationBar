/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex13_navigationbar.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Holds information about badges.
 */
class BadgesViewModel : ViewModel() {

    // Backing property for visibility of the badge
    // It is deactivated by default
    private var _badge = MutableLiveData(false)

    // Visibility of the badge
    val badge: LiveData<Boolean>
        get() = _badge

    // Backing property for number of the numeric badge
    // It is not visible (0) by default
    private var _numericBadge = MutableLiveData(0)

    // Number of the numeric badge
    val numericBadge: LiveData<Int>
        get() = _numericBadge

    /**
     * Activates the badge.
     */
    fun activateBadge() {
        _badge.value = true
    }

    /**
     * Deactivates the badge.
     */
    fun deactivateBadge() {
        _badge.value = false
    }

    /**
     * Increases the current number of the numeric badge, which will be visible.
     */
    fun increaseNumericBadge() {
        _numericBadge.value = _numericBadge.value?.inc()
    }

    /**
     * Sets the current number of the numeric badge to 0 (not visible).
     */
    fun clearNumericBadge() {
        _numericBadge.value = 0
    }
}