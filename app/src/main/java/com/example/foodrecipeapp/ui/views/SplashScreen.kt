package com.example.foodrecipeapp.ui.views

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.databinding.FragmentSplashScreenBinding
import com.example.foodrecipeapp.ui.viewmodels.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.fragment_splash_screen){

    private lateinit var binding: FragmentSplashScreenBinding

    private val categoryViewModel: CategoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSplashScreenBinding.bind(view)




        binding.btnStart.setOnClickListener {
            view.findNavController().navigate(R.id.action_splashScreen_to_homePageFragment)
        }
    }


}