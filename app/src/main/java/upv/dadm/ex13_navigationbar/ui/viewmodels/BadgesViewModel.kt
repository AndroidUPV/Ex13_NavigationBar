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
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Holds the visibility of the small badge and,
 * the number of the large badge
 */
data class BadgesInfo(
    val smallBadgeVisible: Boolean,
    val largeBadgeNumber: Int
)
/**
 * Holds information about badges.
 */
class BadgesViewModel : ViewModel() {

    // Backing property for the UI state of the badges
    private val _badgesUiState = MutableStateFlow(BadgesInfo(false, 0))
    // Backing property for UI state of the badges
    val badgesUiState = _badgesUiState.asStateFlow()

    /**
     * Shows the small badge.
     */
    fun showSmallBadge() {
        viewModelScope.launch {
            _badgesUiState.update { currentState ->
                currentState.copy(smallBadgeVisible = true)
            }
        }
    }

    /**
     * Hides the small badge.
     */
    fun hideSmallBadge() {
        viewModelScope.launch {
            _badgesUiState.update { currentState ->
                currentState.copy(smallBadgeVisible = false)
            }
        }
    }

    /**
     * Increases the current number of the large badge, which will be visible.
     */
    fun increaseLargeBadge() {
        viewModelScope.launch {
            _badgesUiState.update { currentState ->
                currentState.copy(largeBadgeNumber = currentState.largeBadgeNumber + 1)
            }
        }
    }

    /**
     * Sets the current number of the large badge to 0 (not visible).
     */
    fun clearLargeBadge() {
        viewModelScope.launch {
            _badgesUiState.update { currentState ->
                currentState.copy(largeBadgeNumber = 0)
            }
        }
    }
}