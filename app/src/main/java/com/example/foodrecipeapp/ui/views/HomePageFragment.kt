package com.example.foodrecipeapp.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.data.adapters.MainCategoryAdapter
import com.example.foodrecipeapp.data.adapters.SubCategoryAdapter
import com.example.foodrecipeapp.data.entities.CategoryItems
import com.example.foodrecipeapp.data.entities.MealItems
import com.example.foodrecipeapp.data.entities.Recipe
import com.example.foodrecipeapp.databinding.FragmentHomePageBinding
import com.example.foodrecipeapp.ui.viewmodels.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private val categoryViewModel: CategoryViewModel by viewModels()

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var mainCategoryList: ArrayList<CategoryItems>
    private lateinit var subCategoryList: ArrayList<MealItems>

    @Inject
     lateinit var mainCategoryAdapter: MainCategoryAdapter

    @Inject
     lateinit var subCategoryAdapter: SubCategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomePageBinding.bind(view)


        categoryViewModel.getDataFromDb()

        mainCategoryAdapter.setItemClickListener {
            binding.tvCategoryTitle.text ="${it.strcategory} category"
            categoryViewModel.getSpecifiedMealList(it.strcategory)
        }

        subCategoryAdapter.setItemClickListener {
            val selectedMealId: String = it.idMeal
            val bundle = bundleOf("selectedMealId" to selectedMealId)
            view.findNavController().navigate(R.id.action_homePageFragment_to_mealDetailFragment, bundle)
        }

        setupRecyclerView()
        subscribeObservers()

    }

    private fun subscribeObservers(){
        categoryViewModel.mainCategoryList.observe(viewLifecycleOwner){ result ->
            mainCategoryAdapter.categoryItemList = result
        }

        categoryViewModel.subCategoryList.observe(viewLifecycleOwner){ result ->
            subCategoryAdapter.mealItems = result
        }
    }

    private fun setupRecyclerView(){
        rvMainCategory.apply {
            adapter = mainCategoryAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        }

        rvSubCategory.apply{
            adapter = subCategoryAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        }
    }

}