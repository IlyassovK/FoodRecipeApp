package com.example.foodrecipeapp.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.data.entities.CategoryItems
import com.example.foodrecipeapp.data.entities.MealItems
import com.example.foodrecipeapp.data.entities.Recipe
import com.example.foodrecipeapp.databinding.ItemMainCategoryBinding
import com.example.foodrecipeapp.databinding.ItemSubCategoryBinding

class SubCategoryAdapter: RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {

    class SubCategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    var ctx: Context? = null

    val differCallback = object : DiffUtil.ItemCallback<MealItems>() {
        override fun areItemsTheSame(oldItem: MealItems, newItem: MealItems): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MealItems, newItem: MealItems): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        ctx = parent.context

        return SubCategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sub_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val mealItem = mealItems[position]
        val binding = ItemSubCategoryBinding.bind(holder.itemView)

        holder.itemView.apply {

            binding.tvDishName.text = mealItem.strMeal

            Glide.with(context!!).load(mealItem.strMealThumb).into(binding.imgDish)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(mealItem)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return mealItems.size
    }

    val differ = AsyncListDiffer(this, differCallback)

    var mealItems: List<MealItems>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((MealItems) -> Unit)? = null

    fun setItemClickListener(listener: (MealItems) -> Unit){
        onItemClickListener = listener
    }
}