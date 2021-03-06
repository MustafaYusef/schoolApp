package com.smart.resources.schools_app.features.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haytham.coder.extensions.openPdfViewer
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.isPdf
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.ItemLibraryBinding

class LibraryRecyclerAdapter(private val listLib: List<LibraryModel>) : RecyclerView.Adapter<LibraryRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemLibraryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(libraryModel: LibraryModel){
            binding.itemModel=libraryModel
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemLibraryBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listLib.size
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val eBook= listLib[position]

        holder.bind(eBook)

        holder.itemView.setOnClickListener {
            if(eBook.attachment.isPdf()) it.context.openPdfViewer(eBook.attachment)
            else (holder.itemView as ViewGroup)
                .showSnackBar(it.context.getString(R.string.invalid_pdf_url))
        }
    }
}