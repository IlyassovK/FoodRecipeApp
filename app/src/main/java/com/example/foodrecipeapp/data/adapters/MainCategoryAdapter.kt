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
import com.example.foodrecipeapp.databinding.ItemMainCategoryBinding

class MainCategoryAdapter: RecyclerView.Adapter<MainCategoryAdapter.MainCategoryViewHolder>() {

    class MainCategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    var ctx: Context? = null

    val differCallback = object : DiffUtil.ItemCallback<CategoryItems>() {
        override fun areItemsTheSame(oldItem: CategoryItems, newItem: CategoryItems): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryItems, newItem: CategoryItems): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryViewHolder {
        ctx = parent.context
        return MainCategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_main_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainCategoryViewHolder, position: Int) {
        val categoryItem = categoryItemList[position]
        val binding = ItemMainCategoryBinding.bind(holder.itemView)

        holder.itemView.apply {

            binding.tvDishName.text = categoryItem.strcategory

            Glide.with(context!!).load(categoryItem.strcategorythumb).into(binding.imgDish)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(categoryItem)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryItemList.size
    }

    val differ = AsyncListDiffer(this, differCallback)

    var categoryItemList: List<CategoryItems>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((CategoryItems) -> Unit)? = null

    fun setItemClickListener(listener: (CategoryItems) -> Unit){
        onItemClickListener = listener
    }
}