/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex13_navigationbar.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
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
        // Set the activity content to the root element of the generated view
        setContentView(binding.root)

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
        // Update the visibility of the small badge
        viewModel.smallBadge.observe(this) { visible ->
            binding.bottomNavigation.getOrCreateBadge(R.id.firstFragment).isVisible = visible
        }
        // Update the visibility and number of the large badge according to its current number
        viewModel.largeBadge.observe(this) { quantity ->
            binding.bottomNavigation.getOrCreateBadge(R.id.thirdFragment).apply {
                isVisible = quantity > 0
                number = quantity
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

            // Hides the small badge
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