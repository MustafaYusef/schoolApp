package com.smart.resources.schools_app.features.rating

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.databinding.ItemStudentRateBinding

class RatingAdapter(private val it: List<RatingModel>) : RecyclerView.Adapter<RatingAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemStudentRateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ratingModel: RatingModel){
            binding.itemModel=ratingModel
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemStudentRateBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = it.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(it[position])
    }

}