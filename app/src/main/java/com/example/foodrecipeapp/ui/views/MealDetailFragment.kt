package com.example.foodrecipeapp.ui.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.databinding.FragmentMealDetailBinding
import com.example.foodrecipeapp.ui.viewmodels.CategoryViewModel
import com.plcoding.spotifycloneyt.other.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_meal_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailFragment : Fragment(R.layout.fragment_meal_detail) {

    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var binding: FragmentMealDetailBinding
    var youtubeLink = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedMealId: String = arguments?.get("selectedMealId") as String

        CoroutineScope(Job() + Dispatchers.Main).launch{
            categoryViewModel.getSpecifiedMealDetail(selectedMealId)
        }

        binding = FragmentMealDetailBinding.bind(view)

        binding.imgBtnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.btnYoutube.setOnClickListener {
            val uri = Uri.parse(youtubeLink)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding = FragmentMealDetailBinding.bind(view)

        subscribeObservers()

    }

    private fun subscribeObservers() {
        categoryViewModel.mealDetails.observe(viewLifecycleOwner){ result ->

            when(result.status){
                Status.SUCCESS -> {
                    Glide.with(requireContext()).load(result.data?.strmealthumb).into(binding.imgItem)

                    binding.tvCategory.text = result.data?.strmeal

                    var ingredient = "${result.data?.stringredient1}      ${result.data?.strmeasure1}\n" +
                            "${result.data?.stringredient2}      ${result.data?.strmeasure2}\n" +
                            "${result.data?.stringredient3}      ${result.data?.strmeasure3}\n" +
                            "${result.data?.stringredient4}      ${result.data?.strmeasure4}\n" +
                            "${result.data?.stringredient5}      ${result.data?.strmeasure5}\n" +
                            "${result.data?.stringredient6}      ${result.data?.strmeasure6}\n" +
                            "${result.data?.stringredient7}      ${result.data?.strmeasure7}\n" +
                            "${result.data?.stringredient8}      ${result.data?.strmeasure8}\n" +
                            "${result.data?.stringredient9}      ${result.data?.strmeasure9}\n" +
                            "${result.data?.stringredient10}      ${result.data?.strmeasure10}\n" +
                            "${result.data?.stringredient11}      ${result.data?.strmeasure11}\n" +
                            "${result.data?.stringredient12}      ${result.data?.strmeasure12}\n" +
                            "${result.data?.stringredient13}      ${result.data?.strmeasure13}\n" +
                            "${result.data?.stringredient14}      ${result.data?.strmeasure14}\n" +
                            "${result.data?.stringredient15}      ${result.data?.strmeasure15}\n" +
                            "${result.data?.stringredient16}      ${result.data?.strmeasure16}\n" +
                            "${result.data?.stringredient17}      ${result.data?.strmeasure17}\n" +
                            "${result.data?.stringredient18}      ${result.data?.strmeasure18}\n" +
                            "${result.data?.stringredient19}      ${result.data?.strmeasure19}\n" +
                            "${result.data?.stringredient20}      ${result.data?.strmeasure20}\n"

                    binding.tvIngredients.text = ingredient
                    binding.tvInstructions.text = result.data?.strinstructions

                    if(result.data?.strsource != null){
                        youtubeLink = result.data.strsource
                    }else{
                        binding.btnYoutube.visibility = View.GONE
                    }
                }
                Status.ERROR -> Unit
                Status.LOADING -> {

                }
            }

        }
    }

}