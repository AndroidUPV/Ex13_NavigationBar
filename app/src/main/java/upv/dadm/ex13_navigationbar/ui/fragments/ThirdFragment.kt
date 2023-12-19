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

package upv.dadm.ex13_navigationbar.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import upv.dadm.ex13_navigationbar.R
import upv.dadm.ex13_navigationbar.databinding.FragmentMessageBinding

/**
 * Displays a message.
 */
class ThirdFragment : Fragment(R.layout.fragment_message) {

    // Backing property to resource binding
    private var _binding: FragmentMessageBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentMessageBinding.bind(view)
        // Set the message for the current Fragment
        binding.tvMessage.setText(R.string.third_fragment_message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }
}