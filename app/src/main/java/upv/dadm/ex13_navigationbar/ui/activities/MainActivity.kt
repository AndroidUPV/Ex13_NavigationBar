/*
 * Copyright (c) 2022-2024 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package upv.dadm.ex13_navigationbar.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import kotlinx.coroutines.launch
import upv.dadm.ex13_navigationbar.R
import upv.dadm.ex13_navigationbar.databinding.ActivityMainBinding
import upv.dadm.ex13_navigationbar.ui.viewmodels.BadgesViewModel

/**
 * Displays a NavigationBar that lets the user navigate
 * through four different Fragments (they just show a message).
 * Action elements activate badges for the first and third Fragment
 * (small and large badge, respectively).
 * Navigating to that Fragment clears the badge.
 */
class MainActivity : AppCompatActivity(), MenuProvider {

    // Reference to the ViewModel that holds badges information
    private val viewModel: BadgesViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Enable edge-to-edge display
        enableEdgeToEdge()
        // Set the activity content to the root element of the generated view
        setContentView(binding.root)
        // Prevent the layout from overlapping with system bars in edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Make the custom ToolBar the ActionBar
        setSupportActionBar(binding.toolbar)
        // Add this Activity as MenuProvider to itself
        addMenuProvider(this@MainActivity)
        // Add observers for the ViewModel
        setUpObservers()
        // Set up NavController-related elements
        setUpNavController()

    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.badgesUiState.collect { uiState ->
                    // Update the visibility of the small badge
                    binding.bottomNavigation.getOrCreateBadge(R.id.firstFragment).isVisible =
                        uiState.smallBadgeVisible
                    // Update the visibility and number of the large badge according to its current number
                    binding.bottomNavigation.getOrCreateBadge(R.id.thirdFragment).apply {
                        isVisible = uiState.largeBadgeNumber > 0
                        number = uiState.largeBadgeNumber
                    }
                }
            }
        }
    }

    private fun setUpNavController() {
        // Get an instance of the NavController.
        // findNavController() does not work properly with FragmentContainerView in onCreate()
        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        // Configure the NavigationBar to work with the NavController
        setupWithNavController(binding.bottomNavigation, navController)

        // Clear the Badges whenever the user navigates to the associated Fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.firstFragment -> viewModel.hideSmallBadge()
                R.id.thirdFragment -> viewModel.clearLargeBadge()
            }
        }
    }

    // Populates the ActionBar with action elements
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.menu_badges, menu)

    // Reacts to the selection of action elements
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        // Determine the action to take place according to its Id
        return when (menuItem.itemId) {

            // Hide the small badge
            R.id.mActivateBadge -> {
                viewModel.showSmallBadge()
                true
            }

            // Set the number of the large badge to 0
            R.id.mIncreaseNumericBadge -> {
                viewModel.increaseLargeBadge()
                true
            }

            // If none of the custom action elements was selected, let the system deal with it
            else -> false
        }
    }

}